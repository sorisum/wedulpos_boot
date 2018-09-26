package com.wedul.common.controller;

import com.wedul.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공통으로 사용하는 영역에 대한 컨트로러
 * 
 * @author wedul
 *
 */
@RestController
public class CommonController {
	
	@Autowired
	CommonService commonService;
	
	/**
	 * 날씨 가져오기
	 * 
	 * @return
	 */
	@GetMapping(value="/weather")
	public ResponseEntity<?> getWeather() {
		return ResponseEntity.ok(commonService.getWeatherData());
	}

}
