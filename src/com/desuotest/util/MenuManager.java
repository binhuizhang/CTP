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
 * 菜单管理器类 
 *  
 * @author zbh 
 * @date 2016-05-31
 */  
public class MenuManager {  
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);  

    
    
    // 授权后重定向的地址
    private static String redirUrl = "http://datasolution.duapp.com/desuo/oauthServlet";
    
    
    public static void main(String[] args) {  

  
        // 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(ConstArgs.appId, ConstArgs.appSecret);  
  
        if (null != at) {  
            // 调用接口创建菜单  
            int result = WeixinUtil.createMenu(getMenu(), at.getToken());  
  
            // 判断菜单创建结果  
            if (0 == result)  
                log.info("菜单创建成功！");  
            else  
                log.info("菜单创建失败，错误码：" + result);  
        }  
    }  
  
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    private static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("账单查询");  
        btn11.setType("click");  
        btn11.setKey("11");  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("实时资金");  
        btn12.setType("click");  
        btn12.setKey("12");  
  
        CommonButton btn13 = new CommonButton();  
        btn13.setName("持仓信息");  
        btn13.setType("click");  
        btn13.setKey("13");  
  
        CommonButton btn14 = new CommonButton();  
        btn14.setName("交易分析");  
        btn14.setType("click");  
        btn14.setKey("14");  
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("产品1");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("产品2");  
        btn22.setType("click");  
        btn22.setKey("22");  
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("产品3");  
        btn23.setType("click");  
        btn23.setKey("23");  
  
        CommonButton btn24 = new CommonButton();  
        btn24.setName("产品4");  
        btn24.setType("click");  
        btn24.setKey("24");  
  
        ViewButton btn25 = new ViewButton();  
        btn25.setName("查看UserAgent");  
        btn25.setType("view");  
        btn25.setUrl("http://datasolution.duapp.com/desuo/index.jsp");
  
        CommonButton btn31 = new CommonButton();  
        btn31.setName("重置密码");  
        btn31.setType("click");  
        btn31.setKey("31");  
  
        CommonButton btn32 = new CommonButton();  
        btn32.setName("银期变更");  
        btn32.setType("click");  
        btn32.setKey("32");  
  
        ViewButton btn33 = new ViewButton();  
        btn33.setName("第三方链接");  
        btn33.setType("view");
        
        String urladdress = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        urladdress = urladdress.replace("APPID", ConstArgs.appId);
        //必须强调的是编码方式必须正确，如baidu的是gb2312，而google的是UTF-8
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
        mainBtn1.setName("交易查询");  
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14 });  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("理财产品");  
        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("网上营业厅");  
        
        //这里改成new Button[] 是因为btn33和另外两个btn的类型不同；
        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }  
}  
