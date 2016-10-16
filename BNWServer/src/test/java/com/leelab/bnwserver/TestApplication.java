package com.leelab.bnwserver;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.leelab.bnwserver.dao.BnwUserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"/test-context.xml"
})
public class TestApplication {
	@Autowired
	SqlSession sqlSession;
	
	private BnwUserDao user;
	
	@Before
	public void setUp() {
		user = sqlSession.getMapper(BnwUserDao.class);
	}
	
	@Test
	public void test() {
		user.deleteAll();
		assertThat(user.getCount(), is(0));
	}
}
