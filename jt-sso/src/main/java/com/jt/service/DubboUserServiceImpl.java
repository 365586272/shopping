package com.jt.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

//dubbo实现类
@Service
public class DubboUserServiceImpl implements DubboUserService{

	@Autowired
	private JedisCluster jedis;
	@Autowired
	private UserMapper userMapper;

	@Override
	public void saveUser(User user) {
		String md5Pass=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass)
		    .setEmail(user.getPhone())
		    .setCreated(new Date())
		    .setUpdated(user.getCreated());
		userMapper.insert(user);
	}

	@Override
	public String findUserByUP(User user) {
		//.需要将密码进行加密
		String md5Pass=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper=new QueryWrapper<>(user);           
		User userDB=userMapper.selectOne(queryWrapper);
		if(userDB==null) {
			//为空,数据库没有该记录
			return null;
		}
		String key=UUID.randomUUID().toString();
		//保护用户隐私信息
		userDB.setPassword("自己猜");
		String userJSON=ObjectMapperUtil.toJson(userDB);
		jedis.setex(key,7*24*3600,userJSON);
		return key;
	}



}
