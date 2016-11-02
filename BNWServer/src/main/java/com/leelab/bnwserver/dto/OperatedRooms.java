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
		if(participant_ready==1)
		{
			logger.info("{}번방 게임 시작 불가");
			obj.put("result", false);
		}
		else
		{
			logger.info("{}번방 게임 가능");
			obj.put("result", true);			
		}		
		sendSuper(room_no, obj.toString());
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
		logger.info("{}번방 나가기 요청 - {}", room_no, inType);
		
		if(inType.equals("super"))
		{
			JSONObject obj = new JSONObject();
			obj.put("type", "out!");
			broadCastInRoom(room_no, obj.toString());
			sqlSession.getMapper(RoomDao.class).deleteRoom(room_no);
		}
		else
		{
			JSONObject obj = new JSONObject();
			obj.put("type", "participant_out!");
			sendSuper(room_no, obj.toString());

			obj.put("type", "out!");
			sendParticipant(room_no, obj.toString());
			RoomDao dao = sqlSession.getMapper(RoomDao.class);
			RoomDto currentRoom = dao.getRoom(room_no);
			currentRoom.setParticipant("");
			currentRoom.setParticipant_ready(1);
			currentRoom.setRoom_state(RoomState.WAIT.getValue());
			dao.updateRoom(currentRoom);
		}
		
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
		operatedRooms.get(room_no).get("participant").sendMessage(new TextMessage(msg));
	}
	
}
