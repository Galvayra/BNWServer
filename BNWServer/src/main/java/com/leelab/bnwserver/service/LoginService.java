package com.leelab.bnwserver.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leelab.bnwserver.dao.BnwUserDao;
import com.leelab.bnwserver.dao.RecordDao;
import com.leelab.bnwserver.dto.BnwUserDto;

@Service
public class LoginService extends AbstractService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Override
	public void execute(HashMap<String, Object> request, HashMap<String, Object> response) {
		String requestId = request.get("id").toString();
		String requestPassword = request.get("password").toString();
		
		Login result = null;
		logger.info("요청 ID - {}, PW - {}", requestId, requestPassword);
		BnwUserDto user = callDao(BnwUserDao.class).getUser(requestId);
		
		if(user!=null)
		{
			if(user.getPassword().equals(requestPassword))
			{
				result = Login.OK;
				response.put("id", user.getId());
				response.put("password", user.getPassword());
				logger.info("{} 로그인 승인", user.getId());
			}
			else
			{
				result = Login.AUTH_FAIL;
				logger.info("{} 비밀번호 오류", user.getId());
			}
		}
		else
		{
			result = Login.OK;
			BnwUserDto freepass = null;
			try {
				freepass = new BnwUserDto(requestId, requestPassword, "", "freepass@naver.com", new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01"), "000-0000-0000", new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01"),requestId);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			callDao(BnwUserDao.class).addUser(freepass);			
			callDao(RecordDao.class).addRecord(requestId);
			logger.info("{} 존재하지 않는 아이디", requestId);
			response.put("id", freepass.getId());
			response.put("password", freepass.getPassword());
		}
		
		response.put("result", result);
	}

}
