package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.pojo.User;
import com.jt.service.DubboCatService;
import com.jt.service.DubboOrderService;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/order")
public class OrderController {
       //order/create.html
	@Reference(check = false,timeout = 3000)
	private DubboCatService dubboCatService;
	@Reference(check = false,timeout = 3000)
	private DubboOrderService dubboOrderService;
	/**
	 * 页面取值 carts
	 * @return
	 */
     @RequestMapping("/create")
	public String orderCreate(HttpServletRequest request,Model model) {
	    User user=(User)request.getAttribute("JT_USER");
	    Long userId=user.getId();
    	List<Cart> carts=dubboCatService.findCardById(userId);
	    model.addAttribute("carts", carts);
    	return "order-cart";
}
     ///order/submit
     //订单入库
     @RequestMapping("/submit")
     @ResponseBody
     public SysResult submit(Order order,HttpServletRequest request) {
    	 //1.动态获取userId
    	User user =(User)request.getAttribute("JT_USER");
    	order.setUserId(user.getId()); 
    	String orderId=dubboOrderService.saveOrder(order);
    	if(StringUtils.isEmpty(orderId)) {
    		return SysResult.fail(orderId);
    	}
    	return SysResult.success(orderId);
     } 
     ///success.html?id=71585395730140
     /**
      * 根据orderid查询3张表
      */
     @RequestMapping("/success")
     public String success(@RequestParam("id")String orderId,Model model) {
    	
    	 Order order=dubboOrderService.findOrderById(orderId);
    	 model.addAttribute("order", order);
    	 return "success";
     }
}
