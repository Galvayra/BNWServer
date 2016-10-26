package com.leelab.bnwserver.service.room;

import java.util.HashMap;

import com.leelab.bnwserver.dao.RoomDao;
import com.leelab.bnwserver.dto.RoomDto;
import com.leelab.bnwserver.dto.RoomState;
import com.leelab.bnwserver.service.Service;

public class EnterRoomService extends Service {

	@Override
	public void execute(HashMap<String, Object> request, HashMap<String, Object> response) {
		String participant = request.get("participant").toString();
		int room_no = Integer.parseInt(request.get("room_no").toString());
		
		RoomDao room = callDao(RoomDao.class);
		RoomDto targetRoom = room.getRoom(room_no);
		targetRoom.setParticipant(participant);
		targetRoom.setRoom_state(RoomState.FULL.getValue());
		room.updateRoom(targetRoom);		
	}
	
}
