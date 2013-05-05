package com.airad.athena.data.model;

import java.util.ArrayList;

public class Description {
	private ArrayList<String> picList;
	private String content;
	public Description(){
		picList=new ArrayList<String>();
	}
	public ArrayList<String> getPicList() {
		return picList;
	}
	public void setPicList(ArrayList<String> picList) {
		this.picList = picList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
