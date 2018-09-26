package com.wedul.common.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedul.common.dto.WeatherDto;

@Service
@Slf4j
public class CommonService {
	
	public List<WeatherDto> getWeatherData() {
		List<String> regionCode = Arrays.asList("1835848", "1843561", "1835235", "1846266", "1838524", "1841811");
		List<WeatherDto> result = new ArrayList<>();
		
		for (String code : regionCode) {
			try {
				/*RestClientI restClientI = Feign.builder()
											  .client(new OkHttpClient())
											  .target(RestClientI.class, "https://api.openweathermap.org/data/2.5");
				Map<String, Object> data = obm.readValue(restClientI.selectWeather(code), new TypeReference<HashMap<String,Object>>(){});
				*/
				ObjectMapper obm = new ObjectMapper();
				RestTemplate restTemplate = new RestTemplate();
				Map<String, Object> data = obm.readValue(restTemplate.getForEntity(new StringBuffer("https://api.openweathermap.org/data/2.5/weather?id=").append("1835848").append("&APPID=7a4b48354c62cb247d7276be5c87766d").toString(), String.class).getBody(), new TypeReference<HashMap<String,Object>>(){});
				if (null != data) {
					String temp = String.valueOf(((Map<String, Object>) data.get("main")).get("temp"));
					data = (Map<String, Object>) ((List<?>) data.get("weather")).get(0);
					result.add(new WeatherDto(code, String.valueOf(data.get("icon")), String.valueOf(data.get("main")), temp));
				}
				
			} catch (Exception e) {
				log.error("open weatehr error", e);
			}
		}
		return result;
	}
}
