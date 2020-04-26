package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.ImageVO;
@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {

	@Value("${image.localDir}")
	private String localDir;//="E:/Jt_Image/";
	@Value("${image.urlPath}")
	private String urlPath;
	/**
	 * 文件上传的步骤:
	 * 1.校验是否为图片  a.jpg
	 * 2.防止用户上传恶意程序
	 * 3.分目录的存储
	 * 4.防止重名现象
	 * 解决方案:
	 * 1.利用图片的后缀完成校验
	 * 2.利用图片的固有属性    宽度/高度 判断
	 * 3.可以根据时间进行目录划分
	 * 4.利用UUID方式动态生成文件名称
	 */
	@Override
	public ImageVO upLoad(MultipartFile uploadFile) {
		//1.获取图片名称
		String fileName = uploadFile.getOriginalFilename();
		//2.将图片名称转化为小写
		fileName=fileName.toLowerCase();
		//3.正则判断字符串是否符合要求
		if(!fileName.matches("^.+\\.(png|jpg|gif|jpeg)$")) {
			return ImageVO.fail();
		}
		//4.恶意程序判断
		try {
			//获得图片的信息
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if(width==0||height==0) {
				//上传的不是图片,是恶意程序
				return ImageVO.fail();
			}
			//5.确实是图片的上传,按照时间将目录进行划分
			String dateDir = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
			//6.文件目录
			String localFileDir=localDir+dateDir;
			File fileDir=new File(localFileDir);
			if(!fileDir.exists()) {//如果当前目录不存在
				//fileDir.mkdir();//创建一级目录
				fileDir.mkdirs();//创建多级目录
			}
			//动态生成文件名称
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileType=fileName.substring(fileName.lastIndexOf("."));
			String realFileName=uuid+fileType;//xxx.png
			//8.实现真实文件上传
			File imageFile=new File(localFileDir+realFileName);
			uploadFile.transferTo(imageFile);
			//String url="https://img14.360buyimg.com/n0/jfs/t1/89022/18/12746/196563/5e4e1d55E05fa4c68/4995cc0c6b0c53c7.jpg";
			
			String url=urlPath+dateDir+realFileName;
			return ImageVO.success(url, width, height);

		} catch (IOException e) {
			e.printStackTrace();
			return ImageVO.fail();
		}



	}

}
