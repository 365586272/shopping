package com.jt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@Configuration//当前类是配置文件
public class MybatisPulsConfig {

	//实现MP分页拦截器对象
	@Bean //map<方法名称,分页对象>
	public PaginationInterceptor PaginationInterceptor() {		
		return new PaginationInterceptor();
	}
	//将对象交给spring管理后,直接注入引用
}
