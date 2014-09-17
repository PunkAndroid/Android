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
	//通用的显示对话框的方法
	private void showDialog(String title, String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//设置对话框的图标
		builder.setIcon(android.R.drawable.ic_dialog_info);
		//设置对话框的标题
		builder.setTitle(title);
		//设置对话框显示的信息
		builder.setMessage(msg);
		//设置对话框的按钮
		builder.setPositiveButton("确定", null);
		//显示对话框
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
				//显示当前日期
				showDialog("当前日期", sdf.format(new Date()));
				break;
			}
			case R.id.btnShowTime:
			{
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				//显示当前日期
				showDialog("当前时间", sdf.format(new Date()));
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
