package com.jt;

import java.util.Map;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;

public class TestRedis2 {

	private Jedis jedis;
	@BeforeEach
	public void init() {
		String host="192.168.126.166";
		int port=6379;
		jedis=new Jedis(host, port);
	}
	@Test
	public void test01() {
		jedis.set("a","王者荣耀" );
		System.out.println(jedis.get("a"));
	}
	@Test
	public void tset02() {
		jedis.setnx("a", "LOL");
		System.out.println(jedis.get("a"));
	}

	@Test
	public void test03() {
		jedis.setex("a", 60, "花花公子");
		System.out.println(jedis.ttl("a"));
	}
	@Test
	public void tset04() throws InterruptedException {
		SetParams params=new SetParams();
		params.nx().ex(60);
		jedis.set("b", "鸡翅机", params);
		Thread.sleep(3000);
		System.out.println(jedis.ttl("b"));
	}
	@Test
	public void test05() {
		jedis.hset("user", "id", "12");
		jedis.hset("user", "name", "小王");
		jedis.hset("user", "age", "22");
		Map<String, String> map = jedis.hgetAll("user");
		System.out.println(map);
	}
	@Test
	public void test06() {
		jedis.lpush("list", "1","2","3","4");
		System.out.println(jedis.lpop("list"));
		System.out.println(jedis.lrange("list", 0, -1));
	}
	@Test
	public void test07() {
		Transaction multi = jedis.multi();
		multi.set("aa", "ss");
		multi.set("bb", "ss");
		multi.set("cc", "ss");
		multi.discard();
	}
}
