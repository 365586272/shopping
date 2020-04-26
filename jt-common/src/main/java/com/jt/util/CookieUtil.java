package com.jt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {

	public static String getTicket(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String ticket=null;
		if(cookies!=null&& cookies.length>0) {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("JT_TICKET")) {
					ticket=cookie.getValue();
				}
			}
		}		
		return null;
	}
	
}
