package com.android.test.imageviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.view.MotionEvent;
import android.widget.ImageView;

public class TouchImageView extends ImageView {
	
	private PointF down = new PointF();
	private PointF mid = new PointF();
    private float oldDist = 1f;
    private Matrix matrix = new Matrix();
    private Matrix preMatrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    
    private boolean isBig = false;
    
    private int widthScreen;
    private int heightScreen;
    
    private int touchImgWidth;
    private int touchImgHeight;
    
    private float defaultScale;
    
    private long lastClickTime = 0;
    
    private Bitmap touchImg = null;
    
    private static final int DOUBLE_CLICK_TIME_SPACE = 300;
    private static final int DOUBLE_POINT_DISTANCE = 100;
    private static float MAX_SCALE = 3.0f;
    
	public TouchImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public TouchImageView(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context,attrs);
	}
	public TouchImageView(Context context, AttributeSet attrs,int defStyleAttr) {
		// TODO Auto-generated constructor stub
		super(context, attrs, defStyleAttr);
	}
	
	public void initImageView(int screenWidth, int screenHeight){
		widthScreen = screenWidth;
		heightScreen = screenHeight;
		
		touchImg = ((BitmapDrawable) getDrawable()).getBitmap();
		touchImgWidth = touchImg.getWidth();
		touchImgHeight = touchImg.getHeight();
		float scaleX = (float) widthScreen / touchImgWidth;
		float scaleY = (float) heightScreen / touchImgHeight;
		defaultScale = scaleX < scaleY ? scaleX:scaleY;
		
		float subX = (widthScreen - touchImgWidth * defaultScale) /2;
		float subY = (heightScreen - touchImgHeight * defaultScale) /2;
		setScaleType(ScaleType.MATRIX);
		preMatrix.reset();
		preMatrix.postScale(defaultScale, defaultScale);
		preMatrix.postTranslate(subX, subY);
		matrix.set(preMatrix);
		invalidate();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(null != touchImg){
			canvas.save();
			canvas.drawBitmap(touchImg, matrix, null);
			canvas.restore();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = DRAG;
			down.x = event.getX();
			down.y = event.getY();
			savedMatrix.set(matrix);
			
			if(event.getEventTime() - lastClickTime < DOUBLE_CLICK_TIME_SPACE){
				changeSize(event.getX(),event.getY());
			}
			lastClickTime = event.getEventTime();
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			if(oldDist > DOUBLE_POINT_DISTANCE){
				
			}
		default:
			break;
		}
		return super.onTouchEvent(event);
	}
	private float spacing(MotionEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}
	private void changeSize(float x, float y) {
		// TODO Auto-generated method stub
		
	}
	
	
}
