package com.android.myApp.photos;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ShowPhotoInAlbumActivity extends Activity {
	private GridView mGridView;
	private List<String> list;
	private ChildAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photos_in_album);
		
		mGridView = (GridView) findViewById(R.id.child_grid);
		list = getIntent().getStringArrayListExtra("data");
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String photoPath = list.get(position); 
				Intent mIntent = new Intent(ShowPhotoInAlbumActivity.this,PhotoDetailsActivity.class);
				mIntent.putExtra("image_path", photoPath);
				startActivity(mIntent);
			}
			
		});
		adapter = new ChildAdapter(this, list, mGridView);
		mGridView.setAdapter(adapter);
	}
	
	
}
