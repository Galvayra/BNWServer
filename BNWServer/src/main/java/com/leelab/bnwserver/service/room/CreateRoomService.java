package com.leelab.bnwserver.service.room;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.leelab.bnwserver.dao.BnwUserDao;
import com.leelab.bnwserver.dao.RecordDao;
import com.leelab.bnwserver.dao.RoomDao;
import com.leelab.bnwserver.dto.RecordDto;
import com.leelab.bnwserver.service.AbstractService;

@Service
public class CreateRoomService extends AbstractService {

	@Override
	public void execute(HashMap<String, Object> request, HashMap<String, Object> response) {
		String creator = request.get("id").toString();
		String roomTitle = request.get("title").toString();		
		RoomDao dao = callDao(RoomDao.class);
		int nextRoom = dao.getNextRoomNumber();
		dao.addRoom(nextRoom, creator, roomTitle);		

		BnwUserDao userDao = callDao(BnwUserDao.class);
		ParticipantListVo creatorVo = new ParticipantListVo();
		creatorVo.setNickname(userDao.getUser(creator).getNickname());
		RecordDto creatorRecord = callDao(RecordDao.class).getRecord(creator);
		creatorVo.setWin(creatorRecord.getWin());
		creatorVo.setDraw(creatorRecord.getDraw());
		creatorVo.setLose(creatorRecord.getLose());
		creatorVo.setRate(creatorRecord.getWinning_rate());
		creatorVo.setReady(true);
		
		response.put("creator", creator);
		response.put("roomTitle", roomTitle);
		response.put("roomNo", nextRoom);
		response.put("vo", creatorVo);
	}
}
