package com.example.quickeepoc.model;

import org.parceler.Parcel;

@Parcel
public class DeviceGalleryPicture {
	private String accessCode;
	private String type;
	private String filePath;
	private String UploadTime;
	
	public String getAccessCode() {return accessCode;}
	public void setAccessCode(String accessCode){ this.accessCode = accessCode;}
	
	public String getType() {return type;}
	public void setType(String type){ this.type = type;}
	
	public String getFilePath() {return filePath;}
	public void setFilePath(String filePath){ this.filePath = filePath;}
	public String getUploadTime() {return UploadTime;}
	public void setUploadTime(String UploadTime){ this.UploadTime = UploadTime;}
}
