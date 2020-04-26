package com.jt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)//元注解
public @interface CacheFind {//方法即属性

	//1.key可以自动生成,也可以由用户指定
	String key() default "";
	
	int seconds() default 0;//用户不需要设置超时时间
}
