package com.leelab.bnwserver.dao;

import org.apache.ibatis.type.EnumTypeHandler;

import com.leelab.bnwserver.dto.Turn;

public class TurnType extends EnumTypeHandler<Turn>{

	public TurnType(Class<Turn> type) {
		super(type);
	}

}
