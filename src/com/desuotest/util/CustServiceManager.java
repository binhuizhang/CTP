package com.desuotest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.desuotest.pojo.AccessToken;
import com.desuotest.pojo.CustomerServiceAdd;

import net.sf.json.JSONObject;

public class CustServiceManager {

	private static Logger log = LoggerFactory.getLogger(CustServiceManager.class);  
	public static String getCustServiceUrl = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";
	public static String addCustServiceUrl = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
	
	public static void main(String args[]){

		// 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(ConstArgs.appId, ConstArgs.appSecret);  
        
        if (null != at) {  

        	
        	//添加客服
        	CustomerServiceAdd custService = new CustomerServiceAdd();
        	//完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。
        	//如果没有公众号微信号，请前往微信公众平台设置。 
        	custService.setKf_account("desuo@gh_abfbd0e1ecf2");
        	custService.setNickname("zhang");
        	custService.setPassword(WeixinUtil.str2MD5("pa55word"));
        	addCustService(addCustServiceUrl,at.getToken(),custService);
        	
        	//获取客服列表
        	getCustService(getCustServiceUrl,at.getToken());
        }  
		
	}
	
	public static void getCustService(String url,String accessToken){
        // 调用接口获取客服信息
    	url = url.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
        log.info(jsonObject.toString());
	}
	
	public static void addCustService(String url,String accessToken,CustomerServiceAdd custService){
		 // 调用接口添加客服信息
    	url = url.replace("ACCESS_TOKEN", accessToken);
    	// 将要添加的客服对象转换成json字符串  
        String jsonCustServiceAdd = JSONObject.fromObject(custService).toString();
        log.info(jsonCustServiceAdd);
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonCustServiceAdd);
        log.info(jsonObject.toString());
	}
}
















