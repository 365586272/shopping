package com.jt.util;

import com.jt.pojo.User;

public class UserThreadLocal {

	//作用:在线程内实现数据共享 最好在同一个类中实现数据传递,不然会有线程安全
	//必须释放
	private static ThreadLocal<User> thread=new ThreadLocal<>();
	//存数据
	public static void set(User user) {
		thread.set(user);
	}
	
	//取数据
	public static User get() {
		return thread.get();
	}
	
	//清空数据
	public static void remove() {
		thread.remove();
	}
}
