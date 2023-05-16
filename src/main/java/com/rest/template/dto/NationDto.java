package com.rest.template.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NationDto {

	private Integer currentCount;
	private Integer numOfRows;
	private DataDto[] data;	
}
