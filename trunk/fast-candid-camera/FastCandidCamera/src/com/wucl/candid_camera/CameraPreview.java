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
 * ��������߼�
 * 
 * @author wuchenliang
 * 
 */
public class CameraPreview extends Activity {
	private Preview preview;
	private ImageView ivFocus;
	private int repeatCount = 0;// ��¼������ؼ��Ĵ���
	private long lastTime = 0;// ��һ�ε�����ؼ���ʱ��
	private AudioManager audioManager;
	private PowerManager powerManager;
	private WakeLock wakeLock;
	WindowManager.LayoutParams lp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** ȫ�����ã����ش�������װ�� */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		lp = getWindow().getAttributes();
		try {
			ivFocus = new ImageView(this);
		} catch (Exception e) {
		}
		preview = new Preview(this,this,ivFocus);
		setContentView(preview);
		screenControl();
	}

	/**
	 * ������Ļ�����̵ƣ�������
	 */
	private void screenControl() {
		powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);// ��õ�Դ����
		wakeLock = powerManager.newWakeLock(
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "XYTEST");// ��Ļ�������̵ƹأ�������
		wakeLock.acquire();// ���i
	}

	/**
	 * ���þ���
	 */
	private void audioControl(boolean control) {
		if (control) {

			audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);// �������ϵͳ����
			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);// ���þ���ģʽ
			audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
			audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);// ���þ���ģʽ
		} else {
//			try {
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//			} catch (NullPointerException e) {
//				e.printStackTrace();
//			}
		}

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
			audioControl(true);
			takePicture();
			// �������
//			audioControl(false);
			System.out.println("==========================");
			// Toast.makeText(this, "����ɹ�", Toast.LENGTH_SHORT);
			// if (action == KeyEvent.ACTION_DOWN) {
			// // ����
			//
			// }
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			System.out.println("==========================");
			// lp.screenBrightness = 0.0f;
			System.out.println("==========================");
			// Toast.makeText(this, "����ɹ�", Toast.LENGTH_SHORT);
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
			takePicture();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		menu.add(0, 0, 0, "�˳����");
		menu.add(0, 1, 0, "�����л�");
		menu.add(0, 2, 0, "�˳��˵�");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0:
			System.exit(0);
			break;
		case 1:
			break;
		case 2:
			this.closeOptionsMenu();// �رղ˵�
			break;
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		audioControl(false);//��ʼ������ȡ������
		wakeLock.release();// �ͷ���
	}

}

// try {
// audioManager = (AudioManager)
// getSystemService(Context.AUDIO_SERVICE);// �������ϵͳ����
// audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);// ���þ���ģʽ
// ivFocus = new ImageView(this);
// powerManager = (PowerManager)
// getSystemService(Context.POWER_SERVICE);// ��õ�Դ����
// wakeLock = powerManager.newWakeLock(
// PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "XYTEST");// ��Ļ�������̵ƹأ�������
//
// wakeLock.acquire();// ���i
// } catch (Exception e) {
// e.printStackTrace();
// }
