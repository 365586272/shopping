package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;

@RestController
public class FileController {

	@Autowired
	private FileService fileService;
	/**
	 * 文件上传案例
	 *参数 :fileImage
	 *提示:如果需要使用文件上传,一般使用inputStream进行参数
	 *后续:采用输出流实现文件输出
	 *
	 *优化: MultipartFile 封装了输入流/输出流
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
      public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
    	  //动态获取文件名称
		String fileName = fileImage.getOriginalFilename();
		//2.准备文件目录
		String dirPath="e:/Jt_Image/";
		//3.准备文件对象,拼接文件全路径
		File file = new File(dirPath+fileName);
		//4.实现文件上传
		fileImage.transferTo(file);
		return "文件上传";
      }
	/**
	 * 用户完成商品图片上传
	 * pic/upload?dir=image
	 * 参数:uploadFile
	 */
	@RequestMapping("/pic/upload")
	public ImageVO upLoad(MultipartFile uploadFile) {
		
		return fileService.upLoad(uploadFile);
	}
	
}
