package com.rest.template.controller;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.rest.template.dto.PostDto;
import com.rest.template.dto.TodoDto;

@RestController
public class ApiController {
	
	// 시나리오 1단계 : String으로 반환받기
	// 서버 to 서버
	// 만드는 방법
	// 1. URI 객체 만들기
	// 2. RestTemplate 객체 만들기
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> restTemplate1(@PathVariable Integer id) {
		
		// URI uri = new URI("https://jsonplaceholder.typicode.com/todos/1");
		// 위와 효과는 같지만 가독성을 높임
		URI uri = UriComponentsBuilder
					.fromUriString("https://jsonplaceholder.typicode.com") // 엔드포인트 제외하고 호스트 IP만 (구분해주는 것이 좋아서)
					.path("/todos")
					.path("/" + id)  // 쿼리스트링 있다면 .queryParam()으로 달아주기
					.encode() // UTF-8로 인코딩
					.build()
					.toUri();
		
		System.out.println(uri.toString());
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class); 
					
		System.out.println(response.getStatusCode());
		System.out.println("-------------");
		System.out.println(response.getHeaders());
		System.out.println("-------------");
		System.out.println(response.getBody());
		
		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
	}
	
	// 시나리오 2단계
	// 1. URI 객체 만들기
	// 2. RestTemplate 객체 만들기
	@GetMapping("/todos2/{id}")
	public ResponseEntity<?> restTemplate2(@PathVariable Integer id) {
	
		URI uri = UriComponentsBuilder
					.fromUriString("https://jsonplaceholder.typicode.com")
					.path("/todos")
					.path("/" + id)
					.encode()
					.build()
					.toUri();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<TodoDto> response = restTemplate.getForEntity(uri, TodoDto.class);
		
		System.out.println(response.getBody());
		System.out.println(response.getBody().getId());
		System.out.println(response.getBody().getTitle());
		
		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
	}
	
	// 시나리오 3단계
	// 클라이언트 -> 서버 -> 외부 서버
	// 1. URI 객체 생성
	// 2. body에 들어갈 데이터 생성
	// RestTemplate 생성 - (POST 방식은 추가 작업 필요)
	
	@GetMapping("/postsTest")
	public ResponseEntity<?> restTemplate3() {
		
		// URI 객체 생성
		URI uri = UriComponentsBuilder
				.fromUriString("https://jsonplaceholder.typicode.com")
				.path("/posts")
				.encode()
				.build()
				.toUri();
		
		// body에 넣을 데이터 생성
		PostDto postDto = PostDto.builder().title("하이").body("하이~").userId(1).build();
		RestTemplate restTemplate = new RestTemplate();
		// 인자값 : URI, body에 들어갈 데이터, 파싱될 클래스 타입
		ResponseEntity<PostDto> response = restTemplate.postForEntity(uri, postDto, PostDto.class);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.getHeaders());
		System.out.println(response.getBody());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());
	}
	
	// 1. URI 객체 만들기
	// 2. 
	@GetMapping("/exchangeTest")
	public ResponseEntity<?> restTemplate4() {
		
		// exchange 메서드 사용방법 ****
		// HttpEntity 클래스 (body, headers 생성 가능)
		
		// URI 객체 생성
		URI uri = UriComponentsBuilder
				.fromUriString("https://jsonplaceholder.typicode.com")
				.path("/posts")
				.encode()
				.build()
				.toUri();
		
		// RestTemplate 객체 생성
		RestTemplate restTemplate = new RestTemplate();
		
		// 1) HttpHeaders 만들기
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-type", "application/json; charset=UTF-8");
		// httpHeaders.add("Key", "Value");

		
		// 2) body에 들어갈 key-value 구조 만들기 (데이터 만들기)
		// HashMap, MultiValueMap
		// 공통점 : Map 계열
		// HashMap 
		// fruit : "바나나"
		// 같은 Key에 Value가 들어가면 덮어쓰기됨
		
		// MultiValueMap
		// 같은 Key에 Value가 들어가면 배열로 들어가짐
		// fruit : ["바나나", "오렌지", "사과"]
		// 인터페이스임
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("title", "반가워");
		params.add("body", "하이");
		params.add("userId", "5"); // 문자열로 보내도 알아서 Integer로 파싱해줌
	
		
		// 3) HttpEntity 생성해서 결합하기
		HttpEntity<MultiValueMap<String, String>> reqEntity = new HttpEntity<>(params, httpHeaders); // 인자 : 바디데이터, HttpHeaders
		
		// 인자 : URI 객체, HTTP Method 방식, HttpEntity, return 타입
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, String.class);
		
		System.out.println(response.getBody());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());
	}
	
}
