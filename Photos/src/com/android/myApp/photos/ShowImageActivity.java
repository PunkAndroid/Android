package com.android.myApp.photos;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class ShowImageActivity extends Activity {
	private GridView mGridView;
	private List<String> list;
	private ChildAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_image_activity);
		
		mGridView = (GridView) findViewById(R.id.child_grid);
		list = getIntent().getStringArrayListExtra("data");
		
		adapter = new ChildAdapter(this, list, mGridView);
		mGridView.setAdapter(adapter);
	}
	
	
}
