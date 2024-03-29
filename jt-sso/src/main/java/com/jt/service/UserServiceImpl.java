package com.jt.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public Boolean checkUser(String param, Integer type) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        Map<Integer, String> map=new HashMap<>();
        map.put(1, "username");
        map.put(2, "phone");
        map.put(3, "email");
        queryWrapper.eq(map.get(type),param );
		Integer count = userMapper.selectCount(queryWrapper);
		return count==0?false:true;
	}

	


}
