package com.android.myimagescan;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.android.myimagescan.bean.ImageBean;
import com.example.myimagescan.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity {
	private HashMap<String, List<String>> mGroupMap = new HashMap<String, List<String>>();
	private List<ImageBean> list = new ArrayList<ImageBean>();
	private final static int SCAN_OK = 1;
	private ProgressDialog mProgressDialog;
	private GroupAdapter adapter;
	private GridView mGroupGridView;

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SCAN_OK:
				// �رս�����
				mProgressDialog.dismiss();

				adapter = new GroupAdapter(MainActivity.this,
						list = subGroupOfImage(mGroupMap), mGroupGridView);
				mGroupGridView.setAdapter(adapter);
				break;

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mGroupGridView = (GridView) findViewById(R.id.main_grid);
		getImages();

		mGroupGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				List<String> childList = mGroupMap.get(list.get(position)
						.getFolderName());

				Intent mIntent = new Intent(MainActivity.this,
						ShowImageActivity.class);
				mIntent.putStringArrayListExtra("data",
						(ArrayList<String>) childList);
				startActivity(mIntent);
			}
		});
	}

	/**
	 * ����ContentProviderɨ���ֻ��е�ͼƬ���˷��������������߳���
	 */
	private void getImages() {
		// TODO Auto-generated method stub
		// ��ʾ������
		mProgressDialog = ProgressDialog.show(this, null, "���ڼ��ء�����");

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = MainActivity.this
						.getContentResolver();

				// ֻ��ѯjpeg��png��ͼƬ
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or"
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);
				if (mCursor == null) {
					return;
				}
				while (mCursor.moveToNext()) {
					// ��ȡͼƬ��·��
					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					// ��ȡ��ͼƬ�ĸ�·����

					String parentName = new File(path).getParentFile()
							.getName();

					// ���ݸ�·������ͼƬ����mGroupMap��
					if (!mGroupMap.containsKey(parentName)) {
						List<String> childList = new ArrayList<String>();
						childList.add(path);
						mGroupMap.put(parentName, childList);
					} else {
						mGroupMap.get(parentName).add(path);
					}
				}
				// ֪ͨHandlerɨ��ͼƬ���
				mHandler.sendEmptyMessage(SCAN_OK);
				mCursor.close();
			}

		}).start();

	}
    /** 
     * ��װ�������GridView������Դ����Ϊ����ɨ���ֻ���ʱ��ͼƬ��Ϣ����HashMap�� 
     * ������Ҫ����HashMap��������װ��List 
     *  
     * @param mGroupMap 
     * @return 
     */  
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
