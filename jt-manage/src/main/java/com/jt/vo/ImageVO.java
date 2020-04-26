package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class ImageVO {
	// {"error":0,"url":"图片的保存路径","width":图片的宽度,"height":图片的高度}
   private Integer error;
   private String url;    //图片虚拟访问路径
   private Integer width;
   private Integer height;
 
   //调用方便,准备API
   public static ImageVO fail() {
	   return new ImageVO(1,null,null,null);
   }
   public static ImageVO success(String url,Integer width,Integer height) {
	   return new ImageVO(0,url,width,height);
   }
}
