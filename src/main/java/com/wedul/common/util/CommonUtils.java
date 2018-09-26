package com.wedul.common.util;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

/**
 * 주요 Resource Util들이 들어있는 클래스
 *
 * @author wedul
 */
public final class CommonUtils {

  private CommonUtils() {
  }

  /**
   * Base 64 encoding string.
   *
   * @param str the str
   * @return the string
   */
  public static String base64Encoding(String str) {
    return Base64.getEncoder().encodeToString(str.getBytes());
  }

  /**
   * Base 64 decoding string.
   *
   * @param str the str
   * @return the string
   */
  public static String base64Decoding(String str) {
    return Base64.getDecoder().decode(str).toString();
  }

  /**
   * 현재 request를 이용하여  Cookie 획득하기
   *
   * @param request
   * @return
   */
  public static Cookie getCookieBySession(HttpServletRequest request) {
    Cookie loginCookie = WebUtils.getCookie(request, Constant.LOGIN_COOKIE);
    return loginCookie;
  }

  /**
   * 현재 시간을 반환
   *
   * @return
   */
  public static Date getCurrentDate() {
    return new Date(System.currentTimeMillis());
  }

  /**
   * 자리수에 맞는 랜덤 숫자 생성
   *
   * @param length
   * @return
   */
  public static String generateNumber(int length) {

    String numStr = "1";
    String plusNumStr = "1";

    for (int i = 0; i < length; i++) {
      numStr += "0";

      if (i != length - 1) {
        plusNumStr += "0";
      }
    }

    Random random = new Random();
    int result = random.nextInt(Integer.parseInt(numStr)) + Integer.parseInt(plusNumStr);

    if (result > Integer.parseInt(numStr)) {
      result = result - Integer.parseInt(plusNumStr);
    }

    return String.valueOf(result);
  }

  /**
   * integer to boolean
   *
   * @param value
   * @return
   */
  public static boolean int2boolean(int value) {
    return 1 == value;
  }

  /**
   * 랜덤 숫자
   *
   * @param min
   * @param max
   * @return
   */
  public static int randomNumWithRange(int min, int max) {
    return (int) (Math.random() * (max - min + 1)) + min;
  }

  /**
   * 임시 비밀번호 발급
   *
   * @return
   */
  public static String createTempPassword() {
    List<String> list = new ArrayList<>();
    StringBuffer buffer = new StringBuffer();

    // 대 문자 랜덤 생성
    list.add(Character.toString((char) randomNumWithRange(65, 90)));

    // 소문자 3개 생성
    for (int i = 0; i < 3; i++) {
      list.add(Character.toString((char) randomNumWithRange(97, 122)));
    }

    // 숫자  3개 더하기
    for (int i = 0; i < 3; i++) {
      list.add(String.valueOf(randomNumWithRange(0, 9)));
    }

    Collections.shuffle(list);

    list.stream().forEach(str -> {
      buffer.append(str);
    });

    return buffer.toString();
  }

}
