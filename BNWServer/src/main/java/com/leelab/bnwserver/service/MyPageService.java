package com.leelab.bnwserver.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leelab.bnwserver.dao.BnwUserDao;
import com.leelab.bnwserver.dao.RecordDao;

public class MyPageService extends Service {
	
	private static final Logger logger = LoggerFactory.getLogger(MyPageService.class);
	
	@Override
	public void execute(HashMap<String, Object> request, HashMap<String, Object> response) {
		
		Object id = request.get("id");
		if(id!=null)
		{
			logger.info("��ȿ�� ��û Ȯ��{}, ���� ����", id);
			response.put("user", callDao(BnwUserDao.class).getUser(id.toString()));
			response.put("record", callDao(RecordDao.class).getRecord(id.toString()));
		}
		else
		{
			logger.info("��ȿ���� ���� ��û, idŰ ����, ����");
		}
	}
	
}
