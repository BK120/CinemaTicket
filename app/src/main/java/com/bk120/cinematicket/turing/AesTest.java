package com.bk120.cinematicket.turing;


import com.alibaba.fastjson.JSONObject;

/**
 * 加密请求测试类
 * @author 图灵机器人
 *
 */
public class AesTest {
	
	public static String testAes(String mes){
		//图灵网站上的secret
		String secret = "7730738dfd04d7d5";
		//图灵网站上的apiKey
		String apiKey = "8ca3c5fff532411691961cf3b749b532";
		String cmd = mes;//测试用例
		//待加密的json数据
		String data = "{\"key\":\""+apiKey+"\",\"info\":\""+cmd+"\"}";
		//获取时间戳
		String timestamp = String.valueOf(System.currentTimeMillis());
		
		//生成密钥
		String keyParam = secret+timestamp+apiKey;
		String key = Md5.MD5(keyParam);
		
		//加密
		Aes mc = new Aes(key);
		data = mc.encrypt(data);		
		
		//封装请求参数
		JSONObject json = new JSONObject();
			json.put("key", apiKey);
			json.put("timestamp", timestamp);
			json.put("data", data);
			json.put("userid", "101");
		//请求图灵api
		String result = PostServer.SendPost(json.toString(), "http://www.tuling123.com/openapi/api");
		return result;
	}
	
}