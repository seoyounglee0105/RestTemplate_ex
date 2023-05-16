package com.rest.template.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
	
	// 응답 시에 사용함
	private Integer id;
	
	// 요청 or 응답 시에 사용함
	private String title;
	private String body;
	private Integer userId;
	
}
