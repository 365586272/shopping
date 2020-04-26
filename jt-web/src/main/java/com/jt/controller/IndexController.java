package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/item")
public class IndexController {

	@RequestMapping("/index")
	public String index(@PathVariable String aa) {
		
		return "index";
	}
}
