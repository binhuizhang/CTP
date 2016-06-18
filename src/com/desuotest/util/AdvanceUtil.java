package com.desuotest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.desuotest.pojo.SNSUserInfo;
import com.desuotest.pojo.WeixinOauth2Token;

import net.sf.json.JSONObject;

public class AdvanceUtil {

	public static Logger log = LoggerFactory.getLogger(AdvanceUtil.class);  
  
/**
 * 获取网页授权凭证
 * 
 * @param appId 公众号的唯一标识
 * @param appSecret 公众号的秘钥
 * @param code
 * @return WeixinAouth2Token
 */
	
	public static WeixinOauth2Token getOauth2AccessToken(String appId,String appSecret,String code){
		
		
		WeixinOauth2Token wat = null;
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
		if(null != jsonObject){
			try{
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setScope(jsonObject.getString("scope"));
				log.info("获取网页授权凭证成功 access_token:{} errorMsg:{}",jsonObject.getString("access_token"),jsonObject.getString("openid"));
			}catch(Exception e){
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取网页授权凭证失败 errorCode:{} openid:{}",errorCode,errorMsg);
			}
		}
		
		return wat;
	}
	
	/**
	 * 通过网页凭证获取用户信息
	 * 
	 * @param access_token 网页凭证
	 * @param Openid 用户在公众号的唯一标识

	 * @return SNSUserInfo
	 */
	public static SNSUserInfo getSNSUserInfo(String access_token, String openid) {
		SNSUserInfo sui = null;
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url = url.replace("ACCESS_TOKEN", access_token);
		url = url.replace("OPENID", openid);
		
		JSONObject jso = WeixinUtil.httpRequest(url, "GET", null);
		
		if(jso != null){
			try {
				sui = new SNSUserInfo();
				sui.setCity(jso.getString("city"));
				sui.setCountry(jso.getString("country"));
				String headimgurl = jso.getString("headimgurl");
				//默认头像是0，即640*640像素，改成96像素
				headimgurl = headimgurl.substring(0,headimgurl.length()-1)+ "96";
				sui.setHeadimgurl(headimgurl);
				sui.setNickName(jso.getString("nickname"));
				sui.setOpenId(jso.getString("openid"));
				sui.setPrivilege(jso.getString("privilege"));
				sui.setProvince(jso.getString("province"));
				if(jso.getString("sex").equals("1")){
					sui.setSex("男");
				}else if(jso.getString("sex").equals("2")){
					sui.setSex("女");
				}else{
					sui.setSex("未知");
				}
				
//				sui.setUnionId(jso.getString("unionid"));
			} catch(Exception e){
				sui = new SNSUserInfo();
				sui.setCity(e.getMessage().toString());
				sui.setCountry("empty--");
				sui.setHeadimgurl(e.getStackTrace().toString());
				sui.setNickName(e.getStackTrace().toString());
				sui.setOpenId("empty--");
				sui.setPrivilege("empty--");
				sui.setProvince("empty--");
				sui.setSex("empty--");
//				sui.setUnionId("empty--");
				log.info("通过网页授权获取用户信息失败!");
			}
		} else{
			sui = new SNSUserInfo();
			sui.setCity("empty");
			sui.setCountry("empty");
			sui.setHeadimgurl("empty");
			sui.setNickName("empty");
			sui.setOpenId("empty");
			sui.setPrivilege("empty");
			sui.setProvince("empty");
			sui.setSex("empty");
//			sui.setUnionId("empty");
		}
		return sui;
	}

}
