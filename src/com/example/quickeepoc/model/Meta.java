package com.example.quickeepoc.model;

import org.parceler.Parcel;

@Parcel
public class Meta {
	private String stat;
	private Integer code;
	private String method;
	
	public String getStat() {return stat;}
	public Integer getCode() {return code;}
	public String getMethod() {return method;}
}
