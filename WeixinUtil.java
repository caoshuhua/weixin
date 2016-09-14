package com.mbv.weixin.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeixinUtil {
	
	
	public static String getToken(String grant_type, String appid,String secret) {
		StringBuffer bufferRes = new StringBuffer();

        try {
//        							   https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
                URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type="+ grant_type+"&appid=" +appid + "&secret="+secret);
                					   
                HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
                // 连接超时
                conn.setConnectTimeout(25000);
                // 读取超时 --服务器响应比较慢,增大时间
                conn.setReadTimeout(25000);
                HttpURLConnection.setFollowRedirects(true);
                // 请求方式
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");
                conn.setRequestProperty("Referer", "https://api.weixin.qq.com/");
                conn.connect();

//                // 获取URLConnection对象对应的输出流
//                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
//                // 发送请求参数
//                //out.write(URLEncoder.encode(params,"UTF-8"));
//                out.write(params);
//                out.flush();
//                out.close();
                InputStream in = conn.getInputStream();
                BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));

                String valueString = null;
                while ((valueString=read.readLine())!=null){
                        bufferRes.append(valueString);
                }   
                in.close();
                if (conn != null) {
                        // 关闭连接
                        conn.disconnect();
                }
//              return bufferRes.toString();
                
              String result = bufferRes.toString();
  			  JSONObject resultObject =  JSONObject.fromObject(result);
  			  return resultObject.getString("access_token");

        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
	}
	
	public JSONObject httpRequest(String requestUrlString, String method, String jsonString){
		
		JSONObject resultObject = null;
		try{
			URL url = new URL(requestUrlString);  //创建url连接
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); //打开连接
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod(method);
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
			System.out.println(result);
			resultObject =  JSONObject.fromObject(result);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultObject;
	}
	
//	
//	/**
//  	 * 主动发送消息
//  	 * @param mess
//  	 * @return
//  	 */
//  	public  boolean sendReqMsg(ReqBaseMessage mess){
//  		//消息json格式
//  		String jsonContext=mess.toJsonStr();
//  		//System.out.println(jsonContext);
//  		//获得token
//  		String token=getTokenFromWx();
//		 boolean flag=false;
//		 try {
//			 CloseableHttpClient httpclient = HttpClients.createDefault();
//			 HttpPost httpPost= new HttpPost("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+token);
//			 //发送json格式的数据
//			 StringEntity myEntity = new StringEntity(jsonContext, 
//					   ContentType.create("text/plain", "UTF-8"));
//			 //设置需要传递的数据
//			 httpPost.setEntity(myEntity);
//			 // Create a custom response handler
//            ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {
//            	//对访问结果进行处理
//                public JSONObject handleResponse(
//                        final HttpResponse response) throws ClientProtocolException, IOException {
//                    int status = response.getStatusLine().getStatusCode();
//                    if (status >= 200 && status < 300) {
//                        HttpEntity entity = response.getEntity();
//                        if(null!=entity){
//                        	String result= EntityUtils.toString(entity);
//                            //根据字符串生成JSON对象
//                   		 	JSONObject resultObj = JSONObject.fromObject(result);
//                   		 	return resultObj;
//                        }else{
//                        	return null;
//                        }
//                    } else {
//                        throw new ClientProtocolException("Unexpected response status: " + status);
//                    }
//                }
//
//            };
//          //返回的json对象
//            JSONObject responseBody = httpclient.execute(httpPost, responseHandler);
//            System.out.println(responseBody);
//            int result= (Integer) responseBody.get("errcode");
//            if(0==result){
//            	flag=true;
//            }else{
//            	flag=false;
//            }
//			httpclient.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 return flag;
//	}
}
