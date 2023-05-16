package com.rest.template.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.rest.template.dto.DataDto;
import com.rest.template.dto.NationDto;
import com.rest.template.repository.NationNameRepository;

@RestController
public class NationCodeApiController {

	@Autowired
	private NationNameRepository nationNameRepository;
	
	@GetMapping("/nationCode1")
	public ResponseEntity<?> nationCode1() {
		
		// HTTPClient
		// Retrofit2
		// HTTP OK
		// RestTemplate
		
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1262000/CountryCodeService2/getCountryCodeList2"); /*URL*/
        StringBuilder sb = new StringBuilder();
        try {
        	urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=oTiZQbN0NoGb55FeA2KBkgx2JOiTLFVFLl2lHSfh51Dgiyhp6DM%2FSRaMejByeDCGtgkBSmfL0gNyhOT7P3nElA%3D%3D"); /*Service Key*/
        	urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        	urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("50", "UTF-8")); /*한 페이지 결과 수*/
        	urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        	//urlBuilder.append("&" + URLEncoder.encode("cond[country_nm::EQ]","UTF-8") + "=" + URLEncoder.encode("가나", "UTF-8")); /*한글 국가명*/
        	//urlBuilder.append("&" + URLEncoder.encode("cond[country_iso_alp2::EQ]","UTF-8") + "=" + URLEncoder.encode("GH", "UTF-8")); /*ISO 2자리코드*/
			
        	URL url = new URL(urlBuilder.toString());
        	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        	conn.setRequestMethod("GET");
        	conn.setRequestProperty("Content-type", "application/json");
        	System.out.println("Response code: " + conn.getResponseCode());
        	BufferedReader rd;
        	if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
        		rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        	} else {
        		rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        	}
        	String line;
        	while ((line = rd.readLine()) != null) {
        		sb.append(line);
        	}
        	rd.close();
        	conn.disconnect();
        	System.out.println(sb.toString());
        	        	
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return ResponseEntity.status(HttpStatus.OK).body(sb.toString());
	}
	
	@GetMapping("/nationCode2")
	public ResponseEntity<?> nationCode2() {
		// 연습 문제 1 - RestTemplate으로 변환해서 코드 사용하기
		
        StringBuilder uriBuilder = new StringBuilder("http://apis.data.go.kr/1262000/CountryCodeService2/getCountryCodeList2"); /*URL*/
        URI uri;
        try {
			uriBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=oTiZQbN0NoGb55FeA2KBkgx2JOiTLFVFLl2lHSfh51Dgiyhp6DM%2FSRaMejByeDCGtgkBSmfL0gNyhOT7P3nElA%3D%3D");
			uriBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
			uriBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("300", "UTF-8")); /*한 페이지 결과 수*/
			uriBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
		
			// UriComponentsBuilder 쓰면 오류날 때 있어서 URI로 만들기
			uri = new URI(uriBuilder.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("오류");
			return null;
		}
        
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<NationDto> response = restTemplate.getForEntity(uri, NationDto.class);
        System.out.println(response.getBody());
        
        for (DataDto d : response.getBody().getData()) {
        	int result = nationNameRepository.insert(d);
		}
        
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}

	// 연습 문제 2 - RestTemplate 사용해서 공공 데이터 받아오기
	
	// 연습 문제 3 - 공공 데이터 DB 넣어보기
}
