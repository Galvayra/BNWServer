package com.leelab.bnwserver;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.leelab.bnwserver.dao.BnwUserDao;
import com.leelab.bnwserver.dto.BnwUserDto;

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
	public void test() throws ParseException {
		user.deleteAll();
		user.addUser(new BnwUserDto("gusrb0808", "cjsrn1992", "", "gusrb0808@naver.com", new SimpleDateFormat("yyyy-MM-dd").parse("1992-08-08"), "010-3221-6564", new SimpleDateFormat("yyyy-MM-dd").parse("1992-08-08")));
		assertThat(user.getCount(), is(1));
		
		System.out.println(user.getUser("gusrb0808"));
		
	}
}
