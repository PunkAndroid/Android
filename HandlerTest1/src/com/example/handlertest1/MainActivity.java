package com.example.handlertest1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			//��ӡ��ǰ�̵߳�ID
			System.out.println("Fragment-->" + Thread.currentThread().getId());
			//����HandlerThread����ʵ��ʹ��Looper��������Ϣ���еĹ��ܣ��������AndroidӦ�ó������ṩ
			HandlerThread handlerThread = new HandlerThread("handler_thread");
			handlerThread.start();
			MyHandler myHandler = new MyHandler(handlerThread.getLooper());
			Message msg = myHandler.obtainMessage();
			Bundle data = new Bundle();
			data.putInt("age", 12);
			data.putString("path", "Jhon");
			/*
			 * ���ݼ򵥶���
			msg.obj = "abc";
			*/
			msg.setData(data);
			//��msg���͸�Ŀ�������ν��Ŀ����󣬾������ɸ�msg�����handler����
			msg.sendToTarget();
			return rootView;
		}

		class MyHandler extends Handler {
			public MyHandler(Looper looper) {
				super(looper);
			}
			private void handlerMessage(Message msg) {
				// TODO Auto-generated method stub
				/*
				 * ���ռ򵥶���
				 * String s = (String)msg.obj; 
				*/
				Bundle data = msg.getData();
				int age = data.getInt("age");
				String name = data.getString("name");
				System.out.println("age is : " + age +" name is : " + name);
				System.out.println("MyHandler-->"+Thread .currentThread().getId());
				System.out.println("handlerMessage");
			}
		}
	}

}
