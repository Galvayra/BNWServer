package com.leelab.bnwserver.service.room;

import java.util.HashMap;

import com.leelab.bnwserver.dao.RoomDao;
import com.leelab.bnwserver.service.Service;

public class CreateRoomService extends Service {

	@Override
	public void execute(HashMap<String, Object> request, HashMap<String, Object> response) {
		String creator = request.get("id").toString();
		String roomTitle = request.get("title").toString();		
		RoomDao dao = callDao(RoomDao.class);
		int nextRoom = dao.getNextRoomNumber();
		dao.addRoom(nextRoom, creator, roomTitle);		
		response.put("creator", creator);
		response.put("roomTitle", roomTitle);
		response.put("roomNo", nextRoom);
	}
}
