package com.wedul.wedulpos.user.dao;

import com.wedul.common.enums.EnumsRedisKeys;
import com.wedul.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * wedulpos
 *
 * @author wedul
 * @since 2018. 9. 25.
 **/
@Repository
public class CertDao {

  @Autowired
  RedisTemplate<String, String> redisTemplage;

  private final int KEY_EXPIRE_SECOND = 180;

  /**
   * Insert otp num.
   *
   * @param userId userId
   * @param otpNum the otp num
   */
  public void insertOtpNum(String userId, String otpNum) {
    String key = makeKey(userId);
    redisTemplage.opsForValue().set(key, CommonUtils.base64Encoding(otpNum));
    redisTemplage.expire(key, KEY_EXPIRE_SECOND, TimeUnit.SECONDS);
  }

  /**
   * Check otp num boolean.
   *
   * @param userId the user id
   * @param otpNum the otp num
   * @return the boolean
   */
  public boolean checkOtpNum(String userId, String otpNum) {
    String value = redisTemplage.opsForValue().get(makeKey(userId));

    return CommonUtils.base64Encoding(otpNum).equals(value) && redisTemplage.opsForValue().getOperations().delete(makeKey(userId));
  }

  /**
   * make key
   *
   * @param userId
   * @return
   */
  private String makeKey(String userId) {
    // key
    StringJoiner sj = new StringJoiner(":");
    sj.add(EnumsRedisKeys.OTP.getKey()).add(CommonUtils.base64Encoding(userId));
    return sj.toString();
  }

}
