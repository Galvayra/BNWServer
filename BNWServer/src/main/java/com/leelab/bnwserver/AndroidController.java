package com.leelab.bnwserver;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leelab.bnwserver.service.LoginService;

@Controller
@RequestMapping("mobile")
public class AndroidController {
	
	private static final Logger logger = LoggerFactory.getLogger(AndroidController.class);
	
	@Autowired
	private LoginService loginService;
	
	@ResponseBody
	@RequestMapping(value="login", method=RequestMethod.POST)
	public HashMap<String, Object> loginRequest(@RequestBody HashMap<String, Object> param, HttpSession session) {
		logger.info("로그인 요청");
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		loginService.handleRequest(param, map, session);
		
		return map;
	}
	
}
