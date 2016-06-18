package com.desuotest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.desuotest.pojo.SNSUserInfo;
import com.desuotest.pojo.WeixinOauth2Token;

import net.sf.json.JSONObject;

public class AdvanceUtil {

	public static Logger log = LoggerFactory.getLogger(AdvanceUtil.class);  
  
/**
 * ��ȡ��ҳ��Ȩƾ֤
 * 
 * @param appId ���ںŵ�Ψһ��ʶ
 * @param appSecret ���ںŵ���Կ
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
				log.info("��ȡ��ҳ��Ȩƾ֤�ɹ� access_token:{} errorMsg:{}",jsonObject.getString("access_token"),jsonObject.getString("openid"));
			}catch(Exception e){
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("��ȡ��ҳ��Ȩƾ֤ʧ�� errorCode:{} openid:{}",errorCode,errorMsg);
			}
		}
		
		return wat;
	}
	
	/**
	 * ͨ����ҳƾ֤��ȡ�û���Ϣ
	 * 
	 * @param access_token ��ҳƾ֤
	 * @param Openid �û��ڹ��ںŵ�Ψһ��ʶ

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
				//Ĭ��ͷ����0����640*640���أ��ĳ�96����
				headimgurl = headimgurl.substring(0,headimgurl.length()-1)+ "96";
				sui.setHeadimgurl(headimgurl);
				sui.setNickName(jso.getString("nickname"));
				sui.setOpenId(jso.getString("openid"));
				sui.setPrivilege(jso.getString("privilege"));
				sui.setProvince(jso.getString("province"));
				if(jso.getString("sex").equals("1")){
					sui.setSex("��");
				}else if(jso.getString("sex").equals("2")){
					sui.setSex("Ů");
				}else{
					sui.setSex("δ֪");
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
				log.info("ͨ����ҳ��Ȩ��ȡ�û���Ϣʧ��!");
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
