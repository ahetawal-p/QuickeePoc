package com.example.quickeepoc;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class SubmitActivity extends Activity {
	ImageView mergedImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
		initializeComponents();
	}
	
	private void initializeComponents(){
		
	}
}
