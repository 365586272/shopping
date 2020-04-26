package com.jt.vo;

import java.util.List;

import com.jt.pojo.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITable  {
	/**
	 * 序列化作用:
	 *  对象在进行网络传输过程必须实现序列化接口
	 *  原理:对象需要传输,需要转换为字节数组
	 *  1.vo最终形成字符串,可以不序列化 2.vo对象传递,要序列化
	 */
	private Long total;//记录总数
	private List<Item> rows; //每页数据
}
