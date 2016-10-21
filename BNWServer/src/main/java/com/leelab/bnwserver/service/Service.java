package com.leelab.bnwserver.service;

import org.apache.ibatis.session.SqlSession;

public abstract class Service {
	
	private SqlSession sqlSession;
	private Object[] params;
	
	public abstract void execute(Object...params);
	
	public void handleRequest(Object...params) {
		this.params = params;
		execute(params);
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
	
	
}
