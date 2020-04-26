package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;

public interface ItemMapper extends BaseMapper<Item>{


	@Select("select * from tb_item order by updated desc limit #{start},#{rows}")
	List<Item> findItemByPage(int start, Integer rows);

	void deleteByIds(@Param("ids")Long... ids);
 /**
  * mybatis多表传值将数据封装成map集合
  * @param ids
  * @param status
  */
	void updateItemStatus(Long[] ids, int status);
	
}
