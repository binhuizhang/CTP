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

		// ���ýӿڻ�ȡaccess_token  
        AccessToken at = WeixinUtil.getAccessToken(ConstArgs.appId, ConstArgs.appSecret);  
        
        if (null != at) {  

        	
        	//��ӿͷ�
        	CustomerServiceAdd custService = new CustomerServiceAdd();
        	//�����ͷ��˺ţ���ʽΪ���˺�ǰ׺@���ں�΢�źţ��˺�ǰ׺���10���ַ���������Ӣ�Ļ��������ַ���
        	//���û�й��ں�΢�źţ���ǰ��΢�Ź���ƽ̨���á� 
        	custService.setKf_account("desuo@gh_abfbd0e1ecf2");
        	custService.setNickname("zhang");
        	custService.setPassword(WeixinUtil.str2MD5("pa55word"));
        	addCustService(addCustServiceUrl,at.getToken(),custService);
        	
        	//��ȡ�ͷ��б�
        	getCustService(getCustServiceUrl,at.getToken());
        }  
		
	}
	
	public static void getCustService(String url,String accessToken){
        // ���ýӿڻ�ȡ�ͷ���Ϣ
    	url = url.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
        log.info(jsonObject.toString());
	}
	
	public static void addCustService(String url,String accessToken,CustomerServiceAdd custService){
		 // ���ýӿ���ӿͷ���Ϣ
    	url = url.replace("ACCESS_TOKEN", accessToken);
    	// ��Ҫ��ӵĿͷ�����ת����json�ַ���  
        String jsonCustServiceAdd = JSONObject.fromObject(custService).toString();
        log.info(jsonCustServiceAdd);
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonCustServiceAdd);
        log.info(jsonObject.toString());
	}
}
















