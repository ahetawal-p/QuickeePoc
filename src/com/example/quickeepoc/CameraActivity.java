package com.example.quickeepoc;

import java.io.File;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CameraActivity extends Activity {
	ImageView backgroundImage;
	ImageView overlayImage;
	ImageView mergedImageView;
	FrameLayout galleryFrameLayout;
	Button nextButton;
	public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777; 
	Bitmap overlayImageBitmap;
	Bitmap cameraImageBitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		initializeComponents();
	}
	
	private void initializeComponents(){
		overlayImage = (ImageView)findViewById(R.id.colormap_overlay);
		overlayImage.setImageResource(R.drawable.lebron);
		mergedImageView = (ImageView)findViewById(R.id.mergedImageView);
		nextButton = (Button)findViewById(R.id.nextButton);
		galleryFrameLayout = (FrameLayout)findViewById(R.id.gallerylayout);
		overlayImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lebron);
	}
	
	public void openCamera(View view){
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) 
	    {
	        //Get our saved file into a bitmap object:
	    	DisplayMetrics dm = new DisplayMetrics();
	    	getWindowManager().getDefaultDisplay().getMetrics(dm);
	       File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
	       cameraImageBitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), dm.widthPixels, dm.heightPixels);
	       
	       if (cameraImageBitmap.getWidth() > cameraImageBitmap.getHeight()) {
	           Matrix matrix = new Matrix();
	           matrix.postRotate(90);
	           cameraImageBitmap = Bitmap.createBitmap(cameraImageBitmap , 0, 0, cameraImageBitmap.getWidth(), cameraImageBitmap.getHeight(), matrix, true);
	       }
	       
	       backgroundImage = (ImageView)findViewById(R.id.visible_image);
	       backgroundImage.setImageBitmap(cameraImageBitmap);
	    }
	}
	
	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) 
	{ // BEST QUALITY MATCH
	     
	    //First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(path, options);
	 
	    // Calculate inSampleSize, Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    options.inPreferredConfig = Bitmap.Config.RGB_565;
	    int inSampleSize = 1;
	 
	    if (height > reqHeight) 
	    {
	        inSampleSize = Math.round((float)height / (float)reqHeight);
	    }
	    int expectedWidth = width / inSampleSize;
	 
	    if (expectedWidth > reqWidth) 
	    {
	        //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
	        inSampleSize = Math.round((float)width / (float)reqWidth);
	    }
	 
	    options.inSampleSize = inSampleSize;
	 
	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	 
	    return BitmapFactory.decodeFile(path, options);
	}
	
	public Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0,bmp1.getHeight() - bmp2.getHeight(), null);
        
        return bmOverlay;
    }
	
	public Bitmap resizeBitmap(Bitmap bitmap){
		Double resizeFactor = 0.76;
		int resizedHeight = (int)(bitmap.getHeight()* resizeFactor);
		int resizedWidth = (int)(bitmap.getWidth() * resizeFactor);
		Matrix m = new Matrix();
		m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, resizedWidth, resizedHeight), Matrix.ScaleToFit.CENTER);
		
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
	}
	
	public void showOutput(View view){
		galleryFrameLayout.setVisibility(View.GONE);
		Bitmap resizedCameraBitmap = resizeBitmap(cameraImageBitmap);
		Bitmap mergedBitmap = overlay(resizedCameraBitmap, overlayImageBitmap);
		mergedImageView.setVisibility(View.VISIBLE);
		mergedImageView.setImageBitmap(mergedBitmap);
	}
}
