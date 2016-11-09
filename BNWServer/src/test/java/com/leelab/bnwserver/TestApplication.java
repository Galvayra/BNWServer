package com.leelab.bnwserver;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
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
import com.leelab.bnwserver.dao.GameDao;
import com.leelab.bnwserver.dao.RecordDao;
import com.leelab.bnwserver.dao.RoomDao;
import com.leelab.bnwserver.dto.BnwUserDto;
import com.leelab.bnwserver.dto.GameDto;
import com.leelab.bnwserver.dto.RoomDto;
import com.leelab.bnwserver.dto.RoomState;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"/test-context.xml"
})
public class TestApplication {
	@Autowired
	SqlSession sqlSession;
	
	private BnwUserDao user;
	private RecordDao recorder;
	private RoomDao room;
	private GameDao gameDao;
	
	private BnwUserDto tUser, root;
	
	@Before
	public void setUp() throws ParseException {
		user = sqlSession.getMapper(BnwUserDao.class);
		recorder = sqlSession.getMapper(RecordDao.class); 
		room = sqlSession.getMapper(RoomDao.class);
		gameDao = sqlSession.getMapper(GameDao.class);
		
		tUser = new BnwUserDto("gusrb0808", "cjsrn1992", "", "gusrb0808@naver.com", new SimpleDateFormat("yyyy-MM-dd").parse("1992-08-08"), "010-3221-6564", new SimpleDateFormat("yyyy-MM-dd").parse("1992-08-08"),"±è¶Òµü");
		root = new BnwUserDto("admin", "1234", "", "admin@bnwserver.net", new SimpleDateFormat("yyyy-MM-dd").parse("1992-08-08"), "010-3221-6564", new SimpleDateFormat("yyyy-MM-dd").parse("1992-08-08"),"°ü¸®ÀÚ");
	}
	
	@Test
	public void test() throws ParseException {
		
//		GameDto newGame = new GameDto(tUser.getId(), root.getId());
//		//gameDao.insertGame(newGame);
//		
//		GameDto selectedGame = gameDao.selectGame(1);
//		System.out.println(selectedGame);
//		selectedGame.setGamer_1_score(50);
//		gameDao.updateGame(selectedGame);
//		GameDto updatedGame = gameDao.selectGame(1);
//		System.out.println(updatedGame);
		room.deleteAll();
//		gameDao.deleteGames();
	}
	

	
}
