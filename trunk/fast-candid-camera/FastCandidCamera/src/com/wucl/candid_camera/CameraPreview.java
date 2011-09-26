package com.wucl.candid_camera;

import java.io.IOException;
import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

/**
 * ��������߼� �ϼ���ʼ���㡣 �¼���������������5��10��15��20��ѭ��ѡ��
 * 
 * @author wuchenliang
 */
public class CameraPreview extends Activity {
	private Preview preview;
	private ImageView ivFocus;
	private int repeatCount = 0;// ��¼������ؼ��Ĵ���
	private long lastTime = 0;// ��һ�ε�����ؼ���ʱ��
	private WakeLock wakeLock;
	WindowManager.LayoutParams lp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UtilHelp.set_no_title_fullscreen(this);
		lp = getWindow().getAttributes();
		try {
			ivFocus = new ImageView(this);
		} catch (Exception e) {
		}
		preview = new Preview(this, this, ivFocus);
		setContentView(preview);
		wakeLock = UtilHelp.screenControl(this);
	}

	/*
	 * ���ϰ��������㰴������������һ�ţ����°�������ָ������ (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 * ��д���������¼������ϰ���Ϊ���㣬���°���Ϊ�Ƴ� ���ε�����ؼ��˳�����
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		int action = event.getAction();
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			// �Ƿ�����Ϊ����
			UtilHelp.audioControl(this, true);
			takePicture();
			// if (action == KeyEvent.ACTION_DOWN) {
			// // ����
			// }
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			// �������3�˷����˳�����
			// event.getRepeatCount() �޷�ʹ�ã�������ô��������ص�int = 0��
			System.out.println("repeatcount " + event.getRepeatCount() + " +"
					+ event.getDownTime() + " +" + lastTime);
			if (event.getDownTime() - lastTime < 1000) {// ���ε��������1��
				System.out
						.println("time : " + (event.getDownTime() - lastTime));
				repeatCount = repeatCount + 1;
			} else {
				repeatCount = 0;
			}
			if (repeatCount == 2) {// ���ε��
				Toast.makeText(getApplicationContext(), "�˳����C",
						Toast.LENGTH_SHORT);
				System.out.println("3repeatcount   " + repeatCount);
				System.exit(0);
			}
			lastTime = event.getDownTime();
			return true;
		} else {
			System.out.println("==========================");
			System.out.println(keyCode);
			System.out.println("==========================");
		}
		return false;
	}

	private void takePicture() {
		try {
			preview.takePicture();
		} catch (Exception e) {
			this.onDestroy();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent) ͨ��������������
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			UtilHelp.audioControl(this, true);
		takePicture();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "�˳����");
		menu.add(0, 1, 0, "�����л�");
		menu.add(0, 2, 0, "�˳��˵�");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			System.exit(0);
			break;
		case 1:
			// ��������
			break;
		case 2:
			this.closeOptionsMenu();// �رղ˵�
			break;
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			UtilHelp.audioControl(this, false);// ����������ȡ������
		} catch (Exception e) {
			// TODO: handle exception
			UtilHelp.audioControl(this, true);
			UtilHelp.audioControl(this, false);
		}
		wakeLock.release();// �ͷ���
	}
}