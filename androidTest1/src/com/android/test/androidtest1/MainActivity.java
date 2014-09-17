package com.android.test.androidtest1;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	//ͨ�õ���ʾ�Ի���ķ���
	private void showDialog(String title, String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//���öԻ����ͼ��
		builder.setIcon(android.R.drawable.ic_dialog_info);
		//���öԻ���ı���
		builder.setTitle(title);
		//���öԻ�����ʾ����Ϣ
		builder.setMessage(msg);
		//���öԻ���İ�ť
		builder.setPositiveButton("ȷ��", null);
		//��ʾ�Ի���
		builder.create().show();
		Intent intent;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btnShowDate:
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				//��ʾ��ǰ����
				showDialog("��ǰ����", sdf.format(new Date()));
				break;
			}
			case R.id.btnShowTime:
			{
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				//��ʾ��ǰ����
				showDialog("��ǰʱ��", sdf.format(new Date()));
				break;
			}
		}
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnShowDate = (Button) findViewById(R.id.btnShowDate);
        Button btnShowTime = (Button) findViewById(R.id.btnShowTime);
        btnShowDate.setOnClickListener(this);
        btnShowTime.setOnClickListener(this);
    }

}
