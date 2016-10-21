package com.leelab.bnwserver.dao;

import com.leelab.bnwserver.dto.BnwUserDto;

public interface BnwUserDao {
	public void addUser(BnwUserDto user);
	public void deleteAll();
	public int getCount();
	public BnwUserDto getUser(String id);
}
