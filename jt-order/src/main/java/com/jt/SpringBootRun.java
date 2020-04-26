package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.jt.mapper") //为mapper接口创建代理对象
public class SpringBootRun {
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRun.class, args);
	}
	
	@Bean
	public Queue getQueue() {
		
		return new Queue("order_jt",true);
	}
	
	@Bean
	public Queue getQueue1() {
		
		return new Queue("ship_jt",true);
	}
	
	@Bean
	public Queue getQueue2() {
		
		return new Queue("item_jt",true);
	}
	 

}