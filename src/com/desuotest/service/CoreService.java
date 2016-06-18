package com.desuotest.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.desuotest.message.resp.BaseMessageTransResp;
import com.desuotest.message.resp.TextMessageResp;
import com.desuotest.util.MessageUtil;

/** 
 * 核心服务类 
 *  
 * @author liufeng 
 * @date 2013-05-20 
 */  
public class CoreService {  
    /** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";  
  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
            // 回复文本消息  
            TextMessageResp textMessage = new TextMessageResp();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
  
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                //转发至客服
            	BaseMessageTransResp msgtrans = new BaseMessageTransResp();
            	msgtrans.setToUserName(fromUserName);
            	msgtrans.setFromUserName(toUserName);
            	msgtrans.setCreateTime(new Date().getTime());
            	msgtrans.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TRANS_CUST_SERVICE);
            	respMessage = MessageUtil.baseTransMessageToXml(msgtrans); 
            	return respMessage;
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
                respContent = "我们正在调试一些功能，请稍后回复，谢谢！";  
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "我们正在调试一些功能，请稍后回复，谢谢！";  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "我们正在调试一些功能，请稍后回复，谢谢！";  
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "我们正在调试一些功能，请稍后回复，谢谢！";  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                    respContent = "谢谢您的关注！";  
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                	// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                	String eventKey = requestMap.get("EventKey");
                	if (eventKey.equals("11")) {  
                        respContent = "账单查询被点击！";  
                    } else if (eventKey.equals("12")) {  
                        respContent = "实时资金被点击！";  
                    } else if (eventKey.equals("13")) {  
                        respContent = "持仓信息被点击！";  
                    } else if (eventKey.equals("14")) {  
                        respContent = "交易分析被点击！";  
                    } else if (eventKey.equals("21")) {  
                        respContent = "产品1被点击！";  
                    } else if (eventKey.equals("22")) {  
                        respContent = "产品2被点击！";  
                    } else if (eventKey.equals("23")) {  
                        respContent = "产品3被点击！";  
                    } else if (eventKey.equals("24")) {  
                        respContent = "产品4被点击！";  
                    } else if (eventKey.equals("25")) {  
                        respContent = "产品5被点击！";  
                    } else if (eventKey.equals("31")) {  
                        respContent = "重置密码被点击！";  
                    } else if (eventKey.equals("32")) {  
                        respContent = "银期变更被点击！";  
                    } else if (eventKey.equals("33")) {  
                        respContent = "第三方链接被点击！";  
                    }  
                }  
                // 模板消息推送回复
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