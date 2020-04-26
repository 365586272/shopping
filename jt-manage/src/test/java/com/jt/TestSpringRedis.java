package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import redis.clients.jedis.Jedis;

@SpringBootTest//启动spring容器,为测试方法提供支持
public class TestSpringRedis {

	@Autowired
	private Jedis jedis;

	@Test
    public void text01() {
    	jedis.set("abc", "LOL");
    	System.out.println(jedis.get("abc"));
    }
}
