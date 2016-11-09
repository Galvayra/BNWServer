package com.leelab.bnwserver.dto;

import java.sql.Timestamp;

public class GameDto {
	private int no;
	private Timestamp create_at;
	private String gamer_1;
	private String gamer_2;
	private Timestamp finish_at;
	private String winner;
	private String loser;
	private int gamer_1_score;
	private int gamer_2_score;
	private int round;
	private int turn;
	private int gamer_1_round;
	private int gamer_2_round;
	
	public GameDto(){}
	
	public GameDto(int no, String gamer_1, String gamer_2) {
		this.no = no;
		this.gamer_1 = gamer_1;
		this.gamer_2 = gamer_2;
	}
	
	public GameDto(int no, Timestamp create_at, String gamer_1, String gamer_2, Timestamp finish_at, String winner,
			String loser, int gamer_1_score, int gamer_2_score, int round, int turn, int gamer_1_round, int gamer_2_round) {
		super();
		this.no = no;
		this.create_at = create_at;
		this.gamer_1 = gamer_1;
		this.gamer_2 = gamer_2;
		this.finish_at = finish_at;
		this.winner = winner;
		this.loser = loser;
		this.gamer_1_score = gamer_1_score;
		this.gamer_2_score = gamer_2_score;
		this.round = round;
		this.turn = turn;
		this.gamer_1_round = gamer_1_round;
		this.gamer_2_round = gamer_2_round;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public Timestamp getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Timestamp create_at) {
		this.create_at = create_at;
	}
	public String getGamer_1() {
		return gamer_1;
	}
	public void setGamer_1(String gamer_1) {
		this.gamer_1 = gamer_1;
	}
	public String getGamer_2() {
		return gamer_2;
	}
	public void setGamer_2(String gamer_2) {
		this.gamer_2 = gamer_2;
	}
	public Timestamp getFinish_at() {
		return finish_at;
	}
	public void setFinish_at(Timestamp finish_at) {
		this.finish_at = finish_at;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public String getLoser() {
		return loser;
	}
	public void setLoser(String loser) {
		this.loser = loser;
	}
	public int getGamer_1_score() {
		return gamer_1_score;
	}
	public void setGamer_1_score(int gamer_1_score) {
		this.gamer_1_score = gamer_1_score;
	}
	public int getGamer_2_score() {
		return gamer_2_score;
	}
	public void setGamer_2_score(int gamer_2_score) {
		this.gamer_2_score = gamer_2_score;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getGamer_1_round() {
		return gamer_1_round;
	}

	public void setGamer_1_round(int gamer_1_round) {
		this.gamer_1_round = gamer_1_round;
	}

	public int getGamer_2_round() {
		return gamer_2_round;
	}

	public void setGamer_2_round(int gamer_2_round) {
		this.gamer_2_round = gamer_2_round;
	}

	@Override
	public String toString() {
		return "GameDto [no=" + no + ", create_at=" + create_at + ", gamer_1=" + gamer_1 + ", gamer_2=" + gamer_2
				+ ", finish_at=" + finish_at + ", winner=" + winner + ", loser=" + loser + ", gamer_1_score="
				+ gamer_1_score + ", gamer_2_score=" + gamer_2_score + ", round=" + round + ", turn=" + turn
				+ ", gamer_1_round=" + gamer_1_round + ", gamer_2_round=" + gamer_2_round + "]";
	}
	
	
}
