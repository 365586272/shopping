package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/query")
	public EasyUITable findItemByPage(Integer page,Integer rows) {
		
		return itemService.findItemByPage(page, rows);
	}
	/**
	 * /item/save
	 * 商品新增:参数整个form表单数据
	 * 返回值:SysResult对象
	 * 商品关联操作,同时入库item/itemdesc
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		itemService.saveItem(item,itemDesc);
		//int a=10/0;
		//throw new IllegalArgumentException("");
		return SysResult.success();
	}
	//update
	/**
	 * Item/ItemDesc一起接受参数,重名属性要额外注意
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		itemService.updateItem(item,itemDesc);
		//int a=10/0;
		//throw new IllegalArgumentException("");
		return SysResult.success();
	}
	///item/query/item/desc/
	@RequestMapping("/query/item/desc/{id}")
	public SysResult findDescById(@PathVariable("id") Long itemId) {
		ItemDesc itemDesc=itemService.findDescById(itemId);
		return SysResult.success(itemDesc);
	}
	
	/**
	 * spring mvc特性:如果用户传递的参数中间使用","分割
	 * spring mvc可以自动转化为数组类型
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public SysResult deleteItemById(Long... ids) {
		
		itemService.deleteItemById(ids);
		return SysResult.success();
	}
	///item/instock
	@RequestMapping("/instock")
	public SysResult instock(Long... ids) {
		int status=2;
		itemService.updateItemStatus(ids,status);
		return SysResult.success();
	}
	@RequestMapping("/reshelf")
	public SysResult reshelf(Long... ids) {
		int status=1;
		itemService.updateItemStatus(ids,status);
		return SysResult.success();
	}
	
}
