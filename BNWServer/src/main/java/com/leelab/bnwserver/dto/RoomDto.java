package com.leelab.bnwserver.dto;

public class RoomDto {
	private int room_no;
	private String creator;
	private String participant;
	private int room_state;
	
	public RoomDto(){}

	public RoomDto(int room_no, String creator, String participant, int room_state) {
		this.room_no = room_no;
		this.creator = creator;
		this.participant = participant;
		this.room_state = room_state;
	}

	public int getRoom_no() {
		return room_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public int getRoom_state() {
		return room_state;
	}

	public void setRoom_state(int room_state) {
		this.room_state = room_state;
	}

	@Override
	public String toString() {
		return "RoomDto [room_no=" + room_no + ", creator=" + creator + ", participant=" + participant + ", room_state="
				+ room_state + "]";
	}
}
