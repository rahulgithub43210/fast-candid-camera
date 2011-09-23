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
 * 拍摄控制逻辑
 * 
 * @author wuchenliang
 * 
 */
public class CameraPreview extends Activity {
	private Preview preview;
	private ImageView ivFocus;
	private int repeatCount = 0;// 记录点击返回键的次数
	private long lastTime = 0;// 上一次点击返回键的时间
	private AudioManager audioManager;
	private PowerManager powerManager;
	private WakeLock wakeLock;
	WindowManager.LayoutParams lp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** 全屏设置，隐藏窗口所有装饰 */
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
	 * 控制屏幕，键盘灯，呼吸灯
	 */
	private void screenControl() {
		powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);// 获得电源服务
		wakeLock = powerManager.newWakeLock(
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "XYTEST");// 屏幕亮，键盘灯关，不休眠
		wakeLock.acquire();// 加鎖
	}

	/**
	 * 设置静音
	 */
	private void audioControl(boolean control) {
		if (control) {

			audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);// 获得声音系统服务
			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);// 设置静音模式
			audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
			audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);// 设置静音模式
		} else {
//			try {
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//			} catch (NullPointerException e) {
//				e.printStackTrace();
//			}
		}

	}

	/*
	 * 向上按键，拍摄按键，单次拍摄一张，向下按键连拍指定张数 (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 * 重写按键监听事件，向上按键为拍摄，向下按键为推出 三次点击返回键退出拍摄
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		int action = event.getAction();
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			// 是否设置为静音
			audioControl(true);
			takePicture();
			// 解除静音
//			audioControl(false);
			System.out.println("==========================");
			// Toast.makeText(this, "拍摄成功", Toast.LENGTH_SHORT);
			// if (action == KeyEvent.ACTION_DOWN) {
			// // 拍摄
			//
			// }
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			System.out.println("==========================");
			// lp.screenBrightness = 0.0f;
			System.out.println("==========================");
			// Toast.makeText(this, "拍摄成功", Toast.LENGTH_SHORT);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 连续点击3此返回退出程序
			// event.getRepeatCount() 无法使用，无论怎么点击，返回的int = 0；
			System.out.println("repeatcount " + event.getRepeatCount() + " +"
					+ event.getDownTime() + " +" + lastTime);
			if (event.getDownTime() - lastTime < 1000) {// 两次点击不超过1秒
				System.out
						.println("time : " + (event.getDownTime() - lastTime));
				repeatCount = repeatCount + 1;
			} else {
				repeatCount = 0;
			}
			if (repeatCount == 2) {// 三次点击
				Toast.makeText(getApplicationContext(), "退出相機",
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
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent) 通过触屏触发拍摄
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

		menu.add(0, 0, 0, "退出相机");
		menu.add(0, 1, 0, "功能切换");
		menu.add(0, 2, 0, "退出菜单");
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
			this.closeOptionsMenu();// 关闭菜单
			break;
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		audioControl(false);//开始声音，取消静音
		wakeLock.release();// 释放锁
	}

}

// try {
// audioManager = (AudioManager)
// getSystemService(Context.AUDIO_SERVICE);// 获得声音系统服务
// audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);// 设置静音模式
// ivFocus = new ImageView(this);
// powerManager = (PowerManager)
// getSystemService(Context.POWER_SERVICE);// 获得电源服务
// wakeLock = powerManager.newWakeLock(
// PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "XYTEST");// 屏幕亮，键盘灯关，不休眠
//
// wakeLock.acquire();// 加鎖
// } catch (Exception e) {
// e.printStackTrace();
// }
