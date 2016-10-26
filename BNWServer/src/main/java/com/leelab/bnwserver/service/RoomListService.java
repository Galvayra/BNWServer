package com.leelab.bnwserver.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.leelab.bnwserver.dao.RoomDao;
import com.leelab.bnwserver.dto.RoomDto;

public class RoomListService extends Service {
	
	@Override
	public void execute(HashMap<String, Object> request, HashMap<String, Object> response) {
		ArrayList<RoomDto> rooms = callDao(RoomDao.class).getAll();
		response.put("rooms", rooms);
	}
	
}
