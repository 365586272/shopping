package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

/**
 * 后台为前台提供数据的controller
 * @author DELL、
 *
 */
@RestController
@RequestMapping("/web/item")
public class WebItemController {
	@Autowired
	private ItemService itemService;

	/**
	 * 需求:查询商品信息
	 * url:http://manage.jt.com:80/web/item/findItemById/{}
	 */
	@RequestMapping("/findItemById/{itemId}")
	public Item findItemById(@PathVariable Long itemId) {
		return itemService.findItemById(itemId);
	}
	@RequestMapping("/findItemDescById/{itemId}")
	public ItemDesc findItemDescById(@PathVariable Long itemId) {
		
		return itemService.findDescById(itemId);
	}

}
