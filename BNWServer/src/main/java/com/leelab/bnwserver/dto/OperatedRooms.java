package com.leelab.bnwserver.dto;

import java.io.IOException;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.leelab.bnwserver.dao.BnwUserDao;
import com.leelab.bnwserver.dao.GameDao;
import com.leelab.bnwserver.dao.RecordDao;
import com.leelab.bnwserver.dao.RoomDao;

public class OperatedRooms {
	
	private static final Logger logger = LoggerFactory.getLogger(OperatedRooms.class);
	
	private HashMap<Integer, HashMap<String,WebSocketSession>> operatedRooms;
	
	private SqlSession sqlSession;
	
	public OperatedRooms() {
		operatedRooms = new HashMap<Integer, HashMap<String,WebSocketSession>>(); 
	}
	
	public void newOperatedRoom(int room_no) {
		operatedRooms.put(room_no, new HashMap<String, WebSocketSession>());
	}
	
	public HashMap<String, WebSocketSession> getOperatedRoom(int room_no) {
		return operatedRooms.get(room_no);
	}
	
	public void handleRequest(WebSocketSession session, WebSocketMessage<?> message) throws JSONException, IOException {
		JSONObject request = new JSONObject(message.getPayload().toString());
		
		String type = request.getString("type");
		if(type.equals("enter"))
		{
			processTypeEnter(request.getInt("room_no"), session);
		}
		else if(type.equals("chat"))
		{
			processTypeChat(request.getInt("room_no"), request.getString("id"), request.getString("msg"));
		}
		else if(type.equals("game_start"))
		{
			processTypeGameStart(request.getInt("room_no"));
		}
		else if(type.equals("participant_ready"))
		{
			processTypeReady(request.getInt("room_no"), true);
		}
		else if(type.equals("participant_ready_cancel"))
		{
			processTypeReady(request.getInt("room_no"), false);
		}
		else if(type.equals("out_of_room"))
		{
			processTypeOutOfRoom(request.getInt("room_no"), request.getString("in_type"));
		}
		else if(type.equals("forced_game_finish"))
		{
			try
			{
				processTypeForcedGameFinish(request);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(type.equals("end_my_turn"))
		{
			processTypeEndMyTurn(request);
		}
	}
	
	public void processTypeChat(int room_no, String speaker, String msg) throws IOException {
		HashMap<String, WebSocketSession> room = getOperatedRoom(room_no);
		JSONObject obj = new JSONObject();
		obj.put("type", "chat");
		obj.put("msg", msg);
		obj.put("speaker", speaker);
		
		logger.info("메세지 전송 : {} {}", speaker, msg);
		room.get("super").sendMessage(new TextMessage(obj.toString()));
		WebSocketSession participant = room.get("participant");
		if(participant!=null)
		{
			participant.sendMessage(new TextMessage(obj.toString()));
		}		
	}
	public void processTypeEnter(int room_no, WebSocketSession session) throws IOException {
		if(getOperatedRoom(room_no)==null)
		{
			newOperatedRoom(room_no);
			getOperatedRoom(room_no).put("super", session);
			logger.info("{}번방 생성 및 방장 접속", room_no);
		}
		else
		{
			getOperatedRoom(room_no).put("participant", session);
			
			JSONObject obj = new JSONObject();
			obj.put("type", "notify_new_participant");
			String participant_id = sqlSession.getMapper(RoomDao.class).getRoom(room_no).getParticipant();
			BnwUserDto userDto = sqlSession.getMapper(BnwUserDao.class).getUser(participant_id);
			RecordDao rDao = sqlSession.getMapper(RecordDao.class);
			obj.put("user-info", userDto.getId());
			obj.put("nickname", userDto.getNickname());
			obj.put("win", rDao.getRecord(participant_id).getWin());
			obj.put("draw", rDao.getRecord(participant_id).getDraw());
			obj.put("lose", rDao.getRecord(participant_id).getLose());
			obj.put("rate", rDao.getRecord(participant_id).getWinning_rate());
			obj.put("ready", false);
			sendSuper(room_no, obj.toString());
			logger.info("{}번방에 참가자 접속", room_no);
		}		
	}
	public void processTypeGameStart(int room_no) throws IOException {
		int participant_ready = sqlSession.getMapper(RoomDao.class).getRoom(room_no).getParticipant_ready();
		JSONObject obj = new JSONObject();
		obj.put("type", "game_start_operation");
		RoomDto room = sqlSession.getMapper(RoomDao.class).getRoom(room_no);
		obj.put("creator", room.getCreator());
		obj.put("participant", room.getParticipant());
		if(participant_ready==1)
		{
			logger.info("{}번방 게임 시작 불가");
			obj.put("result", false);
			sendSuper(room_no, obj.toString());
		}
		else
		{
			logger.info("{}번방 게임 가능");
			obj.put("result", true);
			
			GameDao gDao = sqlSession.getMapper(GameDao.class);
			int gameNumber = gDao.getNextGameNumber();
			gDao.insertGame(new GameDto(gameNumber, room.getCreator(), room.getParticipant()));
			obj.put("game_no", gameNumber);
			logger.info("{}번방 {}번 게임 생성", gameNumber);
			broadCastInRoom(room_no, obj.toString());
		}		
	}
	public void processTypeReady(int room_no, boolean readyOperation) throws IOException {
		RoomDao dao = sqlSession.getMapper(RoomDao.class);
		RoomDto dto = dao.getRoom(room_no);
		
		JSONObject result = new JSONObject();
		result.put("type", "ready_result");
		
		JSONObject resultForSuper = new JSONObject();
		resultForSuper.put("type", "ready_notify");

		if(readyOperation)
		{	// Ready 완료
			logger.info("{}번방 플레이어 준비 완료", room_no);
			dto.setParticipant_ready(2);
			dao.updateRoom(dto);
			result.put("result", "ready");
			resultForSuper.put("result", "ready");
		}
		else
		{	// Ready 취소
			logger.info("{}번방 플레이어 준비 취소", room_no);
			dto.setParticipant_ready(1);
			dao.updateRoom(dto);
			result.put("result", "cancel");
			resultForSuper.put("result", "cancel");
		}
		
		sendSuper(room_no, resultForSuper.toString());
		sendParticipant(room_no, result.toString());
	}
	public void processTypeOutOfRoom(int room_no, String inType) throws IOException {
		logger.info("{}번방 나가기 요청 - !{}!", room_no, inType);
		RoomDao dao = sqlSession.getMapper(RoomDao.class);

		try
		{
			if(inType.equals("super"))
			{
				JSONObject obj = new JSONObject();
				obj.put("type", "out!");
				broadCastInRoom(room_no, obj.toString());
				System.out.println("..?");
				dao.deleteRoom(room_no);			
			}
			else if(inType.equals("non-super"))
			{
				JSONObject obj = new JSONObject();
				obj.put("type", "participant_out!");
				sendSuper(room_no, obj.toString());

				obj.put("type", "out!");
				sendParticipant(room_no, obj.toString());
				
				RoomDto currentRoom = dao.getRoom(room_no);
				currentRoom.setParticipant("");
				currentRoom.setParticipant_ready(1);
				currentRoom.setRoom_state(RoomState.WAIT.getValue());
				dao.updateRoom(currentRoom);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	private void processTypeForcedGameFinish(JSONObject request) throws IOException {
		logger.info(request.toString());
		
		JSONObject responseForWinner = new JSONObject();
		JSONObject responseForLoser = new JSONObject();
		
		responseForWinner.put("type", "game_finish_out_win");
		responseForLoser.put("type", "game_finish_out_lose");
		
		int room_no = request.getInt("room_no");
		String inType = request.getString("in_type");
		int game_no = request.getInt("game_no");
		
		GameDao gDao = sqlSession.getMapper(GameDao.class);
		RoomDao rDao = sqlSession.getMapper(RoomDao.class);
		RecordDao recordDao = sqlSession.getMapper(RecordDao.class);

		RoomDto room = rDao.getRoom(room_no);
		GameDto game = gDao.selectGame(game_no);
		
		/* 게임 강제종료 요청에 대한 패배처리 */
		if(inType.equals("super"))
		{
			game.setWinner(room.getParticipant());
			game.setLoser(room.getCreator());
		}
		else
		{
			game.setWinner(room.getCreator());
			game.setLoser(room.getParticipant());
		}
		
		RecordDto winnerRecord = recordDao.getRecord(game.getWinner());
		RecordDto loserRecord = recordDao.getRecord(game.getLoser());
		
		winnerRecord.setWin(winnerRecord.getWin()+1);
		loserRecord.setLose(loserRecord.getLose()+1);
		winnerRecord.calculateWinningRate();
		loserRecord.calculateWinningRate();
		
		logger.info("게임 내역 업데이트");
		gDao.updateGame(game);
		
		logger.info("승자 게임 전적 업데이트");
		recordDao.updateRecord(winnerRecord);
		
		logger.info("패자 게임 전적 업데이트");
		recordDao.updateRecord(loserRecord);
		
		logger.info("방 삭제");
		rDao.deleteRoom(room_no);
		
		if(inType.equals("super"))
		{
			sendSuper(room.getRoom_no(), responseForLoser.toString());
			sendParticipant(room.getRoom_no(), responseForWinner.toString());
		}
		else
		{
			sendSuper(room.getRoom_no(), responseForWinner.toString());
			sendParticipant(room.getRoom_no(), responseForLoser.toString());
		}
	}
	private void processTypeEndMyTurn(JSONObject request) throws IOException {
		int game_no = request.getInt("game_no");
		int score = request.getInt("score");
		int room_no = request.getInt("room_no");
		String inType = request.getString("in_type");
		
		GameDao gDao = sqlSession.getMapper(GameDao.class);
		GameDto game = gDao.selectGame(game_no);
		
		JSONObject response = new JSONObject();
		response.put("type", "notification_game_info");
		
		logger.info("게이머 점수 업데이트");		
		if(inType.equals("super"))
		{
			if(game.getGamer_1_score()-score<0)
			{
				response.put("type", "notification_not_invalid_number");
				sendSuper(room_no, response.toString());
				return;
			}
			else
			{
				game.setGamer_1_score(game.getGamer_1_score()-score);
				game.setGamer_1_temp(score);
				game.setTurn(2);
				response.put("turn", "non-super");
			}
		}
		else
		{
			if(game.getGamer_2_score()-score<0)
			{
				response.put("type", "notification_not_invalid_number");
				sendParticipant(room_no, response.toString());
				return;
			}
			else
			{
				game.setGamer_2_score(game.getGamer_2_score()-score);
				game.setTurn(1);
				response.put("turn", "super");
				
				game.setRound(game.getRound()+1);
				
				if(game.getGamer_1_temp()>score)
				{
					game.setGamer_1_round(game.getGamer_1_round()+1);
				}
				else if(game.getGamer_1_temp()==score)
				{
					game.setGamer_1_round(game.getGamer_1_round()+1);					
					game.setGamer_2_round(game.getGamer_2_round()+1);
				}
				else
				{
					game.setGamer_2_round(game.getGamer_2_round()+1);
				}
				
				RecordDao record = sqlSession.getMapper(RecordDao.class);
				RecordDto _super = record.getRecord(game.getGamer_1());
				RecordDto non_super = record.getRecord(game.getGamer_2());
				RoomDao roomDao = sqlSession.getMapper(RoomDao.class);
				JSONObject winnerJson = new JSONObject();
				JSONObject loserJson = new JSONObject();
				JSONObject drawJson = new JSONObject();
				
				winnerJson.put("type", "game_finish_out_win");
				loserJson.put("type", "game_finish_out_lose");
				drawJson.put("type", "game_finish_out_draw");
				if(game.getGamer_1_round()==5)
				{
					if(game.getGamer_2_round()==5)
					{
						_super.setDraw(_super.getDraw()+1);
						non_super.setDraw(non_super.getDraw()+1);
						roomDao.deleteRoom(room_no);

						broadCastInRoom(room_no, drawJson.toString());
						return;
					}
					else
					{
						_super.setWin(_super.getWin()+1);
						non_super.setLose(non_super.getLose()+1);
						game.setWinner(game.getGamer_1());
						game.setLoser(game.getGamer_2());
					}					
					_super.calculateWinningRate();
					non_super.calculateWinningRate();
					record.updateRecord(_super);
					record.updateRecord(non_super);
					gDao.updateGame(game);
					roomDao.deleteRoom(room_no);
					
					sendSuper(room_no, winnerJson.toString());
					sendParticipant(room_no, loserJson.toString());
					return;
				}
				
				if(game.getGamer_2_round()==5)
				{
					if(game.getGamer_1_round()==5)
					{
						_super.setDraw(_super.getDraw()+1);
						non_super.setDraw(non_super.getDraw()+1);
						roomDao.deleteRoom(room_no);
						broadCastInRoom(room_no, drawJson.toString());
						return;
					}
					else
					{
						_super.setLose(_super.getLose()+1);
						non_super.setWin(non_super.getWin()+1);
						game.setWinner(game.getGamer_2());
						game.setLoser(game.getGamer_1());
					}
					
					_super.calculateWinningRate();
					non_super.calculateWinningRate();
					record.updateRecord(_super);
					record.updateRecord(non_super);
					gDao.updateGame(game);
					roomDao.deleteRoom(room_no);
					sendSuper(room_no, loserJson.toString());
					sendParticipant(room_no, winnerJson.toString());
					
					return;
				}
			}
		}
		
		logger.info("게임 정보 업데이트");
		gDao.updateGame(game);
		
		JSONObject gameObject = new JSONObject();
		gameObject.put("no", game.getNo());
		gameObject.put("round", game.getRound());
		gameObject.put("turn", game.getTurn());
		gameObject.put("gamer_1_score", game.getGamer_1_score());
		gameObject.put("gamer_2_score", game.getGamer_2_score());
		gameObject.put("gamer_1_round", game.getGamer_1_round());
		gameObject.put("gamer_2_round", game.getGamer_2_round());
		logger.info(gameObject.toString());
		
		response.put("game", gameObject);
		response.put("score", score);
		broadCastInRoom(room_no, response.toString());
	}
	
	public void setSqlSession(SqlSession session) {
		this.sqlSession = session;
	}
	
	public void broadCastInRoom(int room_no, String msg) throws IOException {
		sendSuper(room_no, msg);
		sendParticipant(room_no, msg);
	}
	
	public void sendSuper(int room_no, String msg) throws IOException {
		operatedRooms.get(room_no).get("super").sendMessage(new TextMessage(msg));
	}
	
	public void sendParticipant(int room_no, String msg) throws IOException {
		WebSocketSession conn = operatedRooms.get(room_no).get("participant");
		if(conn!=null)
		{
			if(conn.isOpen())
			{
				conn.sendMessage(new TextMessage(msg));
			}
		}
	}
	
}
