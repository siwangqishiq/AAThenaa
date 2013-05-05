package com.airad.athena.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.airad.athena.data.model.Description;
import com.airad.athena.data.model.Product;
import com.airad.athena.data.model.Worker;

/**
 * 转换
 * 
 * @author Panyi
 * 
 */
public class DataSwitchService {

	/**
	 * 将请求的json数据转换为List列表
	 * 
	 * @param origin
	 * @return
	 */
	public ArrayList<Worker> getWorkList(String origin) {
		ArrayList<Worker> workers = new ArrayList<Worker>();
		try {
			JSONObject all = new JSONObject(origin);
			JSONArray array = all.getJSONArray("listdata");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Worker worker = new Worker();
				worker.setId(i);
				worker.setDes(obj.getString("des"));
				worker.setPic(obj.getString("picUrl"));
				workers.add(worker);
			}// endfor
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return workers;
	}
	
	/**
	 * json格式转换成Product的List列表
	 * @param origin
	 * @return
	 */
	public ArrayList<Product> getProductList(String origin){
		ArrayList<Product> products=new ArrayList<Product>();
		try {
			JSONObject all = new JSONObject(origin);
			JSONArray array = all.getJSONArray("listdata");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Product item = new Product();
				item.setId(obj.getInt("id"));
				item.setName(obj.getString("name"));
				item.setPicUrl(obj.getString("picUrl"));
				item.setPrice(obj.getString("price"));
				products.add(item);
			}// endfor
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return products;
	}
	
	public Product getProduct(String origin){
		Product record=new Product();
		try {
			JSONObject obj=(new JSONObject(origin)).getJSONObject("data");
			record.setId(obj.getInt("id"));
			record.setName(obj.getString("name"));
			record.setPicUrl(obj.getString("picUrl"));
			record.setPrice(obj.getString("price"));
			return record;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Description getDescription(String origin){
		Description record=new Description();
		try {
			JSONObject obj=new JSONObject(origin);
			record.setContent(obj.getString("content"));
			JSONArray array = obj.getJSONArray("picList");
			for(int i=0;i<array.length();i++){
				record.getPicList().add(array.getString(i));
			}
			return record;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<String> getWelList(String origin){
		ArrayList<String> list=new ArrayList<String>();
		try {
			JSONArray array =new JSONObject(origin).getJSONArray("list");
			for(int i=0;i<array.length();i++){
				list.add(array.getString(i));
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
