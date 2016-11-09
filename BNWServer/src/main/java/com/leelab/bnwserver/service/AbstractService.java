package com.leelab.bnwserver.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

public abstract class AbstractService {

	@Resource
	private SqlSession sqlSession;
	private Object[] params;
	
	public abstract void execute(HashMap<String, Object> request, HashMap<String, Object> response);
	
	public HashMap<String, Object> handleRequest(HashMap<String, Object> input, Object...params) {
		this.params = params;
		HashMap<String, Object> response = new HashMap<String, Object>();
		execute(input, response);
		return response;
	}
	
	public HashMap<String, Object> handleRequest(Object...params) {
		this.params = params;
		HashMap<String, Object> response = new HashMap<String, Object>();
		execute(null, response);
		return response;
	}
	
	public <T> T callDao(Class<T> clazz) {
		return sqlSession.getMapper(clazz);
	}

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getParameter(int index, Class<T> clazz) {
		return (T)params[index];
	}
	
}
