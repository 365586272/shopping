package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@SpringBootTest
public class TestSentinel {

	@Autowired
	private JedisCluster jedisCluster;

	@Test
	public void testClusters() {
		jedisCluster.set("Bean", "集群注入成功");
		System.out.println(jedisCluster.get("Bean"));
	}
}
