package com.wedul.common.controller;

import com.wedul.common.service.CommonService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CommonController {
	
	private final CommonService commonService;

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
