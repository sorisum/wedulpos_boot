package com.wedul.wedulpos.user.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.wedul.wedulpos.user.dto.EnumLoginType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.wedul.common.util.Constant;
import com.wedul.common.util.HashUtil;
import com.wedul.wedulpos.user.dto.MyAuthenticaion;
import com.wedul.wedulpos.user.dto.UserDto;
import com.wedul.wedulpos.user.service.UserService;

/**
 * 인증 프로바이더
 * 로그인시 사용자가 입력한 아이디와 비밀번호를 확인하고 해당 권한을 주는 클래스
 * 
 * @author wedul
 *
 */
@Component("authProvider")
public class AuthProvider implements AuthenticationProvider  {
	
	@Autowired
	UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String id = authentication.getName();
		String password = HashUtil.sha256(authentication.getCredentials().toString());
		UserDto user;

		if (authentication instanceof MyAuthenticaion) {
			return (MyAuthenticaion) authentication;
		} else {
			user = userService.selectUser(new UserDto(id));

			// email에 맞는 user가 없거나 비밀번호가 맞지 않는 경우.
			if (null == user || !user.getPassword().equals(password)) {
				return null;
			}
		}

		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		
		if (user.isAdmin()) {
			grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_ADMIN.toString()));
		} else {
			grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_USER.toString()));
		}

		return new MyAuthenticaion(id, password, grantedAuthorityList, user, EnumLoginType.NORMAL);
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
