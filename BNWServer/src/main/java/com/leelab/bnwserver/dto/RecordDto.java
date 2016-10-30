package com.leelab.bnwserver.dto;

public class RecordDto {
	private String id;
	private int win;
	private int draw;
	private int lose;
	private int score;
	private double winning_rate;
	
	public RecordDto(){}
	
	public RecordDto(String id, int win, int draw, int lose, int score, double winning_rate) {
		this.id = id;
		this.win = win;
		this.draw = draw;
		this.lose = lose;
		this.score = score;
		this.winning_rate = winning_rate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public double getWinning_rate() {
		return winning_rate;
	}

	public void setWinning_rate(double winning_rate) {
		this.winning_rate = winning_rate;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", win=" + win + ", draw=" + draw + ", lose=" + lose + ", score=" + score
				+ ", winning_rate=" + winning_rate + "]";
	}
	
}
