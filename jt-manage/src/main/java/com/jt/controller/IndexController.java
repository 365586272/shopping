package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	/**
	 * RestFul 语法 1.
	 *    1.参数用{}包裹  2.参数与参数之间/分隔 3.参数位置必须固定 
	 *    4.方法中添加同名的参数并使用@PathVariable注解
	 * @PathVariable:
	 *   value/name 标识参数名称,解决参数名称不一致
	 *   required:是否为必传的参数,默认为true
	 * @param moduleName
	 * @return
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}
	@RequestMapping("doIndexUI")
	public String doIndexUI() {
		return "yemiansave";
	}
	
	@PostMapping("/user")
	public String savaUser() {
		//执行新增业务
		return "";
	}

	@DeleteMapping("/user")
	public String deleteUser(Integer id) {
		//执行删除业务
		return "";
	}
	@PutMapping("/user")
	public String updateUser(Integer id) {
		//执行修改业务
		return "";
	}
	@GetMapping("/user")
	public String selectUser(Integer id) {
		//执行查询业务
		return "";
	}
	
	
}
