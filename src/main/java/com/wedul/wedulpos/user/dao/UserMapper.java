package com.wedul.wedulpos.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wedul.wedulpos.user.dto.UserDto;
import com.wedul.wedulpos.user.dto.UserOtpDto;

/**
 * The interface User mapper.
 */
@Mapper
public interface UserMapper {

	/**
	 * 사용자 조회
	 *
	 * @param user the user
	 * @return user dto
	 */
	UserDto selectUser(UserDto user);

	/**
	 * 사용자 입력
	 *
	 * @param user the user
	 * @return the int
	 */
	int insertUser(UserDto user);

	/**
	 * 임시 비밀번호 업데이트
	 *
	 * @param dto the dto
	 * @return int
	 */
	int updateTempPw(UserDto dto);

}
