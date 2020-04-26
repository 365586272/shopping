package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortController {

	@Value("${server.port}")
	private Integer port;
	/**
	 * 动态获取端口号
	 */
	@RequestMapping("/getPort")
	public String getPort() {
		
		return "当前访问的服务器"+port;
	}
}
