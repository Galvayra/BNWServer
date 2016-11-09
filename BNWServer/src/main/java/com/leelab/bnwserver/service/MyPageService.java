package com.leelab.bnwserver.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.leelab.bnwserver.dao.BnwUserDao;
import com.leelab.bnwserver.dao.RecordDao;

@Service
public class MyPageService extends AbstractService {
	
	private static final Logger logger = LoggerFactory.getLogger(MyPageService.class);
	
	@Override
	public void execute(HashMap<String, Object> request, HashMap<String, Object> response) {
		String id = getParameter(0, String.class);
		
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
