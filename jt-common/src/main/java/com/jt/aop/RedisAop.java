package com.jt.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.annotation.CacheFind;
import com.jt.util.ObjectMapperUtil;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Aspect
@Component
@Slf4j
public class RedisAop {
	@Autowired(required = false)
	//private ShardedJedis jedis;//分片机对象
    //private Jedis jedis;//哨兵对象
	private JedisCluster jedis;
	/**
	 * 案例:
	 * 1.拦截itemCatServiceImpl
	 */
	//@Pointcut("bean(itemCatServiceImpl)")
	public void pointCut() {};
	//JoinPoint spring为方法提供一个工具类api接口
	//@Before("pointCut()")
	public void before(JoinPoint jp) throws NoSuchMethodException, SecurityException {

		System.out.println("前置通知");
		//Object target = jp.getTarget(); //获取目标方法
		//Object[] args = jp.getArgs();//获取参数
		MethodSignature ms =(MethodSignature) jp.getSignature();//获取目标方法名称
		//2.1.2获取目标类型的字节码对象
		Class<?> c = jp.getTarget().getClass();
		//获取目标方法对象 
		Method method = c.getDeclaredMethod(ms.getName(), ms.getParameterTypes());

		System.out.println(c.getName()+":"+ms.getName());
		// System.out.println(signature);
	}
	@Around("execution(* com.jt.service..*.*(..))")
	public Object timeAround(ProceedingJoinPoint jp) {
		long start=System.currentTimeMillis();
		try {
			String className = jp.getSignature().getDeclaringTypeName();
			String methodName = jp.getSignature().getName();
			Object obj = jp.proceed();
			long end=System.currentTimeMillis();
			log.info("{"+className+"."+methodName+"方法执行的时间为"+(end-start)+"毫秒}");
			return obj;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();	
		}
	}


	/**
	 * 业务:如果方法有@CacheFind注解,实现缓存的业务逻辑
	 * 切入点表达式:拦截注解
	 * 通知选择:控制目标方法是否执行
	 */
	//可以直接将注解添加到参数中,但只能
	@Around("@annotation(cacheFind)")
	public Object cacheAround(ProceedingJoinPoint jp,CacheFind cacheFind) {
		long start=System.currentTimeMillis();
		String key=getKey(jp,cacheFind);		
		String value= jedis.get(key);
		Object obj=null;
		if(StringUtils.isEmpty(value)) {
			try {
				obj = jp.proceed();
				long end=System.currentTimeMillis();
				log.info("查询数据库的时间为 {}",end-start);
				String json = ObjectMapperUtil.toJson(obj);
				if(cacheFind.seconds()>0) {
					jedis.setex(key, cacheFind.seconds(),json);
				}else {
					jedis.set(key, json);
				}
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}else {
			//反射,动态获取返回值
			Class<?> returnType = getReturnType(jp);
			obj= ObjectMapperUtil.toObject(value, returnType);
           //LinkedHashMap可以自动转化为对象,只要属性相同
			long end=System.currentTimeMillis();
			log.info("查询redis哨兵缓存为{}",end-start);
		}
		return obj;
	}
	private Class<?> getReturnType(ProceedingJoinPoint jp) {
		MethodSignature ms	=(MethodSignature)jp.getSignature();
		return ms.getReturnType();
	}
	private String getKey(ProceedingJoinPoint jp, CacheFind cacheFind) {
		if(!StringUtils.isEmpty( cacheFind.key())) {
			return cacheFind.key();
		}
		String className=jp.getSignature().getDeclaringTypeName();
		String methodName=jp.getSignature().getName();
		Object args0=jp.getArgs()[0];
		String key=className+"."+methodName+"::"+args0;
		return key;
	}




























}
