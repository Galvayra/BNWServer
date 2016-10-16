package com.leelab.bnwserver.dto;

public class GameHistory {
	private int no;
	private String id;
	private int score;
	
	public GameHistory(){}

	public GameHistory(int no, String id, int score) {
		this.no = no;
		this.id = id;
		this.score = score;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "GameHistory [no=" + no + ", id=" + id + ", score=" + score + "]";
	}
	
}
