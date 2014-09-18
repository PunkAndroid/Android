package com.android.myApp.photos;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	private ViewPager mTabPager;
	private Button button1, button2;
	
	private List<String> allPhotosList = new ArrayList<String>();
	private HashMap<String,List<String>> mGroupMap = new HashMap<String, List<String>>();
	private List<ImageBean> list = new ArrayList<ImageBean>();
	private final static int SCAN_OK = 1;
	private ProgressDialog mProgressDialog;
	private GroupAdapter adapter1;
	private ChildAdapter adapter2;
	private GridView photoAlbum_GridView;
	private GridView allPhotos_GridView;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch(msg.what){
			case SCAN_OK:
				mProgressDialog.dismiss();
				
				adapter1 = new GroupAdapter(MainActivity.this,
						list = subGroupOfImage(mGroupMap),photoAlbum_GridView);
				photoAlbum_GridView.setAdapter(adapter1);
				
				adapter2 = new ChildAdapter(MainActivity.this, allPhotosList, allPhotos_GridView);
				allPhotos_GridView.setAdapter(adapter2);
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		button1 = (Button) findViewById(R.id.photo_album);
		button2 = (Button) findViewById(R.id.photos);
		button1.setOnClickListener(new MyOnClickListener(0));
		button2.setOnClickListener(new MyOnClickListener(1));

		LayoutInflater mLi = LayoutInflater.from(this);	
		View view1 = mLi.inflate(R.layout.photo_album, null);
		View view2 = mLi.inflate(R.layout.all_photos, null);
		
		photoAlbum_GridView = (GridView)view1.findViewById(R.id.main_grid);
		allPhotos_GridView = (GridView)view2.findViewById(R.id.all_photos_grid);
		getImages();
		photoAlbum_GridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				List<String> childList = mGroupMap.get(list.get(position).getFolderName());
				
				Intent mIntent = new Intent(MainActivity.this, ShowImageActivity.class);
				mIntent.putStringArrayListExtra("data", (ArrayList<String>)childList);
				startActivity(mIntent);
				
			}
		});
		
//		allPhotos_GridView.setOnItemClickListener(listener);
		
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);

		

		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				// TODO Auto-generated method stub
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				// TODO Auto-generated method stub
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		mTabPager.setAdapter(mPagerAdapter);
		
	}
	private void getImages() {
		// TODO Auto-generated method stub
		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载。。。");

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = MainActivity.this
						.getContentResolver();

				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);
				if (mCursor == null) {
					return;
				}
				while (mCursor.moveToNext()) {
					// 获取图片的路径
					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					// 获取该图片的父路径名

					String parentName = new File(path).getParentFile()
							.getName();

					// 根据父路径名将图片放入mGroupMap中
					allPhotosList.add(path);
					if (!mGroupMap.containsKey(parentName)) {
						List<String> childList = new ArrayList<String>();
						childList.add(path);
						mGroupMap.put(parentName, childList);
					} else {
						mGroupMap.get(parentName).add(path);
					}
				}
				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(SCAN_OK);
				mCursor.close();
			}

		}).start();
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			mTabPager.setCurrentItem(index);

		}
	}
	
	private List<ImageBean> subGroupOfImage(
			HashMap<String, List<String>> mGroupMap) {
		// TODO Auto-generated method stub
		if(mGroupMap.size() == 0){
			return null;
		}
		List<ImageBean> list = new ArrayList<ImageBean>();
		
		Iterator<Map.Entry<String, List<String>>> it = mGroupMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, List<String>> entry = it.next();
			ImageBean mImageBean = new ImageBean();
			String key = entry.getKey();
			List<String> value = entry.getValue();
			
			mImageBean.setFolderName(key);
			mImageBean.setImageCounts(value.size());
			mImageBean.setTopImagePath(value.get(0));
			
			list.add(mImageBean);
		}
		return list;
	}
}
