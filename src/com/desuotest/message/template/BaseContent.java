package com.desuotest.message.template;

/** 
 * 消息模板内容的基类 
 *  
 * @author zbh 
 * @date 2016-06-01 
 */  
public class BaseContent {
	
	private String value;
	private String color;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
