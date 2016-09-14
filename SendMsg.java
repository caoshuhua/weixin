package com.mbv.weixin.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMsg {

	private static Logger log = LoggerFactory.getLogger(SendMsg.class);
	
	public static void main(String[] args) {
		String appId = "xxx";
		String appSecret = "xxx";
		String openId = "onJsuxCsxA9rTsk4K9CndDyXK7Us";
		String templateId = "xxx";
		String detailUrl = "http://xx.com/weixin/deglistget";
		String firstValue = "";
		String keyword1 = "1";
		String keyword2 = "2";
		String keyword3 = "";
		try {
			SendMsg.send_template_message(appId, appSecret, openId, templateId, detailUrl, firstValue, keyword1, keyword2, keyword3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 发送模板消息
     * appId 公众账号的唯一标识
     * appSecret 公众账号的密钥
     * openId 用户标识
     */
    public static void send_template_message(String appId, String appSecret, String openId, String templateId, String detailUrl, 
    		String firstValue, String keyword1, String keyword2, String keyword3) throws Exception{
        String access_token = WeixinUtil.getToken("client_credential", appId, appSecret);
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        WxTemplate temp = new WxTemplate();
        temp.setUrl(detailUrl);
        temp.setTouser(openId);
        temp.setTopcolor("#000000");
        temp.setTemplate_id(templateId);
        Map<String,TemplateData> m = new HashMap<String,TemplateData>();
        TemplateData first = new TemplateData();
        first.setColor("#000000");  
        first.setValue(firstValue);  
        m.put("first", first);  
        TemplateData name = new TemplateData();  
        name.setColor("#000000");  
        name.setValue(keyword1);  
        m.put("keyword1", name);
        TemplateData wuliu = new TemplateData();  
        wuliu.setColor("#000000");  
        wuliu.setValue("2");  
        m.put("keyword2", wuliu);
        TemplateData orderNo = new TemplateData();  
        orderNo.setColor("#000000");  
        orderNo.setValue(keyword2);  
        m.put("keyword3", orderNo);
        TemplateData remark = new TemplateData();  
        remark.setColor("#000000");  
        remark.setValue(keyword3);  
        m.put("remark", remark);
        temp.setData(m);
        String jsonString = JSONObject.fromObject(temp).toString();
        log.info("jsonString:"+jsonString);
        HttpClient httpclient = new DefaultHttpClient(); 
       //excute
 	   HttpPost post = new HttpPost(url);
 	   ResponseHandler<?> responseHandler = new BasicResponseHandler();
 	   post.setEntity(new StringEntity(jsonString.toString(),"UTF-8"));
 	   String response = (String) httpclient.execute(post, responseHandler);
 	   log.info(response);
 	   System.out.println(response);
    }
      
}
