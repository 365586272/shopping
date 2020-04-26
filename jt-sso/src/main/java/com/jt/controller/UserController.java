package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private JedisCluster jedis;

	@Autowired
	private UserService userService;
	@RequestMapping("/getMessage")
	public String getMessage() {
		return "单点登录系统登录成功";
	}
	//http://sso.jt.com/user/check/adminss12
	///1?r=0.7216884089899647&callback
	/**
	 * 参数{param}/{type}
	 * @param username
	 * @param callback
	 * @return
	 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,
			@PathVariable Integer type,
			String callback) {
		Boolean flag = userService.checkUser(param, type);
		return new JSONPObject(callback, SysResult.success(flag));
	}
	//http://sso.jt.com/user/query/
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,
			String callback ) {
		String userJSON=jedis.get(ticket);
		if(StringUtils.isEmpty(userJSON)) {
			return new JSONPObject(callback, SysResult.fail(null));
		}
		//User user=ObjectMapperUtil.toObject(userJSON, User.class);
		return new JSONPObject(callback, SysResult.success(userJSON));
	}
}
