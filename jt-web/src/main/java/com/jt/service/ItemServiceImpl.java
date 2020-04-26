package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;

@Service
public class ItemServiceImpl implements  ItemService{

	@Autowired
	private HttpClientService httpClientService;
	//url:http://manage.jt.com:80/web/item/findItemById/{}
	@Override
	public Item findItemById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemById/"+itemId;
		String json = httpClientService.doGet(url);
		Item item = ObjectMapperUtil.toObject(json, Item.class);
		return item;
	}
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemDescById/"+itemId;
		String json = httpClientService.doGet(url);
		ItemDesc itemDesc = ObjectMapperUtil.toObject(json,ItemDesc.class);
		return itemDesc;
	}

}
