package com.jt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;

//@SpringBootTest
public class TestRedis {

	//redis测试案例
	@Test
	public void test01() {
		String host="192.168.126.166";//host:redis主机地址
		int port=6379;
		Jedis jedis=new Jedis(host, port);
		jedis.set("redis", "你好redis");
		System.out.println(jedis.get("redis"));
	}
	private Jedis jedis;

	//初始化jedis对象
	@BeforeEach
	public void init() {
		String host="192.168.126.166";//host:redis主机地址
		int port=6379;
		jedis=new Jedis(host, port);		
	}
	/**
	 * 如果redis存在key不允许修改
	 */
	@Test
	public void testExist() {
		String key="game"; 
		if(!jedis.exists(key)) {
			jedis.set(key, "LOL");
		}
		System.out.println(jedis.get(key));
	}
	/**
	 * setnx 只有当key不存在时,赋值成功
	 */
	@Test
	public void testNX() {
		jedis.flushAll();
		jedis.setnx("jedis", "无极剑圣");
		jedis.setnx("jedis", "提莫");
		System.out.println(jedis.get("jedis"));
	}
	/**
	 * redis添加记录 ,添加超时时间,展现剩余时间
	 * 不能保证操作的原子性
	 * @throws InterruptedException 
	 */
	@Test
	public void testSetExpire() throws InterruptedException {
		jedis.set("A","123456");
		int a= 10/0;
		jedis.expire("A", 60);
		Thread.sleep(3000);
		System.out.println(jedis.ttl("A"));

	}
	//保证入库操作和超时时间原子性
	@Test
	public void testSetEx() throws InterruptedException {
		jedis.setex("adc", 10, "测试超时时间");
		Thread.sleep(5000);
		System.out.println(jedis.ttl("adc"));
	}
	/**
	 * key不存在时才能赋值,并添加超时时间
	 */
	@Test
	public void testSetNXEX() {
		SetParams setParams=new SetParams();
		setParams.nx().ex(60);
		String result = jedis.set("c", "测试方法",setParams);
		System.out.println("结果:"+result);
	}
	@Test
	public void testHset() {
		jedis.hset("user","id","125");
		jedis.hset("user","name","无名");
		jedis.hset("user","age","125");
		Map<String, String> result = jedis.hgetAll("user");
		System.out.println(result);
	}

	@Test
	public void testList() {
		jedis.lpush("list", "1","2","3","4");
		System.out.println(jedis.rpop("list"));
	}
	//redis控制事务	
	@Test
	public void testMulti() {
		Transaction multi = jedis.multi();
		try {
		multi.set("ww", "wwwww");
		multi.set("rt", "wwwww");
		multi.set("wqew", "wwwww");
 		multi.exec();}
		catch(Exception e) {
			e.printStackTrace();
			multi.discard();
		}
	}
	@Autowired
	private ShardedJedis shardedJedis;


	@Test
    public void testShards() {
		shardedJedis.set("shard", "测试redis分片Bean注入成功");
        System.out.println(shardedJedis.get("shard")); 
	}
	
   /**
    * 测试哨兵
    * masterName 当前主机变量名
    */

	@Test
	public void sentinel() {
		Set<String> sentinels=new HashSet<>();
		sentinels.add("192.168.126.166:26379");
		JedisSentinelPool pool=
				new JedisSentinelPool("mymaster", sentinels);
	    Jedis jedis = pool.getResource();
	    jedis.set("sentinel","aaaa");
	    System.out.println(jedis.get("sentinel"));
	}

	@Test
    public void testCluster() {
    	Set<HostAndPort> nodes=new HashSet<>();
    	nodes.add(new HostAndPort("192.168.126.166",7000));
    	nodes.add(new HostAndPort("192.168.126.166",7001));
    	nodes.add(new HostAndPort("192.168.126.166",7002));
    	nodes.add(new HostAndPort("192.168.126.166",7003));
    	nodes.add(new HostAndPort("192.168.126.166",7004));
    	nodes.add(new HostAndPort("192.168.126.166",7005));
		JedisCluster cluster=new JedisCluster(nodes);
		cluster.set("redisCluster","redis集群");
		System.out.println(cluster.get("redisCluster"));
    	
    }
	







}
