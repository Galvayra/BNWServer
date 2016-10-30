package com.leelab.bnwserver.service.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.leelab.bnwserver.dto.OperatedRooms;

public class RoomControlSocket extends TextWebSocketHandler implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(RoomControlSocket.class);
	
	@Autowired
	private OperatedRooms rooms;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("웹소켓 컨트롤러 생성");
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		logger.info(status.getReason()+","+status.getCode()+" 연결종료");
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		logger.info("연결");
	}
	
	@Override 
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		logger.info(session.hashCode()+"새로운 메세지 도착");
		rooms.handleRequest(session, message);
	}
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		super.handleTransportError(session, exception);
		System.out.println(exception.getMessage());
	}	
}
