package com.leelab.bnwserver.dao;

import java.util.ArrayList;

import com.leelab.bnwserver.dto.RoomDto;

public interface RoomDao {
	public void deleteAll();
	public int getCount();
	public void addRoom(int roomNumber, String creator, String roomTitle);
	public void updateRoom(RoomDto room);
	public int getNextRoomNumber();
	public RoomDto getRoom(int room_no);
	public ArrayList<RoomDto> getAll();
}
