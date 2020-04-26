package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tb_item_cat")
public class ItemCat extends BasePojo {
	private static final long serialVersionUID = -8521677687364567626L;
	@TableId(type = IdType.AUTO)
	private Long id;
	private Long parentId;	//父级ID
	private String name;
	private Integer status;		//父级状态信息 1用  2 不用
	private Integer sortOrder;	//排序号
	private Boolean isParent;	//是否为父级 true  false
}
