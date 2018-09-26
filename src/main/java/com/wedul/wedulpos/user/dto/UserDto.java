package com.wedul.wedulpos.user.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import com.wedul.common.dto.CommonDto;
import com.wedul.common.util.HashUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * User정보 Dto 
 * 
 * @author wedul
 * @date 2017. 11. 4.
 * @name UserDto
 */
@Alias("UserDto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserDto extends CommonDto implements Serializable {
	private String nickname;
	private String email;
	private String password = "";
	private String snsId;
	private boolean isAdmin = false;
	
	public UserDto(String email) {
		this.email = email;
	}
	
	public UserDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public UserDto(String email, String password, boolean isAdmin) {
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public UserDto(String email, String password, String nickname, boolean isAdmin) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.isAdmin = isAdmin;
	}
	
	public String getEcPassword() {
		return HashUtil.sha256(this.password);
	}
	
}
