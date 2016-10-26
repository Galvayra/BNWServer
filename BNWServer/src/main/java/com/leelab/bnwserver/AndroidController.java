package com.leelab.bnwserver;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leelab.bnwserver.service.LoginService;
import com.leelab.bnwserver.service.MyPageService;
import com.leelab.bnwserver.service.room.CreateRoomService;
import com.leelab.bnwserver.service.room.RoomListService;

@RestController
@RequestMapping("mobile")
public class AndroidController {
	
	private static final Logger logger = LoggerFactory.getLogger(AndroidController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private MyPageService myPageService;
	
	@Autowired private RoomListService roomListService;
	
	@Autowired private CreateRoomService createRoomService;
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public HashMap<String, Object> loginRequest(@RequestBody HashMap<String, Object> param) {
		logger.info("로그인 요청");		
		return loginService.handleRequest(param);
	}
	
	@RequestMapping(value="mypage/{id}", method=RequestMethod.POST)
	public HashMap<String, Object> myPageRequest(@PathVariable String id) {
		logger.info("MyPage 정보 요청");
		return myPageService.handleRequest(id);
	}
	
	@RequestMapping("room")
	public HashMap<String, Object> getRoomList() {
		logger.info("방 정보 목록 요청");
		return roomListService.handleRequest();
	}
	
	@RequestMapping(value="createroom", method=RequestMethod.POST)
	public HashMap<String, Object> createRoom(@RequestBody HashMap<String, Object> param) {
		logger.info("방 생성");
		return createRoomService.handleRequest(param);
	}
}
