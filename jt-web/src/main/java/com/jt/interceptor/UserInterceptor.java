package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

@Component//将对象交给spring管理
public class UserInterceptor implements HandlerInterceptor{
	/**
	 * 如果用户没有登录,重定向到登录页面,已经登录,拦截器放行
	 *
	 *如何确定用户登录
	 *1.用户的cookie是否有值
	 *2.用户ticket在redis有没有
	 */
	@Autowired
	private JedisCluster jedis;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		String ticket=null;
		if(cookies!=null&& cookies.length>0) {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("JT_TICKET")) {
					ticket=cookie.getValue();
					break;
				}
			}
		}
		if(!StringUtils.isEmpty(ticket)) {
			if(jedis.exists(ticket)) {
				String userJSON = jedis.get(ticket);
				User user = ObjectMapperUtil.toObject(userJSON, User.class);
			  //利用ThreadLocal
				//UserThreadLocal.set(user);				
				//利用request域/session域
				//request.getSession().setAttribute("JT_USER", user);
				request.setAttribute("JT_USER", user);
				return true;
			}
		}
		
		response.sendRedirect("/user/login.html");
		return false;
	}
}
