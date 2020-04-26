package com.jt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

public class TestObjectMapper {
	private static final ObjectMapper mapper=
			new ObjectMapper();
	/**
	 * 对象和jason串的互相转化
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testJSONObject() throws JsonProcessingException {
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemDesc("无极剑圣")
		.setItemId(88L)
		.setCreated(new Date())
		.setUpdated(new Date());
		String json = mapper.writeValueAsString(itemDesc);
		System.out.println(json);
		ItemDesc itemDesc2 = mapper.readValue(json, ItemDesc.class);
		System.out.println(itemDesc2.getItemDesc());
	}

	@Test
	public void testListJson() throws JsonProcessingException {
		List<ItemDesc> list=new ArrayList<ItemDesc>();
		ItemDesc itemDesc=new ItemDesc(); 
		itemDesc.setItemDesc("无极剑圣")
		.setItemId(88L)
		.setCreated(new Date())
		.setUpdated(new Date());
		ItemDesc itemDesc2=new ItemDesc(); 
		itemDesc.setItemDesc("蛮王")
		.setItemId(88L)
		.setCreated(new Date())
		.setUpdated(new Date());
		list.add(itemDesc);
		list.add(itemDesc2);
		String json =ObjectMapperUtil.toJson(list);
		System.out.println(json);
		List<ItemDesc> list2 =ObjectMapperUtil.toObject(json, list.getClass());
	    System.out.println(list2);
	}
	/**
	 * 简述json转化的实现原理
	 * 答:对象转化json,实际调用对象的get方法,获取属性和属性值 
	 */
	@Test
	public void test01() {
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemDesc("无极剑圣")
		.setItemId(88L)
		.setCreated(new Date())
		.setUpdated(new Date());
		String json = ObjectMapperUtil.toJson(itemDesc);
		System.out.println(json);
		ItemDesc itemDesc1=ObjectMapperUtil.toObject(json,itemDesc.getClass());
	    System.out.println(itemDesc1);
	}

}
