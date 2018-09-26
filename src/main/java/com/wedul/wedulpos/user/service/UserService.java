package com.wedul.wedulpos.user.service;

import com.wedul.common.dto.ResultDto;
import com.wedul.wedulpos.user.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface User service.
 *
 * @author wedul
 */
public interface UserService {

	/**
	 * User 정보 찾기
	 *
	 * @param user the user
	 * @return user dto
	 * @throws Exception
	 */
	UserDto selectUser(UserDto user);

	/**
	 * User 정보 입력하기
	 *
	 * @param user the user
	 * @return the boolean
	 * @throws Exception the exception
	 */
	boolean insertUser(UserDto user) throws Exception;

	/**
	 * Email 인증
	 *
	 * @param email the email
	 * @return string string
	 * @throws Exception the exception
	 */
	String checkEmail(String email) throws Exception;

	/**
	 * 이메일 인증번호 확인
	 *
	 * @param otp the otp
	 * @return boolean boolean
	 * @throws Exception the exception
	 */
	boolean checkCert(String userId, String otp) throws Exception;

	/**
	 * 임시 비밀번호 발급
	 *
	 * @param email the email
	 * @return string string
	 * @throws Exception the exception
	 */
	String createTempPassword(String email) throws Exception;

	/**
	 * 비밀번호 변경
	 *
	 * @param email    the email
	 * @param password the password
	 * @return the string
	 */
	String changePassword(String email, String password);

	/**
	 * nick name check
	 *
	 * @param nickname the nickname
	 * @return string string
	 */
	String checkNickname(String nickname);

	/**
	 * Login sns result dto.
	 *
	 * @param reqDto the req dto
	 * @return the result dto
	 */
	ResultDto facebookLogin(HttpServletRequest request, UserDto reqDto) throws Exception;
}
