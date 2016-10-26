package com.leelab.bnwserver.service.room;

import java.util.ArrayList;
import java.util.HashMap;

import com.leelab.bnwserver.dao.RoomDao;
import com.leelab.bnwserver.dto.RoomDto;
import com.leelab.bnwserver.service.Service;

public class RoomListService extends Service {
	
	@Override
	public void execute(HashMap<String, Object> request, HashMap<String, Object> response) {
		ArrayList<RoomDto> rooms = callDao(RoomDao.class).getAll();
		response.put("rooms", rooms);
	}
	
}
