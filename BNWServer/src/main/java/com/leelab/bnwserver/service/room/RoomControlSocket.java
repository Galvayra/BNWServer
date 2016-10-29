package com.leelab.bnwserver.service.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class RoomControlSocket extends TextWebSocketHandler implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(RoomControlSocket.class);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("웹소켓 컨트롤러 생성");
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		logger.info(session.hashCode()+"연결 설정");
		session.sendMessage(new TextMessage("Hi"));
	}
	
	@Override 
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		super.handleMessage(session, message);
		logger.info("메세지 : {}",message.getPayload());
	}
	
	
	
	
	
}
