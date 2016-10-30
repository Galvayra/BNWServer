package com.leelab.bnwserver.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leelab.bnwserver.dao.BnwUserDao;
import com.leelab.bnwserver.dto.BnwUserDto;

public class LoginService extends Service {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Override
	public void execute(HashMap<String, Object> request, HashMap<String, Object> response) {
		String requestId = request.get("id").toString();
		String requestPassword = request.get("password").toString();

		Login result = null;
		logger.info("��û ID - {}, PW - {}", requestId, requestPassword);
		BnwUserDto user = callDao(BnwUserDao.class).getUser(requestId);
		
		if(user!=null)
		{
			if(user.getPassword().equals(requestPassword))
			{
				result = Login.OK;
				response.put("id", user.getId());
				response.put("password", user.getPassword());
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
		
		response.put("result", result);
	}

}
