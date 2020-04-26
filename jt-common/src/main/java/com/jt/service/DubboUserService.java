package com.jt.service;

import com.jt.pojo.User;

//dubbo自己的接口
public interface DubboUserService {

	//void saveUser(User user);

	void saveUser(User user);

	String findUserByUP(User user);

}
