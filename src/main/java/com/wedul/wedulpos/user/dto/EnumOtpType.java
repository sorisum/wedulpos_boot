package com.wedul.wedulpos.user.dto;

/**
 * OTP Type
 * 
 * @author wedul
 *
 */
public enum EnumOtpType {
	JOIN_OTP(0),
	TEMP_PASSWORD(1);
	
	private int type;
	
	EnumOtpType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
}
