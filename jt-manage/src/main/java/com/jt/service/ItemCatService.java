package com.jt.service;

import java.util.List;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

public interface ItemCatService {

	String findItemCatService(Long itemCatId);

	List<EasyUITree> findItemCatListByParentId(Long parentId);

	List<EasyUITree> findItemCatCache(Long parentId);
}
