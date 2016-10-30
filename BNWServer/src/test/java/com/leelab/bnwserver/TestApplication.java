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
import com.leelab.bnwserver.dao.RecordDao;
import com.leelab.bnwserver.dao.RoomDao;
import com.leelab.bnwserver.dto.BnwUserDto;
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
	
	private BnwUserDto tUser, root;
	
	@Before
	public void setUp() throws ParseException {
		user = sqlSession.getMapper(BnwUserDao.class);
		recorder = sqlSession.getMapper(RecordDao.class); 
		room = sqlSession.getMapper(RoomDao.class);
		
		tUser = new BnwUserDto("gusrb0808", "cjsrn1992", "", "gusrb0808@naver.com", new SimpleDateFormat("yyyy-MM-dd").parse("1992-08-08"), "010-3221-6564", new SimpleDateFormat("yyyy-MM-dd").parse("1992-08-08"),"辫兑迭");
		root = new BnwUserDto("admin", "1234", "", "admin@bnwserver.net", new SimpleDateFormat("yyyy-MM-dd").parse("1992-08-08"), "010-3221-6564", new SimpleDateFormat("yyyy-MM-dd").parse("1992-08-08"),"包府磊");
	}
	
	@Test
	public void test() throws ParseException {
		room.deleteAll();
		//recorder.addRecord(root.getId());
		//room.addRoom(root.getId(), "规规规");
		//int nextRoom = room.getNextRoomNumber();
		//room.addRoom(nextRoom, root.getId(), "规规规");
		
//		RoomDto someRoom = room.getRoom(22);
//		
//		System.out.println(someRoom);
//		
//		someRoom.setParticipant(tUser.getId());
//		someRoom.setRoom_state(RoomState.FULL.getValue());
//		room.updateRoom(someRoom);
//		
//		RoomDto updatedRoom = room.getRoom(22);
//		
//		System.out.println(updatedRoom);
		
//		ArrayList<RoomDto> rooms = room.getAll();
//
//		for(RoomDto r : rooms)
//		{
//			System.out.println(r.toString());
//		}
	}
	

	
}
