package com.wedul.wedulpos.userService;

import com.wedul.common.util.AES256Cipher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * wedulpos
 *
 * @author wedul
 * @since 2018. 8. 21.
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UtilTest {

    @Test
    public void test() {
        System.out.println(AES256Cipher.getInstance().AES_Encode("dbsafer00"));
    }

}
