package com.jt.aop;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//全局异常类处理

import com.jt.vo.SysResult;
@RestControllerAdvice   
//当程序在controller执行有异常时,会执行通知具体方法
public class SystemException {
	/**
	 * aop :公式 切面=切入点+通知方法
	 */
	//当程序出错后,返回什么
	@ExceptionHandler(RuntimeException.class)
	public SysResult handleException(RuntimeException e) {
		e.printStackTrace();
		return SysResult.fail(e.getMessage());
	}
}
