package com.leelab.bnwserver;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leelab.bnwserver.service.LoginService;
import com.leelab.bnwserver.service.MyPageService;

@Controller
@RequestMapping("mobile")
public class AndroidController {
	
	private static final Logger logger = LoggerFactory.getLogger(AndroidController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private MyPageService myPageService;
	
	@ResponseBody
	@RequestMapping(value="login", method=RequestMethod.POST)
	public HashMap<String, Object> loginRequest(@RequestBody HashMap<String, Object> param) {
		logger.info("로그인 요청");		
		return loginService.handleRequest(param);
	}
	
	@ResponseBody
	@RequestMapping(value="mypage", method=RequestMethod.POST)
	public HashMap<String, Object> myPageRequest(@RequestBody HashMap<String, Object> param) {
		logger.info("MyPage 정보 요청");
		return myPageService.handleRequest(param);
	}
}
