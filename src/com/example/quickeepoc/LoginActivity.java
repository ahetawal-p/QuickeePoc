package com.example.quickeepoc;

import retrofit.RetrofitError;
import retrofit.Callback;
import retrofit.client.Response;

import com.example.quickeepoc.infrastructure.DeviceApiService;
import com.example.quickeepoc.infrastructure.RestClient;
import com.example.quickeepoc.model.DeviceCheckinResponse;
import com.example.quickeepoc.model.DeviceGalleryResponse;
import com.example.quickeepoc.model.DevicePlaylistResponse;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {
EditText userNameEditText;
EditText passwordEditText;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initializeUIComponents();
    }
    
    private void initializeUIComponents(){
    	userNameEditText = (EditText)findViewById(R.id.userNameEditText);
    	passwordEditText = (EditText)findViewById(R.id.passwordEditText);
    }
   
    DevicePlaylistResponse devicePlaylistResponse;
    DeviceGalleryResponse deviceGalleryResponse;
    DeviceCheckinResponse deviceCheckinResponse;
    
    public void loginUser(View view){
    	RestClient restClient = new RestClient();
    	DeviceApiService deviceApiService = restClient.getDeviceApiService();
    	Callback devicePlaylistCallback = new Callback<DevicePlaylistResponse>() {
    	    @Override
    	    public void success(DevicePlaylistResponse o, Response response) {
    	    	devicePlaylistResponse = o;
    	    	navigateToHomeActivity();
    	    }

    	    @Override
    	    public void failure(RetrofitError retrofitError) {
    	    	RetrofitError error = retrofitError;
    	    }
    	};
    	
    	Callback deviceGalleryCallback = new Callback<DeviceGalleryResponse>() {
    	    @Override
    	    public void success(DeviceGalleryResponse o, Response response) {
    	    	deviceGalleryResponse = o;
    	    }

    	    @Override
    	    public void failure(RetrofitError retrofitError) {
    	    	RetrofitError error = retrofitError;
    	    }
    	};
    	
    	Callback deviceCheckinCallback = new Callback<DeviceCheckinResponse>() {
    	    @Override
    	    public void success(DeviceCheckinResponse o, Response response) {
    	    	deviceCheckinResponse = o;
    	    }

    	    @Override
    	    public void failure(RetrofitError retrofitError) {
    	    	RetrofitError error = retrofitError;
    	    }
    	};
    	
    	try{
    		deviceApiService.deviceCheckin(4, deviceCheckinCallback);  		
    		deviceApiService.getDevicePlaylist(4, devicePlaylistCallback);
    		deviceApiService.getDeviceGallery(4, deviceGalleryCallback);
    	}
    	catch (Exception ex){
    		String message = ex.getMessage();
    	}

    }
    
    private void navigateToHomeActivity(){
		Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
		startActivity(intent);		
    }
    
}
