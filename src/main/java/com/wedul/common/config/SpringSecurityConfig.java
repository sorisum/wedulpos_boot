package com.wedul.common.config;

import com.wedul.wedulpos.user.serviceImpl.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security Configuration
 *
 * @author wedul
 */
@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@ComponentScan(basePackages = {"com.wedul.*"})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  AuthProvider authProvider;

  @Autowired
  AuthFailureHandler authFailureHandler;

  @Autowired
  AuthSuccessHandler authSuccessHandler;

  @Override
  public void configure(WebSecurity web) throws Exception {
    // 허용되어야 할 경로들
    web.ignoring().antMatchers("/resources/**",
            "/dist/**",
            "/weather",
            "/user/join",
            "/user/email",
            "/user/nickname",
            "/user/send/temppw",
            "/user/cert/check",
            "/user/password",
            "/user/findpw",
            "/user/login/facebook",
            "/join",
            "/getLanguage/**",
            "/getMessage"); // #3
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // 로그인 설정
    http.authorizeRequests()
            .antMatchers("/", "/user/login", "/error**").permitAll()
            .antMatchers("/**").access("ROLE_USER")
            .antMatchers("/**").access("ROLE_ADMIN")
            .antMatchers("/admin/**").access("ROLE_ADMIN")
            .antMatchers("/**").authenticated()
            .and()
            .formLogin()
            .loginPage("/user/login")
            .defaultSuccessUrl("/")
            .failureHandler(authFailureHandler)
            .successHandler(authSuccessHandler)
            .usernameParameter("id")
            .passwordParameter("password")
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .and()
            .csrf()
            .and()
            .authenticationProvider(authProvider);
  }

}
