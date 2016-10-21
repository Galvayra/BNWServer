package com.leelab.bnwserver.service;

import org.apache.ibatis.session.SqlSession;

public abstract class Service {
	
	private SqlSession sqlSession;
	
	public abstract void execute(Object...params);
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public SqlSession getSqlSession() {
		return sqlSession;
	}
	
	public <T> T callDao(Class<T> clazz) {
		return sqlSession.getMapper(clazz);
	}
}
