package com.wedul.wedulpos.user.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * The type My authenticaion.
 *
 * @author wedul
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MyAuthenticaion extends UsernamePasswordAuthenticationToken {

  private static final long serialVersionUID = 1L;

  UserDto user;
  EnumLoginType loginType;

  public MyAuthenticaion(String id, String password, List<GrantedAuthority> grantedAuthorityList, UserDto user, EnumLoginType loginType) {
    super(id, password, grantedAuthorityList);
    this.user = user;
    this.loginType = loginType;
  }

}
