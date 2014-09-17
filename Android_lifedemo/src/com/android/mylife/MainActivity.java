package com.android.mylife;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	private final String TAG="Main";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate Method is executed");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStart Method is executed");
		super.onStart();
	
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onRestart Method is executed");
		super.onRestart();
	
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onResume Method is executed");
		super.onResume();
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStop Method is executed");
		super.onStop();
	
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPause Method is executed");
		super.onPause();

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onDestroy Method is executed");
		super.onDestroy();
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
