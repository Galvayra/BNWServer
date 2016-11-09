package com.leelab.bnwserver.dao;

import com.leelab.bnwserver.dto.RecordDto;

public interface RecordDao {
	public RecordDto getRecord(String id);
	public void addRecord(String id);
	public void deleteAll();
	public void updateRecord(RecordDto record);
}
