package com.wedul.wedulpos.user.controller;

import com.wedul.wedulpos.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wedul.wedulpos.user.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * User관련 컨트롤러
 *
 * @author wedul
 */
@RestController
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 회원가입
     *
     * @param reqDto
     * @return
     * @throws Exception
     */
    @RequestMapping("/join")
    public ResponseEntity<?> join(UserDto reqDto) throws Exception {
        return ResponseEntity.ok(userService.insertUser(reqDto));
    }

    /**
     * facebook으로 로그인
     *
     * @param reqDto the req dto
     * @return the response entity
     * @throws Exception the exception
     */
    @RequestMapping("/login/facebook")
    public ResponseEntity<?> loginfacebook(HttpServletRequest request, UserDto reqDto) throws Exception {
        return ResponseEntity.ok(userService.facebookLogin(request, reqDto));
    }

    /**
     * 이메일 인증 요청
     *
     * @param email
     * @return
     * @throws Exception
     */
    @RequestMapping("/email")
    public ResponseEntity<?> checkEmail(String email) throws Exception {
        return ResponseEntity.ok(userService.checkEmail(email.trim()));
    }

    /**
     * nickname check
     *
     * @param nickname
     * @return
     * @throws Exception
     */
    @RequestMapping("/nickname")
    public ResponseEntity<?> checkNickName(String nickname) throws Exception {
        return ResponseEntity.ok(userService.checkNickname(nickname.trim()));
    }

    /**
     * 임시 비밀번호 발급
     *
     * @param email
     * @return
     * @throws Exception
     */
    @RequestMapping("/send/temppw")
    public ResponseEntity<?> createTempPw(String email) throws Exception {
        return ResponseEntity.ok(userService.createTempPassword(email.trim()));
    }

    /**
     * 인증번호 확인
     *
     * @param otp
     * @return
     * @throws Exception
     */
    @RequestMapping("/cert/check")
    public ResponseEntity<?> checkCert(String userId, String otp) throws Exception {
        return ResponseEntity.ok(userService.checkCert(userId.trim(), otp.trim()));
    }

    /**
     * 패스워드 초기화
     *
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping("/password")
    public ResponseEntity<?> changePassword(
            @RequestParam String email,
            @RequestParam String password) throws Exception {
        return ResponseEntity.ok(userService.changePassword(email.trim(), password.trim()));
    }

}
