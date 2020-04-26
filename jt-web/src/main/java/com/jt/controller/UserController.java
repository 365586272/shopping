package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private JedisCluster jedis;
	
	@Reference(check = false)
	private DubboUserService dubboUserService;
    //http://www.jt.com/user/login.html
	//http://www.jt.com/user/register.html
    @RequestMapping("/{module}")
	public String register(@PathVariable String module) {
	  return module;
  }
    
    ///user/doRegister
    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult doRegister(User user) {
    	dubboUserService.saveUser(user);
    	return SysResult.success();
    }
    ///user/doLogin
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult doLogin(User user,HttpServletResponse response) {	
    	String uuid=dubboUserService.findUserByUP(user);
    	//校验uuid是否正确
    	if(StringUtils.isEmpty(uuid)) {
    		return SysResult.fail("登录失败");
    	}
    	//写入cookie
    	Cookie cookie=new Cookie("JT_TICKET", uuid);
    	cookie.setMaxAge(7*24*60*60);//设定cookie最大使用时间
    	cookie.setDomain("jt.com");//在指顶域名设定共享数据
    	cookie.setPath("/");//cookie作用范围,从根目录开始
    	response.addCookie(cookie);
    	return SysResult.success();
    }
    //http://www.jt.com/user/logout.html
    //实现用户登出
    //重定向 用户首页
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response) {
      //先获取cookie的值
    	Cookie[] cookies = request.getCookies();
       //从数组中动态获取JT_TICKET信息
    	String ticket=null;
    	if(cookies!=null &&cookies.length>0) {
    		for(Cookie cookie:cookies){
    			if(cookie.getName().equals("JT_TICKET")) {
    				ticket=cookie.getValue();
    			    cookie.setMaxAge(0); 
    			    cookie.setDomain("jt.com");
    			    cookie.setPath("/");
    			    response.addCookie(cookie);
    			    break;
    			}   		  		
    		}    		      		
    	}   
    	if(jedis.exists(ticket)) {
			jedis.del(ticket);	
		}
    	return "redirect:/";
    }
}
