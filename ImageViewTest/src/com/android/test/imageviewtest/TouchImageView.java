package com.android.test.imageviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.FloatMath;
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
		super(context, attrs);
	}

	public TouchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		// TODO Auto-generated constructor stub
		super(context, attrs, defStyleAttr);
	}

	public void initImageView(int screenWidth, int screenHeight) {
		widthScreen = screenWidth;
		heightScreen = screenHeight;

		touchImg = ((BitmapDrawable) getDrawable()).getBitmap();
		touchImgWidth = touchImg.getWidth();
		touchImgHeight = touchImg.getHeight();
		float scaleX = (float) widthScreen / touchImgWidth;
		float scaleY = (float) heightScreen / touchImgHeight;
		defaultScale = scaleX < scaleY ? scaleX : scaleY;

		float subX = (widthScreen - touchImgWidth * defaultScale) / 2;
		float subY = (heightScreen - touchImgHeight * defaultScale) / 2;
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
		if (null != touchImg) {
			canvas.save();
			canvas.drawBitmap(touchImg, matrix, null);
			canvas.restore();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// 使用switch (event.getAction())可以处理ACTION_DOWN和ACTION_UP事件；
		// 使用switch (event.getAction() &
		// MotionEvent.ACTION_MASK)就可以处理处理多点触摸的ACTION_POINTER_DOWN和ACTION_POINTER_UP事件。
		// ACTION_DOWN和ACTION_UP就是单点触摸屏幕，按下去和放开的操作；
		// ACTION_POINTER_DOWN和ACTION_POINTER_UP就是多点触摸屏幕，当有一只手指按下去的时候，另一只手指按下和放开的动作捕捉；
		// ACTION_MOVE就是手指在屏幕上移动的操作；
		case MotionEvent.ACTION_DOWN:// 屏幕按下
			mode = DRAG;
			down.x = event.getX();// 触碰点X坐标
			down.y = event.getY();// 触碰点Y坐标
			savedMatrix.set(matrix);

			if (event.getEventTime() - lastClickTime < DOUBLE_CLICK_TIME_SPACE) {
				changeSize(event.getX(), event.getY());
			}
			lastClickTime = event.getEventTime();
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			if (oldDist > DOUBLE_POINT_DISTANCE) {
				mode = ZOOM;

				savedMatrix.set(matrix);
				midPoint(mid, event);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == ZOOM) {
				float newDist = spacing(event);
				float scale = newDist / oldDist;
				if (scale > 1.01 || scale < 0.99) {
					preMatrix.set(savedMatrix);
					preMatrix.postScale(scale, scale, mid.x, mid.y);// 缩放
					if (canZoom()) {
						matrix.set(preMatrix);
						invalidate();
					}
				}
			} else if (mode == DRAG) {
				if (1.0f < distance(event, down)) {
					preMatrix.set(savedMatrix);

					preMatrix.postTranslate(event.getX() - down.x, 0);
//					if (event.getX() > down.x) {
//						if (canDrag(DRAG_RIGHT)) {
//							savedMatrix.set(preMatrix);
//						} else {
//							preMatrix.set(savedMatrix);
//						}
//					} else {
//						if (canDrag(DRAG_LEFT)) {
//							savedMatrix.set(preMatrix);
//						} else {
//							preMatrix.set(savedMatrix);
//						}
//					}
					preMatrix.postTranslate(0, event.getY() - down.y);
//					if (event.getY() > down.y) {
//						if (canDrag(DRAG_DOWN)) {
//							savedMatrix.set(preMatrix);
//						} else {
//							preMatrix.set(savedMatrix);
//						}
//					} else {
//						if (canDrag(DRAG_TOP)) {
//							savedMatrix.set(preMatrix);
//						} else {
//							preMatrix.set(savedMatrix);
//						}
//					}
//
					matrix.set(preMatrix);
					invalidate();
//					down.x = event.getX();
//					down.x = event.getY();
//					savedMatrix.set(matrix);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			mode = NONE;
			springback();
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			break;

		}
		return true;
	}

	private void springback() {
		// TODO Auto-generated method stub
		preMatrix.set(matrix);
		float[] x = new float[4];
		float[] y = new float[4];
		getFourPoint(x, y);// 图片四个顶点的坐标
		if (x[1] - x[0] > widthScreen) {
			if (x[0] > 0) {
				preMatrix.postTranslate(-x[0], 0);
				matrix.set(preMatrix);
				invalidate();
			} else if (x[1] < widthScreen) {
				preMatrix.postTranslate(widthScreen - x[1], 0);
				matrix.set(preMatrix);
				invalidate();
			}
		} else if (x[1] - x[0] < widthScreen - 1f) {
			preMatrix.postTranslate(widthScreen - (x[1] - x[0]) / 2 - x[0], 0);
			matrix.set(preMatrix);
			invalidate();
		}

		if (y[2] - y[0] > heightScreen) {
			if (y[0] > 0) {
				preMatrix.postTranslate(0, -y[0]);
				matrix.set(preMatrix);
				invalidate();
			} else if (y[2] < heightScreen) {
				preMatrix.postTranslate(0, heightScreen - y[2]);
				matrix.set(preMatrix);
				invalidate();
			}
		} else if (y[2] - y[0] < heightScreen - 1f) {
			preMatrix.postTranslate(0, (heightScreen - (y[2] - y[0])) / 2
					- y[0]);
			matrix.set(preMatrix);
			invalidate();
		}
	}

	private void getFourPoint(float[] x, float[] y) {
		// 图片四个顶点的坐标
		float[] f = new float[9];
		preMatrix.getValues(f);
		x[0] = f[Matrix.MSCALE_X] * 0 + f[Matrix.MSKEW_X] * 0
				+ f[Matrix.MTRANS_X];
		y[0] = f[Matrix.MSKEW_Y] * 0 + f[Matrix.MSCALE_Y] * 0
				+ f[Matrix.MTRANS_Y];
		x[1] = f[Matrix.MSCALE_X] * touchImg.getWidth() + f[Matrix.MSKEW_X] * 0
				+ f[Matrix.MTRANS_X];
		y[1] = f[Matrix.MSKEW_Y] * touchImg.getWidth() + f[Matrix.MSCALE_Y] * 0
				+ f[Matrix.MTRANS_Y];
		x[2] = f[Matrix.MSCALE_X] * 0 + f[Matrix.MSKEW_X]
				* touchImg.getHeight() + f[Matrix.MTRANS_X];
		y[2] = f[Matrix.MSKEW_Y] * 0 + f[Matrix.MSCALE_Y]
				* touchImg.getHeight() + f[Matrix.MTRANS_Y];
		x[3] = f[Matrix.MSCALE_X] * touchImg.getWidth() + f[Matrix.MSKEW_X]
				* touchImg.getHeight() + f[Matrix.MTRANS_X];
		y[3] = f[Matrix.MSKEW_Y] * touchImg.getWidth() + f[Matrix.MSCALE_Y]
				* touchImg.getHeight() + f[Matrix.MTRANS_Y];
	}

	private final static int DRAG_LEFT = 0;
	private final static int DRAG_RIGHT = 1;
	private final static int DRAG_TOP = 2;
	private final static int DRAG_DOWN = 3;

	private boolean canDrag(final int direction) {
		// TODO Auto-generated method stub
		float[] x = new float[4];
		float[] y = new float[4];
		getFourPoint(x, y);

		// 出界判断
		if ((x[0] > 0 || x[2] > 0 || x[1] < widthScreen || x[3] < widthScreen)
				&& (y[0] > 0 || y[1] > 0 || y[2] < heightScreen || y[3] < heightScreen)) {
			return false;
		}
		if (DRAG_LEFT == direction) {// 动作为左移
			// 左移出界判断
			if (x[1] < widthScreen || x[3] < widthScreen) {
				return false;
			}
		} else if (DRAG_RIGHT == direction) {// 动作为右移
			// 右移出界判断
			if (x[0] > 0 || x[2] > 0) {
				return false;
			}
		} else if (DRAG_TOP == direction) {// 动作为上移
			// 上移出界判断
			if (y[2] < heightScreen || y[3] < heightScreen) {
				return false;
			}
		} else if (DRAG_DOWN == direction) {// 动作为下移
			// 下移出界判读
			if (y[0] > 0 || y[1] > 0) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean canZoom() {
		float[] x = new float[4];
		float[] y = new float[4];
		getFourPoint(x, y);
		// 图片现宽度
		double width = Math.sqrt((x[0] - x[1]) * (x[0] - x[1]) + (y[0] - y[1])
				* (y[0] - y[1]));
		double height = Math.sqrt((x[0] - x[2]) * (x[0] - x[2]) + (y[0] - y[2])
				* (y[0] - y[2]));
		// 缩放比例判断
		if (width < touchImgWidth * defaultScale - 1
				|| width > touchImgWidth * MAX_SCALE + 1) {
			return false;
		}
		// 出界判断
		if (width < widthScreen && height < heightScreen) {
			return false;
		}
		return true;
	}

	// 触碰两点间距离
	private static float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		if (x < 0) {
			x = -x;
		}
		float y = event.getY(0) - event.getY(1);
		if (y < 0) {
			y = -y;
		}
		return FloatMath.sqrt(x * x + y * y);
	}

	// 取手势中心点
	private static void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	// 第二个点移动时取第一个触碰点与第二个触碰点间的距离
	private static float distance(MotionEvent event, PointF down2) {
		float x = down2.x - event.getX();
		if (x < 0) {
			x = -x;
		}
		float y = down2.y - event.getY();
		if (y < 0) {
			y = -y;
		}
		return FloatMath.sqrt(x * x + y * y);
	}

	private void changeSize(float x, float y) {
		if (isBig) {
			float subX = (widthScreen - touchImgWidth * defaultScale) / 2;
			float subY = (heightScreen - touchImgHeight * defaultScale) / 2;
			preMatrix.reset();
			preMatrix.postScale(defaultScale, defaultScale);
			preMatrix.postTranslate(subX, subY);
			matrix.set(preMatrix);
			invalidate();

			isBig = false;
		} else {
			float transX = (widthScreen - touchImgWidth * MAX_SCALE) / 2;
			float transY = (heightScreen - touchImgHeight * MAX_SCALE) / 2;
			preMatrix.reset();
			preMatrix.postScale(MAX_SCALE, MAX_SCALE);
			preMatrix.postTranslate(transX, transY);
			matrix.set(preMatrix);
			invalidate();

			isBig = true;
		}

	}

}
