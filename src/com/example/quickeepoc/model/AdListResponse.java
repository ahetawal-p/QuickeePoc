package com.example.quickeepoc.model;

import org.parceler.Parcel;

@Parcel
public class AdListResponse {
	private Advertisement[] AdList;
	public Advertisement[] getAdvertisements() { return AdList;}
	
	private AdListInfo AdListInfo;
}
