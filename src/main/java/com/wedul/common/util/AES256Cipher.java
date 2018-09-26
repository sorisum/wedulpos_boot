package com.wedul.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * AES 알고리즘
 * 
 * @author wedul
 *
 */
@Slf4j
public class AES256Cipher {

	private static volatile AES256Cipher INSTANCE;
	final static String secretKey = "12345678901234567890123456789012"; //32bit
	static String IV = ""; //16bit

	public static AES256Cipher getInstance() {
		if (INSTANCE == null) {
			synchronized (AES256Cipher.class) {
				if (INSTANCE == null)
					INSTANCE = new AES256Cipher();
			}
		}
		return INSTANCE;
	}

	private AES256Cipher() {
		IV = secretKey.substring(0, 16);
	}

	//암호화
	public String AES_Encode(String str) {
		
		try {
			byte[] keyData = secretKey.getBytes();
			
			SecretKey secureKey = new SecretKeySpec(keyData, "AES");
			
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));
			
			byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
			String enStr = new String(Base64.encodeBase64(encrypted));
			
			return enStr;
		} catch (Exception ex) {
			log.error("encrypt error", ex);
			return str;
		}
	}

	//복호화
	public String AES_Decode(String str) {
		
		try {
			byte[] keyData = secretKey.getBytes();
			SecretKey secureKey = new SecretKeySpec(keyData, "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));
			
			byte[] byteStr = Base64.decodeBase64(str.getBytes());
			
			return new String(c.doFinal(byteStr), "UTF-8");
		} catch (Exception ex) {
			log.error("decrypt error", ex);
			return str;
		}
	}
}
