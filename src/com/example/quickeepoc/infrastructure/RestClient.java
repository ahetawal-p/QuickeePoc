package com.example.quickeepoc.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient
{
    private static final String BASE_URL = "http://api.quickeecam.com/v1";
    private DeviceApiService deviceApiService;

    public RestClient()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();
       

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        deviceApiService = restAdapter.create(DeviceApiService.class);
    }

    public DeviceApiService getDeviceApiService()
    {
        return deviceApiService;
    }
}
