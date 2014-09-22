package com.android.myApp.photos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class PhotoDetailsActivity extends Activity {
	private ZoomImageView zoomImageView;
	private int viewHeight = 0;
	private int viewWidth  = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_details);
		zoomImageView = (ZoomImageView)findViewById(R.id.photo_details_view);
		
		
		String imagePath = getIntent().getStringExtra("image_path");
		Options opts = new Options();
		opts.inSampleSize = 2;
		/*
	 	ViewTreeObserver vto2 = zoomImageView.getViewTreeObserver();

		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				zoomImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				viewHeight = zoomImageView.getHeight();
				viewWidth = zoomImageView.getWidth();
			}
		});
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath);
		int imageHeight = opts.outHeight;
		int imageWidth = opts.outWidth;
		
		opts.inSampleSize = Math.max(imageHeight/viewHeight,imageWidth/viewWidth);
		
		opts.inJustDecodeBounds = false;
		*/
		
		/*
		opts.inDither = false;
		opts.inPurgeable = true;
		opts.inTempStorage = new byte[36*1024];
		File file = new File(imagePath);
		FileInputStream fs=null;
		try {
			fs = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bitmap bitmap = null;
		if(fs!=null)
			try {
				bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, opts);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(fs!=null){
					try{
						fs.close();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		*/
		
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, opts);
		zoomImageView.setImageBitmap(bitmap);
	}
}
