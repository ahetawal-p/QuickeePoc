package com.example.quickeepoc.model;

import org.parceler.Parcel;

@Parcel
public class DeviceGalleryListResponse {
	private DeviceGalleryPicture[] list;
	public DeviceGalleryPicture[] getList(){ return list;}
}
