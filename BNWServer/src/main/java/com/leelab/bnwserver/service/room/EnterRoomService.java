package com.leelab.bnwserver.service.room;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.leelab.bnwserver.dao.BnwUserDao;
import com.leelab.bnwserver.dao.RecordDao;
import com.leelab.bnwserver.dao.RoomDao;
import com.leelab.bnwserver.dto.RecordDto;
import com.leelab.bnwserver.dto.RoomDto;
import com.leelab.bnwserver.dto.RoomState;
import com.leelab.bnwserver.service.AbstractService;

@Service
public class EnterRoomService extends AbstractService {

	@Override
	public void execute(HashMap<String, Object> request, HashMap<String, Object> response) {
		String participant = request.get("participant").toString();
		int room_no = Integer.parseInt(request.get("room_no").toString());
		
		/* 방 상태정보 수정 */
		RoomDao room = callDao(RoomDao.class);
		RoomDto targetRoom = room.getRoom(room_no);
		targetRoom.setParticipant(participant);
		targetRoom.setRoom_state(RoomState.FULL.getValue());
		room.updateRoom(targetRoom);
		RoomDto updatedRoom = room.getRoom(room_no);
		
		BnwUserDao userDao = callDao(BnwUserDao.class);
		RecordDao recordDao = callDao(RecordDao.class);

		ArrayList<ParticipantListVo> participants = new ArrayList<ParticipantListVo>();

		ParticipantListVo creator = new ParticipantListVo();
		creator.setNickname(userDao.getUser(updatedRoom.getCreator()).getNickname());
		RecordDto creatorRecord = recordDao.getRecord(updatedRoom.getCreator());
		creator.setWin(creatorRecord.getWin());
		creator.setDraw(creatorRecord.getDraw());
		creator.setLose(creatorRecord.getLose());
		creator.setRate(creatorRecord.getWinning_rate());
		creator.setReady(true);
		
		ParticipantListVo participantVo = new ParticipantListVo();
		participantVo.setNickname(userDao.getUser(participant).getNickname());
		RecordDto paticipantRecord = recordDao.getRecord(participant);
		participantVo.setWin(paticipantRecord.getWin());
		participantVo.setDraw(paticipantRecord.getDraw());
		participantVo.setLose(paticipantRecord.getLose());
		participantVo.setRate(paticipantRecord.getWinning_rate());
		participantVo.setReady(false);
		
		participants.add(creator);
		participants.add(participantVo);
		
		response.put("room", updatedRoom);
		response.put("vos", participants);
	}
	
}
