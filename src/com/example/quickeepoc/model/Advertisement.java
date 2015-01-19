package com.example.quickeepoc.model;

import org.parceler.Parcel;

@Parcel
public class Advertisement {
	private Integer id;
	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	
	private String Name;
	public String getName() {return Name;}
	public void setName(String name){ this.Name = name;}
	
	private String HdVersion;
	public String getHdVersion() {return HdVersion;}
	public void setHdVersion(String hdVersion){ this.HdVersion = hdVersion;}
	
	private String SmallVersion;
	public String getSmallVersion() {return SmallVersion;}
	public void setSmallVersion(String smallVersion){ this.SmallVersion = smallVersion;}
	
	private String BannerVersion;
	public String getBannerVersion() {return BannerVersion;}
	public void setBannerVersion(String bannerVersion){ this.BannerVersion = bannerVersion;}
	
	private String PreImage;
	public String getPreImage() {return PreImage;}
	public void setPreImage(String preImage){ this.PreImage = preImage;}
	
	private String PostImage;
	public String getPostImage() {return PostImage;}
	public void setPostImage(String postImage){ this.PostImage = postImage;}
	
	private Integer DisplayTime;
	public Integer getDisplayTime() {return DisplayTime;}
	public void setDisplayTime(Integer displayTime){ this.DisplayTime = displayTime;}
	
	private Integer Order;
	public Integer getOrder() {return Order;}
	public void setOrder(Integer order){ this.Order = order;}
	
}
