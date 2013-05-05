package com.airad.athena.data.model;

/**
 * 工作人员信息
 * 
 * @author Panyi
 * 
 */
public class Worker {
	private Integer id;
	private String name;
	private String title;
	private String des;
	private String picUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getPic() {
		return picUrl;
	}

	public void setPic(String picUrl) {
		this.picUrl = picUrl;
	}
}
