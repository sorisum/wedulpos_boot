package com.wedul.common.util;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * MailUtil
 * 
 * @author wedul
 *
 */
@AllArgsConstructor
@Component
public class MailUtil {
	
	private final JavaMailSender javaMailSender;

	/**
	 * Mail 전송
	 * 
	 * @param email
	 * @param subject
	 * @param content
	 * @throws Exception
	 */
	public void sendMailWithJava(String email, String subject, String content) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		message.setSubject(subject, "euc-kr");
		message.setText(content, "euc-kr", "html");
		message.addRecipient(RecipientType.TO, new InternetAddress(email));
		javaMailSender.send(message);
	}
	
}
