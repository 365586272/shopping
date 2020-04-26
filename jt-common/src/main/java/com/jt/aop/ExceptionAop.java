package com.jt.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ExceptionAop {

	@AfterThrowing(pointcut = "execution(* com.jt.service..*.*(..))",throwing = "e")
	public void doHandleException(JoinPoint jp,Throwable e) {
		MethodSignature signature = (MethodSignature)jp.getSignature();
		String ClassName = signature.getDeclaringTypeName();
		String name = signature.getName();
		log.error("{}error message is{}",ClassName+":"+name,e.getMessage());
	}
	
}
