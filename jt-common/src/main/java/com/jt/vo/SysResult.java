package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//定义系统返回值得对象
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
   private Integer status;//状态码信息 200正确 201
   private String message;//服务器返回给页面的提示信息
   private Object data;
   /**
    * 封装API方法,完成用户调用
    */
   public static SysResult fail(String message) {
	   
	   return new SysResult(201, message, null);
   }
   public static SysResult success() {
	   return new SysResult(200, "业务调用正常", null);
   }
   public static SysResult success(Object data) {
	   return new SysResult(200, "业务调用正常", data);
   }
   public static SysResult success(String message,Object data) {
	   return new SysResult(200,message, data);
   }

}
