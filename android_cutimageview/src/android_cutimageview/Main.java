package android_cutimageview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android_cutimageview.R;

public class Main extends Activity implements OnClickListener {
	private Button selectImageBtn;
	private Button cutImageBtn;
	private ImageView imageView;
	// ����������̬�����ͱ�����������ͼ�ķ��صı�־
	private static final int IMAGE_SELECT = 1;
	private static final int IMAGE_CUT = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		selectImageBtn = (Button) findViewById(R.id.selectImageBtn);
		cutImageBtn = (Button) findViewById(R.id.cutImageBtn);
		imageView = (ImageView) findViewById(R.id.imageView);

		selectImageBtn.setOnClickListener(this);
		cutImageBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.selectImageBtn:
			// ��ȡ�ֻ��е���Ƭ�����Ҿ���ѡ��ͼƬ�Ĺ��ܡ�
			Intent intent1 = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent1, IMAGE_SELECT);
			break;
		case R.id.cutImageBtn:
			Intent intent2 = getImageClipIntent();
			startActivityForResult(intent2, IMAGE_CUT);
			break;

		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			//����ͼƬ�����ֻ�����Ļ��С��ʾ
			if(requestCode == IMAGE_SELECT){
				Uri uri = data.getData();
				Point size = new Point();
				getWindowManager().getDefaultDisplay().getSize(size);
				int dw = size.x;
				int dh = size.y/2;
				try {
					BitmapFactory.Options opt = new BitmapFactory.Options();
					opt.inJustDecodeBounds = true;
					Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri),null,opt);
					//��ͼƬ�Ŀ��Ⱥ͸߶ȶ�Ӧ�ֻ���Ļ����ƥ�䣻
					int hRatio = (int)Math.ceil(opt.outHeight/(float)dh);
					int wRatio = (int)Math.ceil(opt.outWidth/(float)dw);
					if(hRatio>1||wRatio>1){
						opt.inSampleSize = hRatio > wRatio ? hRatio:wRatio;
					}
					opt.inJustDecodeBounds = false;
					bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri),null,opt);
					imageView.setImageBitmap(bmp);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			else if(requestCode == IMAGE_CUT){
				Bitmap bitmap = data.getParcelableExtra("data");
				imageView.setImageBitmap(bitmap);
			}
		}
	}
	private Intent getImageClipIntent() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT,null);//ACTION_GET_CONTENT:�ó������û�ѡ���ض����͵����ݣ������ظ����ݵ�URI.
		//ʵ�ֶ�ͼƬ�Ĳü�����������ͼƬ�����Ժʹ�С��
		intent.setType("image/*");//��ȡ����ͼƬ����
		intent.putExtra("crop", "true");//����ѡ��ͼƬ����
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 80); 
		intent.putExtra("outputY", 80);
		intent.putExtra("return-datad", true);
		return intent;
	}
}