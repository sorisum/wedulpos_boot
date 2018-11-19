package com.wedul.common.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.wedul.common.util.Constant;
import com.wedul.common.util.MessageBundleUtil;

/**
 * 언어 관련 컨트롤
 * 
 * @author wedul
 * @date 2017. 10. 30.
 * @name LanguageController
 */
@Controller
@AllArgsConstructor
public class LanguageController {

	private MessageBundleUtil messageBundleUtil;

	/**
	 * 로케일 설정 컨트롤러 
	 *
	 * @author jeongcheol
	 * @date 2017. 6. 17.
	 * @ruturn String
	 *
	 */
	@RequestMapping(value = "/changeLocale")
	public String changeLocale(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String locale) {
		HttpSession session = request.getSession();
		Locale lo = null;

		//step. Parameter에 따라서 Locale 생성, 기본은 KOREAN.
		if (locale.matches("en")) {
			lo = Locale.ENGLISH;
		} else {
			lo = Locale.KOREAN;
		}

		// step. Locale을 새로 설정한다.
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, lo);

		// step. 해당 컨트롤러에게 요청을 보낸 주소로 돌아간다.
		String redirectURL = "redirect:" + request.getHeader("referer");
		return redirectURL;
	}

	/**
	 * Language 가져오기
	 * 
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLanguage/{propertiesName}")
	@ResponseBody
	public void getLanguage(HttpServletResponse response, HttpServletRequest request, @PathVariable String propertiesName) throws Exception {
		List<String> readLine = new ArrayList<>();
		HttpSession session = request.getSession();
		readLine.addAll(setMessage(propertiesName, session));
		
		if (!propertiesName.contains(Constant.COMMON)) {
			propertiesName = 
					propertiesName.contains("_ko") ? Constant.COMMON + "_ko" : Constant.COMMON + "_en"; 
			
			readLine.addAll(setMessage(propertiesName, session));
		}

		IOUtils.writeLines(readLine, null, response.getOutputStream());
	}
	

	/**
	 * message 가지고오기
	 * 
	 * @param propertiesName
	 * @param session
	 * @throws IOException
	 */
	private List<String> setMessage(String propertiesName, HttpSession session) throws IOException {
		Resource resource = null;
		resource = new ClassPathResource("/messages/" + propertiesName.split("_")[0] + "/"+ propertiesName + ".properties");
		
		InputStream inputStream = resource.getInputStream();
		return IOUtils.readLines(inputStream);
	}
	
	
	/**
	 * Ajax를 이용하여 Message Properties 값 가져오기
	 * 
	 * @returnO
	 */
	@RequestMapping(value= "/getMessage", method=RequestMethod.POST)
	@ResponseBody
	public String getMyAjaxMessage(HttpServletRequest request, @RequestParam String msg) {
		return messageBundleUtil.getMessage(msg);
	}
}
