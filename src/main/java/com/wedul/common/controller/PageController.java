package com.wedul.common.controller;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;  

/**
 * pageController
 * 
 * @author wedul
 * @date 2017. 10. 31.
 * @name PageController
 */
@Controller
@RequestMapping(method = RequestMethod.GET)
public class PageController {
	
	private List<String> money = asList("month", "wallet", "statistic", "setting");
	private List<String> user = asList("join", "login", "findpw");
	
	/**
	 * 메인 페이지 이동 
	 * 
	 * @authoer: wedul
	 * @date : 2017. 11. 4.
	 * @file : PageController.java
	 * @return : String
	 */
	@RequestMapping(value = {"/main", "/"})
	public String home() {
		return "main";
	}
	
	/**
	 * todo 페이지
	 * 
	 * @param response
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping("/todo") 
	public String todo(HttpServletResponse response) throws IOException{
		return "todo";
	}
	
	/**
	 * 스케줄 페이지
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/schedule") 
	public String schedule(HttpServletResponse response) throws IOException{
		return "schedule";
	}
	
	/**
	 * 가계부 페이지
	 * 
	 * @param response
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/money/{path}") 
	public String money(HttpServletResponse response, @PathVariable String path) throws IOException{
		if (money.contains(path)) {
			return "money/" + path;
		} 
		
		response.sendError(404);
		return null;
	}
	
	/**
	 * page of User Controller
	 * 
	 * @param response
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/user/{path}")
	public String user(HttpServletResponse response, @PathVariable String path) throws IOException{
		if (user.contains(path)) {
			return "user/" + path;
		} 
		
		response.sendError(404);
		return null;
	}
	
	/**
	 * 메시지 화면 
	 * 
	 * @param respons
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/message")
	public String message(HttpServletResponse respons) throws IOException {
		return "message";
	}
}


  