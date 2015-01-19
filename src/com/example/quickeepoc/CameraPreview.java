package com.example.quickeepoc;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressWarnings("deprecation")
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	 
    private Camera camera;
    private SurfaceHolder holder;
    private boolean isViewAvailable;
 
    public CameraPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public CameraPreview(Context context) {
        super(context);
    }
 
    public void init(Camera camera) {
        this.camera = camera;
        initSurfaceHolder();
    }
 
    
    private void initSurfaceHolder() {
        holder = getHolder();
        holder.addCallback(this);
        //holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
 
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initCamera(holder);
        isViewAvailable = true;
    }
 
    private void initCamera(SurfaceHolder holder) {
        try {
        	//camera.setDisplayOrientation(90);
        	setCameraDisplayOrientation(getContext(),Camera.CameraInfo.CAMERA_FACING_BACK,camera );
        	setCameraSizes();
            camera.setPreviewDisplay(holder);
           
//            camera.setOneShotPreviewCallback(new Camera.PreviewCallback() {
//                @Override
//                public void onPreviewFrame(byte[] data, Camera camera) {
//                	 Log.i("CameraPreview", "In Preview Callback...");
////                    autoFocus(new Camera.AutoFocusCallback() {
////                        @Override
////                        public void onAutoFocus(boolean b, Camera camera) {
////                            Log.i("CameraPreview", "Auto focus set :)");
////                        }
////                    });
//                }
//            });
            camera.startPreview();
            
        } catch (Exception e) {
            Log.e("CamerPreview", "Error setting camera preview", e);
        }
    }
    
    
    private void setCameraSizes() {
    	Camera.Parameters cameraParameters = camera.getParameters();
        List<Camera.Size> listSupportedPictureSizes = cameraParameters.getSupportedPictureSizes();
        for(Camera.Size size : listSupportedPictureSizes){
        	System.out.println("Pic Size " + size.width + " X " + size.height);
        	
        }
        
        
        List<Camera.Size> listSupportedPPreviewSizes = cameraParameters.getSupportedPreviewSizes();
        for(Camera.Size size : listSupportedPPreviewSizes){
        	System.out.println("Preview Size " + size.width + " X " + size.height);
        }
        
        //cameraParameters.setPreviewSize(640, 480);
		
	}

	public void setCameraDisplayOrientation(Context activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = ((Activity)activity).getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
    
    private boolean canRequestAutoFocus(Context context){
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        boolean hasAutoFocus = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS);
        return hasAutoFocus && isViewAvailable;
    }

    private void autoFocus(Camera.AutoFocusCallback autoFocusCallback){
        if(canRequestAutoFocus(getContext())){
        	camera.cancelAutoFocus();
            try {
            	camera.autoFocus(autoFocusCallback);
            } catch (RuntimeException e) {
            	 Log.e("CamerPreview", "Error setting camera autofocus", e);
            }
        }
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
 
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    	 isViewAvailable = false;
    }
}