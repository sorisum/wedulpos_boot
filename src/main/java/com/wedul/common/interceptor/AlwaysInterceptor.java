package com.wedul.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wedul.common.util.SessionUtil;
import com.wedul.wedulpos.user.dto.UserDto;

/**
 * Always Page Interceptor
 *
 * @author wedul
 * @date 2017. 11. 3.
 * @name AlwaysInterceptor
 */
public class AlwaysInterceptor extends HandlerInterceptorAdapter {

  UserDto loginUser;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

    loginUser = SessionUtil.getCurrentUser();
    if (null != loginUser) {
      request.setAttribute("loginUser", loginUser.getEmail());
      request.setAttribute("loginUserNickname", loginUser.getNickname());
      request.setAttribute("loginType", SessionUtil.getLoginType());
      request.setAttribute("isSnsLogin", StringUtils.isNotBlank(loginUser.getSnsId()));
    }
    request.setAttribute("projectName", "Wedul Pos");
    return true;
  }

}
