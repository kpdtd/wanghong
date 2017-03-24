package com.wanghong.po;

public class ActorPayVideo {
	private Integer id;
	private Integer actorId;
	private Integer type;
	private String savePath;
	private String image;
	private Integer price;
	private String introduction;
	private Integer status;
	private String reason;
	private java.util.Date createTime;
	
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setActorId(Integer value) {
		this.actorId = value;
	}
	
	public Integer getActorId() {
		return this.actorId;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setSavePath(String value) {
		this.savePath = value;
	}
	
	public String getSavePath() {
		return this.savePath;
	}
	public void setPrice(Integer value) {
		this.price = value;
	}
	
	public Integer getPrice() {
		return this.price;
	}
	public void setIntroduction(String value) {
		this.introduction = value;
	}
	
	public String getIntroduction() {
		return this.introduction;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setReason(String value) {
		this.reason = value;
	}
	
	public String getReason() {
		return this.reason;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}

