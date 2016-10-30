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
		logger.info("������ ��Ʈ�ѷ� ����");
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		logger.info(status.getReason()+","+status.getCode()+" ��������");
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		logger.info("����");
	}
	
	@Override 
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		logger.info(session.hashCode()+"���ο� �޼��� ����");
		rooms.handleRequest(session, message);
	}
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		super.handleTransportError(session, exception);
		System.out.println(exception.getMessage());
	}	
}
