package com.rest.template.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {

	private Integer userId;
	private Integer id;
	private String title;
	private Boolean completed;
	
}
