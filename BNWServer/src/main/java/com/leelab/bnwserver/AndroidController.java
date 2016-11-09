package com.leelab.bnwserver;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leelab.bnwserver.service.LoginService;
import com.leelab.bnwserver.service.MyPageService;
import com.leelab.bnwserver.service.room.CreateRoomService;
import com.leelab.bnwserver.service.room.EnterRoomService;
import com.leelab.bnwserver.service.room.RoomListService;

@RestController
@RequestMapping("mobile")
public class AndroidController {
	
	private static final Logger logger = LoggerFactory.getLogger(AndroidController.class);
	
	@Resource
	private LoginService loginService;
	
	@Resource
	private MyPageService myPageService;
	
	@Resource
	private RoomListService roomListService;
	
	@Resource
	private CreateRoomService createRoomService;
	
	@Resource
	private EnterRoomService enterRoomService;
	
	@RequestMapping("test")
	public void test() {
		System.out.println(loginService.hashCode());
		loginService.handleRequest();
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public HashMap<String, Object> loginRequest(@RequestBody HashMap<String, Object> param) {
		logger.info("�α��� ��û");		
		return loginService.handleRequest(param);
	}
	
	@RequestMapping(value="mypage/{id}", method=RequestMethod.POST)
	public HashMap<String, Object> myPageRequest(@PathVariable String id) {
		logger.info("MyPage ���� ��û");
		return myPageService.handleRequest(id);
	}
	
	@RequestMapping("room")
	public HashMap<String, Object> getRoomList() {
		logger.info("�� ���� ��� ��û");
		return roomListService.handleRequest();
	}
	
	@RequestMapping(value="createroom", method=RequestMethod.POST)
	public HashMap<String, Object> createRoom(@RequestBody HashMap<String, Object> param) {
		logger.info("�� ����");
		return createRoomService.handleRequest(param);
	}
	
	@RequestMapping(value="enterroom", method=RequestMethod.POST)
	public HashMap<String, Object> enter(@RequestBody HashMap<String, Object> param) {
		logger.info("�� ���� ��û");
		return enterRoomService.handleRequest(param);
	}
}
