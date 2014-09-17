package com.android.myimagescan;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.BaseAdapter;
import android.widget.ImageView;  
import android.widget.TextView; 






import com.android.myimagescan.MyImageView.OnMeasureListener;
import com.android.myimagescan.bean.ImageBean;
import com.android.myimagescan.loader.NativeImageLoader;
import com.android.myimagescan.loader.NativeImageLoader.NativeImageCallBack;
import com.example.myimagescan.R;

public class GroupAdapter extends BaseAdapter {
	private List<ImageBean> list;
	private Point mPoint = new Point(0,0);//������װImageView�Ŀ��͸ߵĶ���
	private GridView mGridView;
	protected LayoutInflater mInflater;
	
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
	
	public GroupAdapter(Context context,List<ImageBean> list,GridView mGridView){
		this.list = list;
		this.mGridView = mGridView;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		ImageBean mImageBean = list.get(position);
		String path = mImageBean.getTopImagePath();//�ļ����е�һ��ͼƬ��·������Ϊ����ͼ
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.grid_group_item, null);
			viewHolder.mImageView = (MyImageView) convertView.findViewById(R.id.group_image);
			viewHolder.mTextViewTitle = (TextView) convertView.findViewById(R.id.group_title);
			viewHolder.mTextViewCounts = (TextView) convertView.findViewById(R.id.group_count);
			
			//��������ImageView�Ŀ��͸�
			viewHolder.mImageView.setOnMeasureListener(new OnMeasureListener(){
				public void onMeasureSize(int width,int height){
					mPoint.set(width,height);
				}
			});
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.mImageView.setImageResource(R.drawable.friends_sends_pictures_no);
		}
		
		viewHolder.mTextViewTitle.setText(mImageBean.getFolderName());
		viewHolder.mTextViewCounts.setText(Integer.toString(mImageBean.getImageCounts()));
		//��ImageView����·��Tag�������첽����ͼƬ��С���ɡ�
		viewHolder.mImageView.setTag(path);
		
		//����NativeImageLoader����ر���ͼƬ
		Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint,new NativeImageCallBack(){

			@Override
			public void onImageLoader(Bitmap bitmap, String path) {
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
		public MyImageView mImageView;
		public TextView mTextViewTitle;
		public TextView mTextViewCounts;
	}
	
}