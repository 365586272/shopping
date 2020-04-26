package com.jt.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

@RestController
@RequestMapping("/web")
public class JSONPController {
//http://manage.jt.com/web/testJSONP?callback
    //@RequestMapping("/testJSONP")
	public String testJSONP(String callback) {
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(100L).setItemDesc("json跨域访问");
		String json = ObjectMapperUtil.toJson(itemDesc);
    	return callback+"("+json+")";
	}
    
    /**
     * JSONP的工具API
     */
    @RequestMapping("/testJSONP")
    public JSONPObject testJSONP2(String callback,int id) {
    	System.out.println(id);
    	ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(100L).setItemDesc("jsonp工具API跨域访问");
    	return new JSONPObject(callback, itemDesc);
    }
    

}
