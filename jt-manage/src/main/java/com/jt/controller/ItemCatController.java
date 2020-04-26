package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	@GetMapping("/queryItemName")
	public String findItemCatNameById(Long itemCatId) {

		return itemCatService.findItemCatService(itemCatId);
	}
	/**
	 * 查询商品分类信息,返回list<vo>对象
	 *  /item/cat/list
	 * 参数:parentId
	 * 返回值:list<vo>
	 */
	@RequestMapping("/list")
	public List<EasyUITree> findItemCatListByParentId
	(@RequestParam(value="id",defaultValue = "0") Long parentId){
		//实现数据缓存的查询
		//return	itemCatService.findItemCatCache(parentId);

		return itemCatService.findItemCatListByParentId(parentId);
	}
}
