package com.mbv.weixin.web.rest.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OAuth2Utils{
	
	private static Logger log = LoggerFactory.getLogger(OAuth2Utils.class);
	
	private static String APPID = "xxx";//这个是你服务号appid，和URL里面的appid是一个意思
	private static String APPSECRET = "xxx"; //这个是你服务号的app秘钥
	private static String ACCESS_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code"; //这个是请求获取用户信息的URL

	public static String getOpenId(HttpServletRequest request) {
		log.info("来自微信的请求...");
		String openid = "";   //用来接收用户的appid
		try{
			request.setCharacterEncoding("UTF-8");
			String code = request.getParameter("code");//获取OAuth2.0请求后，服务器返回的code内容,这个code在接下来向微信服务请求用户信息的时候要用到
			log.info(code);
			String requestUrlString = ACCESS_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET).replace("CODE", code);//将请求用户的URL中的///参数替换成真正的内容
			URL url = new URL(requestUrlString);  //创建url连接
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); //打开连接
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.setUseCaches(false);
			urlConnection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			//<span style="font-family: Arial, Helvetica, sans-serif;">//存储服务器返回的信息</span>
			String line = ""; 
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			String result = buffer.toString();
			log.info("result:"+result);
			JSONObject resultObject =  JSONObject.fromObject(result);
			openid = resultObject.getString("openid");  //获取得到用户appid
			log.info("openid:"+openid);
			
			request.setAttribute("result", result);
			request.setAttribute("code", code);
			request.setAttribute("openid", openid);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return openid;
	}
	


}
