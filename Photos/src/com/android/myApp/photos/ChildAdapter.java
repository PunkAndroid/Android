package com.android.myApp.photos;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.myApp.photos.MyImageView.OnMeasureListener;
import com.android.myApp.photos.NativeImageLoader.NativeImageCallBack;

public class ChildAdapter extends BaseAdapter {
	private Point mPoint = new Point(0,0);//用来封装ImageView的宽和高的对象
	
	private GridView mGridView;
	private List<String> list;
	protected LayoutInflater mInflater;
	
	public ChildAdapter(Context context, List<String> list, GridView mGridView){
		this.list = list;
		this.mGridView = mGridView;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		String path = list.get(position);
		
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.grid_child_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mImageView = (MyImageView) convertView.findViewById(R.id.child_image);
			
			viewHolder.mImageView.setOnMeasureListener(new OnMeasureListener() {
				
				@Override
				public void onMeasureSize(int width, int height) {
					// TODO Auto-generated method stub
					mPoint.set(width, height);
				}
			});
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.mImageView.setImageResource(R.drawable.friends_sends_pictures_no);
		}
		viewHolder.mImageView.setTag(path);
		
		//利用NativeImageLoader类加载本地图片
		Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint,new NativeImageCallBack() {
			
			@Override
			public void onImageLoader(Bitmap bitmap, String path) {
				// TODO Auto-generated method stub
				ImageView mImageView = (ImageView) mGridView.findViewWithTag(path);
				if(bitmap != null && mImageView != null){
					mImageView.setImageBitmap(bitmap);
				}
			}
		});
		
		if(bitmap != null){
			viewHolder.mImageView.setImageBitmap(bitmap);
		}else{
			viewHolder.mImageView.setImageResource(R.drawable.friends_sends_pictures_no);
		}
		return convertView;
	}
	public static class ViewHolder{
		public MyImageView mImageView;;
	}
}
