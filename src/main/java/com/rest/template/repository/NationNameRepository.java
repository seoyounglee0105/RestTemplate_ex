package com.rest.template.repository;

import org.apache.ibatis.annotations.Mapper;

import com.rest.template.dto.DataDto;

@Mapper
public interface NationNameRepository {

	public int insert(DataDto dataDto);
	
}
