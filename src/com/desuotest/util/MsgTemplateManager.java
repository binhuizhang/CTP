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
 * ��Ϣģ�巢�͹������� 
 *  
 * @author zbh 
 * @date 2016-05-31
 */  
public class MsgTemplateManager {  
    private static Logger log = LoggerFactory.getLogger(MsgTemplateManager.class);  
    
    
    public static void main(String[] args) {  

        // ���ýӿڻ�ȡaccess_token  
        AccessToken at = WeixinUtil.getAccessToken(ConstArgs.appId, ConstArgs.appSecret);  
  
        if (null != at) {  
        	
        	// ƴװ�˻�����ģ����Ϣ����
        	String tempID = "SrEHBzUvlwxocduca07AXt25EXLwXeS9p8fKmHVYwQ0";
        	String toUser = "oe5_xssdznERPEivmXMxdCpmf6bI"; //�û�����ÿһ�����ںŶ�Ψһ��OpenID
        	String detailUrl = "https://www.baidu.com/";
        	String topColor = "#FF0000";
        	AccountRiskMsgData data = new AccountRiskMsgData();
        	
        	//��ͷ��
        	BaseContent first = new BaseContent();
        	//����openID ץȡ�û��ǳ�
        	
        	BaseUserInfo userInfo = UserInfoManager.getUserInfo(at.getToken(), toUser);
        	
        	first.setValue("�𾴵ģ�"+userInfo.getNickName()+" ���ã�\n�����ڻ��˻��ոմ������·������ѣ�");
        	first.setColor("#000000");
        	data.setFirst(first);
        	
        	//��������
        	Date date=new Date();//��ȡʱ��
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        	BaseContent keyword1 = new BaseContent();
        	keyword1.setValue(sdf.format(date));
        	keyword1.setColor("#173177");
        	data.setKeyword1(keyword1);

        	//�ֲ�Ʒ��
        	BaseContent keyword2 = new BaseContent();
        	keyword2.setValue("Cu");
        	keyword2.setColor("#173177");
        	data.setKeyword2(keyword2);
        	
        	//�ֲַ���
        	BaseContent keyword3 = new BaseContent();
        	keyword3.setValue("98%");
        	keyword3.setColor("#173177");
        	data.setKeyword3(keyword3);
        	
        	//��̬Ȩ��
        	BaseContent keyword4 = new BaseContent();
        	keyword4.setValue("123456");
        	keyword4.setColor("#173177");
        	data.setKeyword4(keyword4);
        	
        	//��������
        	BaseContent keyword5 = new BaseContent();
        	keyword5.setValue("������ʱ׷�ӱ�֤��򽵵Ͳ�λ");
        	keyword5.setColor("#173177");
        	data.setKeyword5(keyword5);
        	
        	//������
        	BaseContent remark = new BaseContent();
        	remark.setValue("��л����ʹ�ã�");
        	remark.setColor("#000000");
        	data.setRemark(remark);
        	
        	MsgTemplate accountRiskMsgTemp = getMsgTemplate(tempID,toUser,detailUrl,topColor,data);
        	
        	// ����ģ����Ϣ����  
            int result = WeixinUtil.msgTemplateSend(accountRiskMsgTemp, at.getToken());
  
            // �жϲ˵��������  
            if (0 == result)  
                log.info("ģ����Ϣ���ͳɹ���");  
            else  
                log.info("ģ����Ϣ����ʧ�ܣ������룺" + result);  
                
        }  
    }  
    
    
    /** 
     * ��װ��Ϣģ������ 
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
