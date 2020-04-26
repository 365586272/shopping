package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCatService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Reference(check=false,timeout = 3000)
	private DubboCatService dubboCatService;
	
	//cart/show
	@RequestMapping("/show")
	public String showCart(Model model,	HttpServletRequest request) {
		//User user =(User) request.getSession().getAttribute("JT_USER");
		User user=(User)request.getAttribute("JT_USER");
		Long userId=user.getId();
		List<Cart> cartList=dubboCatService.findCardById(userId);
		model.addAttribute("cartList",cartList);
		return "cart";
	} 
	///cart/update/num/562379/5
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNum(Cart cart,HttpServletRequest request) {
		User user=(User)request.getAttribute("JT_USER");
		Long userId=user.getId();
		cart.setUserId(userId);
		dubboCatService.updateNum(cart);
		return SysResult.success();
	}
	
	///cart/delete/
	@RequestMapping("/delete/{itemId}")
	public String delete(@PathVariable Long itemId,	HttpServletRequest request) {
		User user=(User)request.getAttribute("JT_USER");
		Long userId=user.getId();
		Cart cart=new Cart();
		cart.setItemId(itemId).setUserId(userId);
		dubboCatService.deleteItemById(cart);
		return "redirect:/cart/show.html";
	}
	//cart/add
	@RequestMapping("/add/{itemId}")
	public String add(Cart cart,HttpServletRequest request) {
		User user=(User)request.getAttribute("JT_USER");
		Long userId=user.getId();
		cart.setUserId(userId);
		dubboCatService.insert(cart);
		return "redirect:/cart/show.html";
	}
}
