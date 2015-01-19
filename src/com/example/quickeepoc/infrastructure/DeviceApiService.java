package com.example.quickeepoc.infrastructure;

import com.example.quickeepoc.model.DeviceCheckinResponse;
import com.example.quickeepoc.model.DeviceGalleryResponse;
import com.example.quickeepoc.model.DevicePlaylistResponse;

import retrofit.http.GET;
import retrofit.http.Path;

public interface DeviceApiService {
	@GET("/device/playlist/{deviceId}")
	public void getDevicePlaylist(@Path("deviceId") Integer deviceId, retrofit.Callback<DevicePlaylistResponse> devicePlaylistResponse);	

	@GET("/device/gallery/{deviceId}")
	public void getDeviceGallery(@Path("deviceId") Integer deviceId, retrofit.Callback<DeviceGalleryResponse> deviceGalleryResponse);		

	@GET("/device/checkin/{deviceId}")
	public void deviceCheckin(@Path("deviceId") Integer deviceId, retrofit.Callback<DeviceCheckinResponse> deviceCheckinResponse);		
}


