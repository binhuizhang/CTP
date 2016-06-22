package com.desuotest.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.desuotest.pojo.AccessToken;
import com.desuotest.pojo.Button;
import com.desuotest.pojo.CommonButton;
import com.desuotest.pojo.ComplexButton;
import com.desuotest.pojo.Menu;
import com.desuotest.pojo.ViewButton;

/** 
 * �˵��������� 
 *  
 * @author zbh 
 * @date 2016-05-31
 */  
public class MenuManager {  
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);  

    
    
    // ��Ȩ���ض���ĵ�ַ
    private static String redirUrl = "http://datasolution.duapp.com/desuo/oauthServlet";
    
    
    public static void main(String[] args) {  

  
        // ���ýӿڻ�ȡaccess_token  
        AccessToken at = WeixinUtil.getAccessToken(ConstArgs.appId, ConstArgs.appSecret);  
  
        if (null != at) {  
            // ���ýӿڴ����˵�  
            int result = WeixinUtil.createMenu(getMenu(), at.getToken());  
  
            // �жϲ˵��������  
            if (0 == result)  
                log.info("�˵������ɹ���");  
            else  
                log.info("�˵�����ʧ�ܣ������룺" + result);  
        }  
    }  
  
    /** 
     * ��װ�˵����� 
     *  
     * @return 
     */  
    private static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("�˵���ѯ");  
        btn11.setType("click");  
        btn11.setKey("11");  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("ʵʱ�ʽ�");  
        btn12.setType("click");  
        btn12.setKey("12");  
  
        CommonButton btn13 = new CommonButton();  
        btn13.setName("�ֲ���Ϣ");  
        btn13.setType("click");  
        btn13.setKey("13");  
  
        CommonButton btn14 = new CommonButton();  
        btn14.setName("���׷���");  
        btn14.setType("click");  
        btn14.setKey("14");  
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("��Ʒ1");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("��Ʒ2");  
        btn22.setType("click");  
        btn22.setKey("22");  
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("��Ʒ3");  
        btn23.setType("click");  
        btn23.setKey("23");  
  
        CommonButton btn24 = new CommonButton();  
        btn24.setName("��Ʒ4");  
        btn24.setType("click");  
        btn24.setKey("24");  
  
        ViewButton btn25 = new ViewButton();  
        btn25.setName("�鿴UserAgent");  
        btn25.setType("view");  
        btn25.setUrl("http://datasolution.duapp.com/desuo/index.jsp");
  
        CommonButton btn31 = new CommonButton();  
        btn31.setName("��������");  
        btn31.setType("click");  
        btn31.setKey("31");  
  
        CommonButton btn32 = new CommonButton();  
        btn32.setName("���ڱ��");  
        btn32.setType("click");  
        btn32.setKey("32");  
  
        ViewButton btn33 = new ViewButton();  
        btn33.setName("����������");  
        btn33.setType("view");
        
        String urladdress = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        urladdress = urladdress.replace("APPID", ConstArgs.appId);
        //����ǿ�����Ǳ��뷽ʽ������ȷ����baidu����gb2312����google����UTF-8
        String urladdr = "";
		try {
			urladdr = URLEncoder.encode(redirUrl, "UTF-8");
	        System.out.println(urladdr);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			urladdr = redirUrl;
			e.printStackTrace();
		}
        
        urladdress = urladdress.replace("REDIRECT_URI", urladdr);
        urladdress = urladdress.replace("SCOPE", "snsapi_userinfo");
        urladdress = urladdress.replace("STATE", "123456");
        log.info(urladdress);
        btn33.setUrl(urladdress);  
        
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("���ײ�ѯ");  
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14 });  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("��Ʋ�Ʒ");  
        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("����Ӫҵ��");  
        
        //����ĳ�new Button[] ����Ϊbtn33����������btn�����Ͳ�ͬ��
        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });  
  
        /** 
         * ���ǹ��ں�xiaoqrobotĿǰ�Ĳ˵��ṹ��ÿ��һ���˵����ж����˵���<br> 
         *  
         * ��ĳ��һ���˵���û�ж����˵��������menu����ζ����أ�<br> 
         * ���磬������һ���˵���ǡ��������顱����ֱ���ǡ���ĬЦ��������ômenuӦ���������壺<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }  
}  
