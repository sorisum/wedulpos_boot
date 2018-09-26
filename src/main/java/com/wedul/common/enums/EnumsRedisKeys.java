package com.wedul.common.enums;

/**
 * The enum Enums redis keys.
 */
public enum EnumsRedisKeys {

  OTP("OTP:USERID");

  private String key;

  EnumsRedisKeys(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

}
