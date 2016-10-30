package com.leelab.bnwserver.dto;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class OperatedRooms {
	
	private static final Logger logger = LoggerFactory.getLogger(OperatedRooms.class);
	
	private HashMap<Integer, HashMap<String,WebSocketSession>> operatedRooms;
	
	public OperatedRooms() {
		operatedRooms = new HashMap<Integer, HashMap<String,WebSocketSession>>(); 
	}
	
	public void newOperatedRoom(int room_no) {
		operatedRooms.put(room_no, new HashMap<String, WebSocketSession>());
	}
	
	public HashMap<String, WebSocketSession> getOperatedRoom(int room_no) {
		return operatedRooms.get(room_no);
	}
	
	public void handleRequest(WebSocketSession session, WebSocketMessage<?> message) {
		JSONObject request = new JSONObject(message.getPayload().toString());
		
		String type = request.getString("type");
		if(type.equals("enter"))
		{
			processTypeEnter(request.getInt("room_no"), session);
		}
		else if(type.equals("chat"))
		{
			processTypeChat(request.getInt("room_no"), request.getString("id"), request.getString("msg"));
		}
	}
	
	public void processTypeChat(int room_no, String speaker, String msg) {
		HashMap<String, WebSocketSession> room = getOperatedRoom(room_no);
		JSONObject obj = new JSONObject();
		obj.put("type", "chat");
		obj.put("msg", msg);
		obj.put("speaker", speaker);
		try
		{
			logger.info("皋技瘤 傈价 : {} {}", speaker, msg);
			room.get("super").sendMessage(new TextMessage(obj.toString()));
			WebSocketSession participant = room.get("participant");
			if(participant!=null)
			{
				participant.sendMessage(new TextMessage(obj.toString()));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void processTypeEnter(int room_no, WebSocketSession session) {
		if(getOperatedRoom(room_no)==null)
		{
			newOperatedRoom(room_no);
			getOperatedRoom(room_no).put("super", session);
			logger.info("{}锅规 积己 棺 规厘 立加", room_no);
		}
		else
		{
			getOperatedRoom(room_no).put("participant", session);
			logger.info("{}锅规俊 曼啊磊 立加", room_no);
		}		
	}

}
