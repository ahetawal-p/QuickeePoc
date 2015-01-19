package com.example.quickeepoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.example.quickeepoc.recyclerview.PhotoStreamAdapter;
import com.example.quickeepoc.util.BitmapHelper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class HomeActivity extends Activity implements PictureCallback{

	private Camera camera;
    private CameraPreview cameraPreview;
    private WebView webView;
    private RecyclerView photoStream;
    private CountDownTimer countDownTimer;
    private Button takePhotoBtn;
    
    Bitmap overlayImageBitmap;
	Bitmap cameraImageBitmap;
	
	
	private FrameLayout cameraHolder;
    private ImageView mergedImageView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        takePhotoBtn = (Button)findViewById(R.id.takephotobtn);
        overlayImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lebron);
    	
    	cameraHolder = (FrameLayout)findViewById(R.id.cameraHolder);
        mergedImageView = (ImageView)findViewById(R.id.mergedImageView);
    	
        
        loadPhotoStream();
        loadWebview();
        initCountDownTimer();
        
        // Camera may be in use by another activity or the system or not available at all
       // camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        camera = Camera.open();
        if(camera != null){
            initCameraPreview();
        } else {
            Log.e("Quickee", "No Camera present");
        }
    }
    
    
    
    
    private void initCountDownTimer() {
    	countDownTimer = new CountDownTimer(3000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				takePhotoBtn.setText(""+millisUntilFinished/1000);
				YoYo.with(Techniques.BounceIn).duration(300).playOn(takePhotoBtn);
			}
			@Override
			public void onFinish() {
				//takePhotoBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
				//YoYo.with(Techniques.Bounce).duration(800).playOn(cameraPreview);
				takePicture();
			}
		};
	}

    
    private void flashAnimation() {
    	View mFlashEffectView = findViewById(R.id.flash_effect_view);
        ValueAnimator fadeIn = ObjectAnimator.ofFloat(mFlashEffectView, "alpha", 0f, 0.5f);
        fadeIn.setDuration(50);
        ValueAnimator fadeOut = ObjectAnimator.ofFloat(mFlashEffectView, "alpha", 0.5f, 0f);
        fadeOut.setDuration(50);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(fadeIn, fadeOut);
        animatorSet.start();
    }
    
    
    private void takePicture(){
    	takePhotoBtn.setVisibility(View.GONE);
    	flashAnimation();
    	camera.takePicture(null, null, this);
    }
    
    
    
    @Override
    public void onPictureTaken(byte[] data, Camera cam) {
        Log.d("Quickee", "Picture taken");
        countDownTimer.cancel();
        camera.stopPreview();
        
        cameraHolder.setVisibility(View.GONE);
       // mergedResult.setVisibility(View.VISIBLE);
        String path = savePictureToFileSystem(data);
        
        performMergeOfOverlayWithCurrentImage(path);
        
        
        Bitmap resizedCameraBitmap = BitmapHelper.resizeBitmap(cameraImageBitmap);
        
        //mergedImageView.setImageBitmap(resizedCameraBitmap);
		Bitmap mergedBitmap = overlay(resizedCameraBitmap, overlayImageBitmap);
		
		mergedImageView.setVisibility(View.VISIBLE);
		mergedImageView.setImageBitmap(mergedBitmap);
		
        

       

    }
    
    
     
    
    public Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0,bmp1.getHeight() - bmp2.getHeight(), null);
        
        return bmOverlay;
    }
 
    
    
	private void performMergeOfOverlayWithCurrentImage(String path) {
		DisplayMetrics dm = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(dm);
       File file = new File(path);
       cameraImageBitmap = BitmapHelper.decodeSampledBitmapFromFile(file.getAbsolutePath(), dm.widthPixels, dm.heightPixels);
       
       if (cameraImageBitmap.getWidth() > cameraImageBitmap.getHeight()) {
           Matrix matrix = new Matrix();
           matrix.postRotate(90);
           cameraImageBitmap = Bitmap.createBitmap(cameraImageBitmap , 0, 0, cameraImageBitmap.getWidth(), cameraImageBitmap.getHeight(), matrix, true);
       }
		
	}

	
	public void backToCamera(View button){
		mergedImageView.setVisibility(View.GONE);
		 cameraHolder.setVisibility(View.VISIBLE);
      takePhotoBtn.setBackgroundResource(android.R.drawable.btn_default);
      takePhotoBtn.setVisibility(View.VISIBLE);
      takePhotoBtn.setText("Photo");
     // camera = Camera.open();
      //initCameraPreview();
     camera.startPreview();
        
    }
	



	private String savePictureToFileSystem(byte[] data) {
		File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "QuickeePoc");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("QuickeePoc", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
	    File mediaFile = new File(mediaStorageDir.getPath() + File.separator +"IMG_"+".jpg");
	    
	    saveToFile(data, mediaFile);
	    
	    return mediaFile.getAbsolutePath();
	}

	
	public static boolean saveToFile(byte[] bytes, File file){
		boolean saved = false;
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.close();
			saved = true;
		} catch (FileNotFoundException e) {
			Log.e("QuickeePoc", "FileNotFoundException", e);
		} catch (IOException e) {
			Log.e("QuickeePoc", "IOException", e);
		}
		return saved;
	}


	private void loadPhotoStream() {
    	photoStream = (RecyclerView) findViewById(R.id.photostream);

        // If the size of views will not change as the data changes.
    	photoStream.setHasFixedSize(true);

        // Setting the LayoutManager.
    	LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    	mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    	photoStream.setLayoutManager(mLayoutManager);

        // Setting the adapter.
    	PhotoStreamAdapter mAdapter = new PhotoStreamAdapter(this);
        photoStream.setAdapter(mAdapter);
		
	}




	@SuppressLint("SetJavaScriptEnabled")
	private void loadWebview() {
    	webView = (WebView)findViewById(R.id.campaignView);
    	webView.getSettings().setBuiltInZoomControls(true);
    	webView.getSettings().setDisplayZoomControls(false);
		//emailView.setInitialScale(1);
    	//webView.getSettings().setLoadWithOverviewMode(true);
    	//webView.getSettings().setUseWideViewPort(true);
		//emailView.loadData(emailContent, "text/html", "UTF-8");
		//webView.loadUrl("http://commonsware.com");
    	webView.setWebViewClient(new WebViewController());
    	webView.getSettings().setJavaScriptEnabled(true);
    	//webView.loadUrl("http://m.youtube.com/watch?v=Soa3gO7tL-c");
    	webView.loadUrl("http://www.google.com");
		
	}
	
	public class WebViewController extends WebViewClient {

	     @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            view.loadUrl(url);
	            return true;
	        }
	}




	// Show the camera view on the activity
    private void initCameraPreview() {
        cameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
        cameraPreview.init(camera);
    }
 
    
    @SuppressLint("NewApi")
	public void onCaptureClick(View button){
        // Take a picture with a callback when the photo has been created
        // Here you can add callbacks if you want to give feedback when the picture is being taken
        //camera.takePicture(null, null, this);
    	takePhotoBtn.setBackground(getResources().getDrawable(R.drawable.roundshape));
    	takePhotoBtn.setText("3");
    	
    	countDownTimer.start();
    }
 
   
    
 // ALWAYS remember to release the camera when you are finished
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }
 
    private void releaseCamera() {
        if(camera != null){
            camera.release();
            camera = null;
        }
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
