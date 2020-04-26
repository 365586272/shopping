package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;


@Transactional(isolation = Isolation.READ_COMMITTED,
propagation = Propagation.REQUIRED,
rollbackFor = Throwable.class)
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDescMapper itemDescMapper;


	@Override
	public ItemDesc findDescById(Long itemId) {
		
		return itemDescMapper.selectById(itemId);
	}
	/*@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		PageInfo<Item> ph = itemMapper.findItemByPageHelper();
		long total = ph.getTotal();
		List<Item> itemList = ph.getList();
		return new EasyUITable(total, itemList);
	}*/

	/*@Override
	public EasyUITable findItemByPage(Integer page,Integer rows) {
	   long total =itemMapper.selectCount(null);
	   int start =(page-1)*rows;
	   List<Item> itemList=itemMapper.findItemByPage(start,rows);
	   return new EasyUITable(total, itemList);
	}*/
	/**
	 *page 封装的一个分页对象信息
	 *new Page<>(current, size)(查询页数,每页条数)
	 *如果使用mp方式进行分页查询,需配置拦截器
	 */
	//@Cacheable("Table")
	@Override
	public EasyUITable findItemByPage(Integer page,Integer rows) {
		System.out.println("从cache取出");
		Page<Item> myPage=new Page<>(page, rows);
		QueryWrapper<Item> queryWrapper=new QueryWrapper<>();
		queryWrapper.orderByDesc("updated");
		IPage<Item> IPage = itemMapper.selectPage(myPage, queryWrapper);
		long total = IPage.getTotal();
		List<Item> itemList = IPage.getRecords();
		return new EasyUITable(total, itemList);
	}


	//@CacheEvict(value="Table",allEntries = true)
	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		//商品入库
		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		itemMapper.insert(item);		
		//商品详情入库
		itemDesc.setItemId(item.getId())
		.setCreated(item.getCreated())
		.setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);
	}


	//@CacheEvict(value="Table",allEntries = true)
	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);	
		itemDesc.setItemId(item.getId()).setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}


	//@CacheEvict(value="Table",allEntries = true)
	@Override
	public void deleteItemById(Long[] ids) {
		//利用mp实现删除

		List<Long> idList = Arrays.asList(ids); 
		itemMapper.deleteBatchIds(idList);

		//itemMapper.deleteByIds(ids);
		itemDescMapper.deleteBatchIds(idList);
	}


	/**
	 * 批量修改
	 */
	@Override
	public void updateItemStatus(Long[] ids, int status) {
		Item item=new Item();
		item.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item> updateWrapper=new  UpdateWrapper<>();
		List<Long> idList = Arrays.asList(ids);
		updateWrapper.in("id", idList);
		itemMapper.update(item, updateWrapper);
		//itemMapper.updateItemStatus(ids,status);
	}

	@Override
	public Item findItemById(Long itemId) {
		
		return itemMapper.selectById(itemId);	 
	}







}
