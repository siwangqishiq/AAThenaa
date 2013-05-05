package com.airad.athena.data.model;

/**
 * 商品信息
 * @author Panyi 
 *
 */
public class Product {
	private Integer id;
	private String name;
	private String price;
	private String picUrl;
	private String brand;
	private String use;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}
}
