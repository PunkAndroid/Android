package com.android.myimagescan;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView{
	private OnMeasureListener onMeasureListener;  
    
    public void setOnMeasureListener(OnMeasureListener onMeasurelistener) {  
        this.onMeasureListener = onMeasurelistener;  
    }  
	public MyImageView(Context context,AttributeSet attrs) {
		super(context,attrs);
	}
	
	public MyImageView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		//将图片测量的大小回调到onMeasureSize()方法中
		if(onMeasureListener != null){
			onMeasureListener.onMeasureSize(getMeasuredWidth(), getMeasuredHeight());
		}
	}

	public interface OnMeasureListener {
		// TODO Auto-generated method stub
		public void onMeasureSize(int width, int height);
	}

}
