package com.desuotest.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.desuotest.message.resp.BaseMessageTransResp;
import com.desuotest.message.resp.TextMessageResp;
import com.desuotest.util.MessageUtil;

/** 
 * ���ķ����� 
 *  
 * @author liufeng 
 * @date 2013-05-20 
 */  
public class CoreService {  
    /** 
     * ����΢�ŷ��������� 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // Ĭ�Ϸ��ص��ı���Ϣ����  
            String respContent = "�������쳣�����Ժ��ԣ�";  
  
            // xml�������  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // ���ͷ��ʺţ�open_id��  
            String fromUserName = requestMap.get("FromUserName");  
            // �����ʺ�  
            String toUserName = requestMap.get("ToUserName");  
            // ��Ϣ����  
            String msgType = requestMap.get("MsgType");  
  
            // �ظ��ı���Ϣ  
            TextMessageResp textMessage = new TextMessageResp();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
  
            // �ı���Ϣ  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                //ת�����ͷ�
            	BaseMessageTransResp msgtrans = new BaseMessageTransResp();
            	msgtrans.setToUserName(fromUserName);
            	msgtrans.setFromUserName(toUserName);
            	msgtrans.setCreateTime(new Date().getTime());
            	msgtrans.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TRANS_CUST_SERVICE);
            	respMessage = MessageUtil.baseTransMessageToXml(msgtrans); 
            	return respMessage;
            }  
            // ͼƬ��Ϣ  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
                respContent = "�������ڵ���һЩ���ܣ����Ժ�ظ���лл��";  
            }  
            // ����λ����Ϣ  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "�������ڵ���һЩ���ܣ����Ժ�ظ���лл��";  
            }  
            // ������Ϣ  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "�������ڵ���һЩ���ܣ����Ժ�ظ���лл��";  
            }  
            // ��Ƶ��Ϣ  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "�������ڵ���һЩ���ܣ����Ժ�ظ���лл��";  
            }  
            // �¼�����  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // �¼�����  
                String eventType = requestMap.get("Event");  
                // ����  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                    respContent = "лл���Ĺ�ע��";  
                }  
                // ȡ������  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ  
                }  
                // �Զ���˵�����¼�  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                	// �¼�KEYֵ���봴���Զ���˵�ʱָ����KEYֵ��Ӧ
                	String eventKey = requestMap.get("EventKey");
                	if (eventKey.equals("11")) {  
                        respContent = "�˵���ѯ�������";  
                    } else if (eventKey.equals("12")) {  
                        respContent = "ʵʱ�ʽ𱻵����";  
                    } else if (eventKey.equals("13")) {  
                        respContent = "�ֲ���Ϣ�������";  
                    } else if (eventKey.equals("14")) {  
                        respContent = "���׷����������";  
                    } else if (eventKey.equals("21")) {  
                        respContent = "��Ʒ1�������";  
                    } else if (eventKey.equals("22")) {  
                        respContent = "��Ʒ2�������";  
                    } else if (eventKey.equals("23")) {  
                        respContent = "��Ʒ3�������";  
                    } else if (eventKey.equals("24")) {  
                        respContent = "��Ʒ4�������";  
                    } else if (eventKey.equals("25")) {  
                        respContent = "��Ʒ5�������";  
                    } else if (eventKey.equals("31")) {  
                        respContent = "�������뱻�����";  
                    } else if (eventKey.equals("32")) {  
                        respContent = "���ڱ���������";  
                    } else if (eventKey.equals("33")) {  
                        respContent = "���������ӱ������";  
                    }  
                }  
                // ģ����Ϣ���ͻظ�
                else if(eventType.equals(MessageUtil.EVENT_TYPE_TEMPLATESENDJOBFINISH)){
                	respContent = requestMap.get("Status")+"--------";
                }
            }  
  
            textMessage.setContent(respContent);  
            respMessage = MessageUtil.textMessageToXml(textMessage);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return respMessage;  
    }  
}