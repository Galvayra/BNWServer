package com.leelab.bnwserver.dto;

public enum Turn {

	BLACK(1),
	WHITE(2);
	
	private final int value;
	
	Turn(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static Turn valueOf(int value) {
		switch(value)
		{
		case 1 : return BLACK;
		case 2 : return WHITE;
		default : return null;
		}
	}
	
}
