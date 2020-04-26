package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_item_desc")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDesc extends BasePojo{
    private static final long serialVersionUID = -3532471496881435303L;
	@TableId//没有自增
	private Long itemId;
    private String itemDesc;
    
    public String getVip() {
    	return "jsonIgnore";
    }
}
