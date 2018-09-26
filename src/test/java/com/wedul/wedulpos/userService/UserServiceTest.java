package com.wedul.wedulpos.userService;

import com.wedul.common.util.MessageBundleUtil;
import com.wedul.wedulpos.user.dao.CertDao;
import com.wedul.wedulpos.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

  @Autowired
  UserService userService;

  @Autowired
  CertDao certDao;

  @Autowired
  MessageBundleUtil messageBundleUtil;

  @Test
  public void whenValidParameter_thenSuccess_CreateTemp_password() throws Exception {
    String password = userService.createTempPassword("rokking1@naver.com");

    assertThat(password, is(messageBundleUtil.getMessage("user.find.message.sns_id_not_support_chpasswd")));
  }

  @Test
  public void create_user_otp_number() {
   certDao.insertOtpNum("wedul@naver.com", "214FZD12");

   assertTrue(certDao.checkOtpNum("wedul@naver.com", "214FZD12"));
  }

}
