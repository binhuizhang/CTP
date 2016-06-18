package com.desuotest.pojo;

import java.util.List;

public class NewsMaterial extends BaseMaterial{
	private List<News> news_item;

	public List<News> getNews_item() {
		return news_item;
	}

	public void setNews_item(List<News> news_item) {
		this.news_item = news_item;
	}
	
	
}
