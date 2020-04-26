package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.baomidou.mybatisplus.core.conditions.SharedString;

import redis.clients.jedis.HostAndPort;
//标识配置类
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class JedisConfig {
//    @Value("${redis.node}")
//	private String node;
//	
//   //将redis交给spring管理
//	@Bean
//	public Jedis jedis() {
//	String host=node.split(":")[0];
//	int port=Integer.parseInt(node.split(":")[1]) ;
//	return new Jedis(host,port);
//	}
	@Value("${redis.cluster}")
	private String cluster;

	@Value("${redis.nodes}")
	private String nodes;
	@Value("${redis.sentinel}")
	private String sentinel;
	@Bean
	public ShardedJedis shardedJedis() {
		List<JedisShardInfo> shards=new ArrayList<JedisShardInfo>();
		String[] nodesArray = nodes.split(",");
		String node="";
		for(int i=0;i<nodesArray.length;i++) {
			node=nodesArray[i];
			shards.add(new JedisShardInfo(node.split(":")[0], Integer.parseInt(node.split(":")[1])));
		}
		return new ShardedJedis(shards);
	}
	
	//@Bean
	public JedisSentinelPool jedisSentinelPool() {
		Set<String> sentinels=new HashSet<>();
		sentinels.add(sentinel);
		return new JedisSentinelPool("mymaster", sentinels);
	}
	
	//@Autowired
	private JedisSentinelPool jedisSentinelPool;
	
	//@Bean
	public Jedis jedis() {
		return jedisSentinelPool.getResource();
	}
	
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> set=new HashSet<>();
		String[] clusterArray = cluster.split(",");
		for(String clusters:clusterArray) {
			String host=clusters.split(":")[0];
			int port=Integer.parseInt(clusters.split(":")[1]);
		    set.add(new HostAndPort(host, port));
		}		
		return new JedisCluster(set);
	}
	
}
