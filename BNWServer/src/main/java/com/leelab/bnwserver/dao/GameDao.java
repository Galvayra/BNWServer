package com.leelab.bnwserver.dao;

import com.leelab.bnwserver.dto.GameDto;

public interface GameDao {
	public void updateGame(GameDto game);
	public void insertGame(GameDto game);
	public GameDto selectGame(int no);
	public void deleteGames();
	public int getNextGameNumber();
}
