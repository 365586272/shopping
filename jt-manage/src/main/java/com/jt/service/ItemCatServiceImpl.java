package com.jt.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.annotation.CacheFind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired(required=false)
	//required=false 如果spring没有实例化对象,
	private Jedis jedis;

	@Override
	public String findItemCatService(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		//int a=10/0;
		return itemCat.getName();
	}

	@CacheFind //该方法适用缓存k--(v -返回值结果)
	@Override
	public List<EasyUITree> findItemCatListByParentId(Long parentId) {
	    QueryWrapper<ItemCat> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
	    List<ItemCat> itemCatList=itemCatMapper.selectList(queryWrapper);
		List<EasyUITree> treeList=
				new ArrayList<EasyUITree>(itemCatList.size());
	    //将信息存储到vo中
		for(ItemCat itemCat:itemCatList) {
			Long id=itemCat.getId();
			String text=itemCat.getName();
			String state=itemCat.getIsParent()?"closed":"open";
			EasyUITree easyUITree = new EasyUITree(id, text, state);
			treeList.add(easyUITree);
		}
		return treeList;
	}
/**
 * 从redis中动态获取数据
 * 步骤 1.首先查询缓存,如果缓存没有,则查询数据库
 *     1.注入redis对象
 *     2.准备key, 包名.类名.方法名:参数id
 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EasyUITree> findItemCatCache(Long parentId) {
		long start=System.currentTimeMillis();
		String key="com.jt.service.ItemCatServiceImpl.findItemCatCache::"+parentId;
		String value = jedis.get(key);
		List<EasyUITree> treeList=new ArrayList<>();
		if(StringUtils.isEmpty(value)) {
			treeList=findItemCatListByParentId(parentId);
			System.out.println(treeList);
			System.out.println("查询数据库时间:"+(System.currentTimeMillis()-start));
		    String json = ObjectMapperUtil.toJson(treeList);
			jedis.set(key, json);
		}else {
			treeList=
			ObjectMapperUtil.toObject(value, treeList.getClass());
			System.out.println("查询redis缓存时间:"+(System.currentTimeMillis()-start));
		}
		return treeList;
	}


}
