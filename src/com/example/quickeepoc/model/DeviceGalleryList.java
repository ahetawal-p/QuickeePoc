package com.example.quickeepoc.model;

import org.parceler.Parcel;

@Parcel
public class DeviceGalleryList {
	private DeviceGalleryPicture[] deviceGalleryPictures;
	public DeviceGalleryPicture[] getDeviceGalleryPicture(){ return deviceGalleryPictures;}
}
