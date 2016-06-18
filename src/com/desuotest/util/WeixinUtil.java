package com.desuotest.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.desuotest.message.template.MsgTemplate;
import com.desuotest.pojo.AccessToken;
import com.desuotest.pojo.Menu;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/** 
 * ����ƽ̨ͨ�ýӿڹ����� 
 *  
 * @author  
 * @date 2016-05-31 
 */  
public class WeixinUtil {  
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
  
    
    
 // ��ȡaccess_token�Ľӿڵ�ַ��GET�� ��200����/�죩  
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";    
    /** 
     * ��ȡaccess_token 
     *  
     * @param appid ƾ֤ 
     * @param appsecret ��Կ 
     * @return 
     */  
    public static AccessToken getAccessToken(String appid, String appsecret) {  
        AccessToken accessToken = null;  
      
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);  
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
        // �������ɹ�  
        if (null != jsonObject) {  
            try {  
                accessToken = new AccessToken();  
                accessToken.setToken(jsonObject.getString("access_token"));  
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
            } catch (JSONException e) {  
                accessToken = null;  
                // ��ȡtokenʧ��  
                log.error("��ȡtokenʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
        return accessToken;  
    }  
    
//����ģ����Ϣ��POST�� ��10�򣨴�/�죩
    public static String msg_template_send_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";  

    public static int msgTemplateSend(MsgTemplate megTemplate, String accessToken) {
    	int result = 0;
    	// ƴװ����ģ����Ϣ��url  
        String url = msg_template_send_url.replace("ACCESS_TOKEN", accessToken);  
        
        // ��ģ����Ϣ����ת����json�ַ���  
        String jsonMsgTemplate = JSONObject.fromObject(megTemplate).toString();
        
        //�����������滻�ɹ涨��ʽ
//        jsonMsgTemplate = jsonMsgTemplate.replace("date", "Date");
//        jsonMsgTemplate = jsonMsgTemplate.replace("money", "Money");
        
    	System.out.println(jsonMsgTemplate);
        
        // ���ýӿڷ���ģ����Ϣ  
        JSONObject jsonObject = httpRequest(url, "POST", jsonMsgTemplate);  
      
        if (null != jsonObject) {  
            if (0 != jsonObject.getInt("errcode")) {  
                result = jsonObject.getInt("errcode");  
                log.error("ģ����Ϣ����ʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
    	return result;
    }
    
    
 // �˵�������POST�� ��100����/�죩  
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
      
    /** 
     * �����˵� 
     *  
     * @param menu �˵�ʵ�� 
     * @param accessToken ��Ч��access_token 
     * @return 0��ʾ�ɹ�������ֵ��ʾʧ�� 
     */  
    public static int createMenu(Menu menu, String accessToken) {  
        int result = 0;  
      
        // ƴװ�����˵���url  
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
        // ���˵�����ת����json�ַ���  
        String jsonMenu = JSONObject.fromObject(menu).toString();  
        // ���ýӿڴ����˵�  
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);  
      
        if (null != jsonObject) {  
            if (0 != jsonObject.getInt("errcode")) {  
                result = jsonObject.getInt("errcode");  
                log.error("�����˵�ʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
      
        return result;  
    }  
    
    
    
    /** 
     * ����https���󲢻�ȡ��� 
     *  
     * @param requestUrl �����ַ 
     * @param requestMethod ����ʽ��GET��POST�� 
     * @param outputStr �ύ������ 
     * @return JSONObject(ͨ��JSONObject.get(key)�ķ�ʽ��ȡjson���������ֵ) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��  
            TrustManager[] tm = { new MyX509TrustManager()};  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // ������SSLContext�����еõ�SSLSocketFactory����  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // ��������ʽ��GET/POST��  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // ����������Ҫ�ύʱ  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // ע������ʽ����ֹ��������  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // �����ص�������ת�����ַ���  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // �ͷ���Դ  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
    
	/**
	 * ���ַ�������MD5���ܣ���ʵ����ת����MD5�ַ���
	 */
	public static String str2MD5(String password){
		String md5Str = "";

        try {
            //1 ����һ���ṩ��ϢժҪ�㷨�Ķ��󣬳�ʼ��Ϊmd5�㷨����
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            //2 ����Ϣ���byte����
            byte[] input = password.getBytes();
 
            //3 ��������ֽ�����,�������128λ��
            byte[] buff = md.digest(input);
 
            //4 ������ÿһ�ֽڣ�һ���ֽ�ռ��λ������16��������md5�ַ���
            md5Str = byteToStr(buff);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		return md5Str;
	}
	
	/**
	 * ���ֽ�����ת��Ϊʮ�������ַ���
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * ���ֽ�ת��Ϊʮ�������ַ���
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
}  