package com.leelab.bnwserver.service;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leelab.bnwserver.dao.BnwUserDao;
import com.leelab.bnwserver.dto.BnwUserDto;

public class LoginService extends Service {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public void execute(Object... params) {

		HashMap<String, Object> jsonMap = (HashMap<String, Object>) params[0];
		HashMap<String, Object> returnMap = (HashMap<String,Object>) params[1];

		String requestId = jsonMap.get("id").toString();
		String requestPassword = jsonMap.get("password").toString();

		Login result = null;
		
		BnwUserDto user = callDao(BnwUserDao.class).getUser(requestId);
		
		if(user!=null)
		{
			if(user.getPassword().equals(requestPassword))
			{
				result = Login.OK;
				((HttpSession)params[2]).setAttribute("id", user.getId());
				logger.info("{} �α��� ����", user.getId());
			}
			else
			{
				result = Login.AUTH_FAIL;
				logger.info("{} ��й�ȣ ����", user.getId());
			}
		}
		else
		{
			result = Login.ID_NOT_FOUND;
			logger.info("{} �������� �ʴ� ���̵�", requestId);
		}
		
		returnMap.put("result", result);
	}

}
