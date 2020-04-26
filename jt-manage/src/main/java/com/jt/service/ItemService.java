package com.jt.service;

import java.util.List;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {

	EasyUITable findItemByPage(Integer page,Integer rows);

	void saveItem(Item item,ItemDesc itemDesc);

	void updateItem(Item item, ItemDesc itemDesc);

	void deleteItemById(Long[] ids);

	void updateItemStatus(Long[] ids, int status);

	ItemDesc findDescById(Long itemId);

	Item findItemById(Long itemId);

	
	

	
}
