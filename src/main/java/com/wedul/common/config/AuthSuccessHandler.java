package com.wedul.common.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedul.common.dto.ResultDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 로그인 성공 핸들러
 *
 * @author wedul
 */
@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws ServletException, IOException {

    ObjectMapper om = new ObjectMapper();
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().print(om.writeValueAsString(ResultDto.success()));
    response.getWriter().flush();
  }

}
