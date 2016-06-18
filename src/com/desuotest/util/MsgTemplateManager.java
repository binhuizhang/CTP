package com.desuotest.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.desuotest.message.template.AccountRiskMsgData;
import com.desuotest.message.template.BaseContent;
import com.desuotest.message.template.BaseMsgData;
import com.desuotest.message.template.MsgTemplate;
import com.desuotest.pojo.AccessToken;
import com.desuotest.pojo.BaseUserInfo;

/** 
 * 消息模板发送管理器类 
 *  
 * @author zbh 
 * @date 2016-05-31
 */  
public class MsgTemplateManager {  
    private static Logger log = LoggerFactory.getLogger(MsgTemplateManager.class);  
    
    
    public static void main(String[] args) {  

        // 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(ConstArgs.appId, ConstArgs.appSecret);  
  
        if (null != at) {  
        	
        	// 拼装账户风险模板消息内容
        	String tempID = "SrEHBzUvlwxocduca07AXt25EXLwXeS9p8fKmHVYwQ0";
        	String toUser = "oe5_xssdznERPEivmXMxdCpmf6bI"; //用户对于每一个公众号都唯一的OpenID
        	String detailUrl = "https://www.baidu.com/";
        	String topColor = "#FF0000";
        	AccountRiskMsgData data = new AccountRiskMsgData();
        	
        	//开头语
        	BaseContent first = new BaseContent();
        	//根据openID 抓取用户昵称
        	
        	BaseUserInfo userInfo = UserInfoManager.getUserInfo(at.getToken(), toUser);
        	
        	first.setValue("尊敬的："+userInfo.getNickName()+" 您好！\n您的期货账户刚刚触发以下风险提醒：");
        	first.setColor("#000000");
        	data.setFirst(first);
        	
        	//结算日期
        	Date date=new Date();//获取时间
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        	BaseContent keyword1 = new BaseContent();
        	keyword1.setValue(sdf.format(date));
        	keyword1.setColor("#173177");
        	data.setKeyword1(keyword1);

        	//持仓品种
        	BaseContent keyword2 = new BaseContent();
        	keyword2.setValue("Cu");
        	keyword2.setColor("#173177");
        	data.setKeyword2(keyword2);
        	
        	//持仓风险
        	BaseContent keyword3 = new BaseContent();
        	keyword3.setValue("98%");
        	keyword3.setColor("#173177");
        	data.setKeyword3(keyword3);
        	
        	//动态权益
        	BaseContent keyword4 = new BaseContent();
        	keyword4.setValue("123456");
        	keyword4.setColor("#173177");
        	data.setKeyword4(keyword4);
        	
        	//提醒内容
        	BaseContent keyword5 = new BaseContent();
        	keyword5.setValue("请您及时追加保证金或降低仓位");
        	keyword5.setColor("#173177");
        	data.setKeyword5(keyword5);
        	
        	//结束语
        	BaseContent remark = new BaseContent();
        	remark.setValue("感谢您的使用！");
        	remark.setColor("#000000");
        	data.setRemark(remark);
        	
        	MsgTemplate accountRiskMsgTemp = getMsgTemplate(tempID,toUser,detailUrl,topColor,data);
        	
        	// 调用模板消息发送  
            int result = WeixinUtil.msgTemplateSend(accountRiskMsgTemp, at.getToken());
  
            // 判断菜单创建结果  
            if (0 == result)  
                log.info("模板消息发送成功！");  
            else  
                log.info("模板消息发送失败，错误码：" + result);  
                
        }  
    }  
    
    
    /** 
     * 组装消息模板数据 
     *  
     * @return MsgTemplate
     */  
    private static MsgTemplate getMsgTemplate(
    		String tempID,String toUser,String detailUrl,String topColor,BaseMsgData data){
    	MsgTemplate msgTemplate = new MsgTemplate();

    	msgTemplate.setTouser(toUser);
    	msgTemplate.setTemplate_id(tempID);
    	msgTemplate.setUrl(detailUrl);
    	msgTemplate.setTopcolor(topColor);
    	msgTemplate.setData(data);
    	return msgTemplate;
    }  
}  
