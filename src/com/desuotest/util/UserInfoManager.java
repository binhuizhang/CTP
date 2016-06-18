package com.desuotest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.desuotest.pojo.AccessToken;
import com.desuotest.pojo.BaseUserInfo;
import com.desuotest.pojo.UserList;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class UserInfoManager {
	private static Logger log = LoggerFactory.getLogger(UserInfoManager.class);  

	public static void main(String args[]){
		// 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(ConstArgs.appId, ConstArgs.appSecret);
        log.info(at.getToken());
		UserList userList = getUserList(at.getToken(),"");
		for(int i=0;i<userList.getCount();i++){
			String openID = userList.getOpenIdList().get(i);
			System.out.println(openID);
			BaseUserInfo userInfo = getUserInfo(at.getToken(),openID);
			System.out.println(userInfo.getNickName());
		}
		
		
	}
	
	public static BaseUserInfo getUserInfo(String accessToken,String openID){
		BaseUserInfo userInfo = null;
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url = url.replace("ACCESS_TOKEN", accessToken);
    	url = url.replace("OPENID", openID);
    	JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
    	if(jsonObject!=null){
        	try{
        		userInfo = new BaseUserInfo();
        		userInfo.setNickName(jsonObject.getString("nickname"));
        	}catch(JSONException e){
        		int errorCode = jsonObject.getInt("errcode");
        		String errorMsg = jsonObject.getString("errmsg");
        		log.error("获取用户信息失败 errcode:{} errmsg:{}",errorCode,errorMsg);
        	}
    	}
		return userInfo;
	}
	
	
	/**
	 * 获取关注者列表
	 * @param accessToken
	 * @param nextOpenId
	 * @return UserList
	 */
	@SuppressWarnings("unchecked")
	public static UserList getUserList(String accessToken,String nextOpenId){
        // 调用接口获取用户列表信息
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
    	url = url.replace("ACCESS_TOKEN", accessToken);
    	url = url.replace("NEXT_OPENID", nextOpenId);
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
        UserList userList = null;
        if(jsonObject!=null){
        	try{
        		log.info(jsonObject.toString());
            	userList = new UserList();
            	userList.setTotal(jsonObject.getInt("total"));
            	userList.setCount(jsonObject.getInt("count"));
            	userList.setNextOpenId(jsonObject.getString("next_openid"));
            	
            	//用这种方式将JsonArray 转化成一个List
            	JSONObject dataObject = (JSONObject) jsonObject.get("data");
            	userList.setOpenIdList(JSONArray.toList(dataObject.getJSONArray("openid"),List.class));
        	}
        	catch(JSONException e){
        		userList = null;
        		int errorCode = jsonObject.getInt("errcode");
        		String errorMsg = jsonObject.getString("errmsg");
        		log.error("获取关注者列表失败 errcode:{} errmsg:{}",errorCode,errorMsg);
        	}
        }
        return userList;
	}
	
}
