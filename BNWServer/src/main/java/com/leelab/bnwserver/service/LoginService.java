package com.leelab.bnwserver.service;

import java.util.HashMap;

import com.leelab.bnwserver.dao.BnwUserDao;
import com.leelab.bnwserver.dto.BnwUserDto;

public class LoginService extends Service {
	
	@Override
	@SuppressWarnings("unchecked")
	public void execute(Object... params) {
		
		HashMap<String, Object> jsonMap = (HashMap<String, Object>) params[0];
		
		String requestId = jsonMap.get("id").toString();
		String requestPassword = jsonMap.get("password").toString();
		System.out.println(getSqlSession());
		BnwUserDto user = callDao(BnwUserDao.class).getUser(requestId);
		
		HashMap<String, Object> returnMap = (HashMap<String,Object>) params[1];
		returnMap.put("user", user.toString());
	}

}
