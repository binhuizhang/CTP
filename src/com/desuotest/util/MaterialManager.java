package com.desuotest.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.desuotest.pojo.AccessToken;
import com.desuotest.pojo.BaseMaterial;
import com.desuotest.pojo.CommonMaterial;
import com.desuotest.pojo.MaterialList;
import com.desuotest.pojo.News;
import com.desuotest.pojo.NewsMaterial;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MaterialManager {
	private static Logger log = LoggerFactory.getLogger(UserInfoManager.class);
	
	public static void main(String args[]){
		// 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(ConstArgs.appId, ConstArgs.appSecret);
        
        //抓取图文素材列表
        MaterialList materialList = batchget_material(at.getToken(),"news",0,20);
        System.out.println(materialList.getMaterials().size()+"\n\n");
        List<BaseMaterial> materialContentList = materialList.getMaterials();
        for(int i=0;i<materialList.getItem_count();i++){
        	NewsMaterial newsMaterial = (NewsMaterial) materialContentList.get(i);
        	System.out.println("Media_id: "+newsMaterial.getMedia_id());
        	System.out.println("Update_time: "+newsMaterial.getUpdate_time());
        	List<News> newsList = newsMaterial.getNews_item();
        	for(int j=0;j<newsList.size();j++){
        		News news = newsList.get(j);
        		System.out.println("Author: "+news.getAuthor());
        		System.out.println("Content: "+news.getContent());
        		System.out.println("Content_source_url: "+news.getContent_source_url());
        		System.out.println("Digest: "+news.getDigest());
        		System.out.println("Show_cover_pic: "+news.getShow_cover_pic());
        		System.out.println("Thumb_media_id: "+news.getThumb_media_id());
        		System.out.println("Title: "+news.getTitle());
        		System.out.println("Url: "+news.getUrl()+"\n");
        	}
        	System.out.println("\n\n\n");
        }
        
        //抓取图片素材列表
        MaterialList imgMaterialList = batchget_material(at.getToken(),"image",0,20);
        List<BaseMaterial> imgMaterialContentList = imgMaterialList.getMaterials();
        for(int k=0;k<imgMaterialList.getItem_count();k++){
        	CommonMaterial imgMaterial = (CommonMaterial) imgMaterialContentList.get(k);
        	System.out.println("Media_id: "+imgMaterial.getMedia_id());
        	System.out.println("Update_time: "+imgMaterial.getUpdate_time());
        	System.out.println("Name: "+imgMaterial.getName());
        	System.out.println("URL: "+imgMaterial.getUrl());
        	System.out.println("\n\n\n");
        }
        
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static MaterialList batchget_material(String accessToken,String type,int offset,int count){
		MaterialList materialList = null;
		String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", accessToken);
		String jsonData = "{\"type\":\"%s\",\"offset\":%d,\"count\":%d}";
		jsonData = String.format(jsonData, type,offset,count);
		
		JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonData);
		if(jsonObject!=null){
			log.info(jsonObject.toString());
			materialList = new MaterialList();
			materialList.setTotal_count(jsonObject.getInt("total_count"));
			materialList.setItem_count(jsonObject.getInt("item_count"));
			
			JSONArray itemArray = jsonObject.getJSONArray("item");
			//materialList的第三个参数为List<BaseMaterial> materials
			List<BaseMaterial> baseMaterialList = null;
			
			
			//如果请求的是图文消息
			if(type.equals("news")){
				baseMaterialList = new ArrayList<BaseMaterial>();
				
				//从jsonArray里面一个个地把NewsMaterial拼出来
				for(int i=0;i<itemArray.size();i++){
					JSONObject newsObject = (JSONObject) itemArray.get(i);
					
					NewsMaterial newsMaterial = new NewsMaterial();
					newsMaterial.setMedia_id(newsObject.getString("media_id"));
					newsMaterial.setUpdate_time(newsObject.getString("update_time"));
					//把json中News的Array 转为List
					JSONObject contentObject = newsObject.getJSONObject("content");
					JSONArray news_itemArray = contentObject.getJSONArray("news_item");
					List<News> newsList = JSONArray.toList(news_itemArray,News.class);
					newsMaterial.setNews_item(newsList);
					
					//拼好了NewsMaterial,就添加到newsMaterialList中
					baseMaterialList.add(newsMaterial);
				}
			}else{
				//jsonArray可以直接转为CommonMaterial List
				baseMaterialList = JSONArray.toList(itemArray,CommonMaterial.class);
			}
			
			materialList.setMaterials(baseMaterialList);
			
		}
		
		return materialList;
	}
}
