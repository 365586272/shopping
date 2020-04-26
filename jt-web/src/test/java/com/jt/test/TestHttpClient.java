package com.jt.test;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.jasper.tagplugins.jstl.core.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jt.util.HttpClientService;

@SpringBootTest
public class TestHttpClient {

	/**
	 * 1.实例化HttpClient对象
	 * 2.请求url地址
	 * 3.定义请求方式get/put/post/delete
	 * 4.利用api发起http请求,获取响应结果 
	 * 5.判断返回值,校验状态码信息
	 * 6.状态码200正确,动态获取返回值信息
	 */
	@Test
	public void test01() {
		//创建新连接
		HttpClient httpClient = HttpClients.createDefault();
	    String uri="https://www.baidu.com";
	    HttpGet httpGet=new HttpGet(uri);
	    try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int code = httpResponse.getStatusLine().getStatusCode();
			String reason = httpResponse.getStatusLine().getReasonPhrase();
			System.out.println("返回值信息:"+code+","+reason);
		    if(code==200) {
		    	HttpEntity entity = httpResponse.getEntity();
		    	String result = EntityUtils.toString(entity, "utf-8");
		    	System.out.println(result);
		    }
	    } catch (IOException e) {
			e.printStackTrace();
		}
	}
//	@Test
//	public void test02() {
//		HttpClient httpClient=HttpClients.createDefault();
//		//HttpUriRequest request;
//		String uri="https://www.baidu.com";
//		
//		HttpGet httpGet=new HttpGet(uri);
//		try {
//			HttpResponse response = httpClient.execute(httpGet);
//			int code = response.getStatusLine().getStatusCode();
//			String reason = response.getStatusLine().getReasonPhrase();
//			if(code==200) {
//				HttpEntity entity = response.getEntity();
//				String result = EntityUtils.toString(entity,"utf-8");
//				System.out.println(result);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	@Autowired
	private HttpClientService httpClientService;
	@Test
	public void test03() {
		String url="http://www.baidu.com";
		Map<String , String> params=new HashMap<String, String>();
		params.put("id", "1");
		params.put("name", "小明");
		String result = httpClientService.doGet(url, params, null);
		System.out.println(result);
		
	}
	public static void main(String[] args) {
		
	}
	
}
