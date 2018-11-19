package com.wedul.wedulpos.variables.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wedul.wedulpos.variables.dao.VariablesMapper;
import com.wedul.wedulpos.variables.serviceI.VariablesServiceI;

/**
 * Variables 서비스
 * 
 * @author wedul
 *
 */
@Service("VariablesService")
public class VariablesServiceImpl implements VariablesServiceI {
	
	private Map<String, String> map;
	
	public VariablesServiceImpl(VariablesMapper dao) {
		map = new HashMap<>();
		try {
			dao.selectVariablesList().stream().forEach((e) -> {
				map.put(e.getName(), e.getValue());
			});
		} catch (Exception ex) {
		}
	}

	@Override
	public String getStringValue(String key) {
		return String.valueOf(map.get(key));
	}

	@Override
	public int getIntegerValue(String key) {
		return Integer.valueOf(map.get(key));
	}

}
