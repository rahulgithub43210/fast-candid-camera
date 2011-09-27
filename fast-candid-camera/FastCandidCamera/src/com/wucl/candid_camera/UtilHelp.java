package com.wucl.candid_camera;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class UtilHelp {
	static Context context;
	private static PowerManager powerManager;
	private static WakeLock wakeLock;
	private static AudioManager audioManager;


	// <uses-permission android:name="android.permission.VIBRATE" />
	/**
	 * 振动器控制
	 */
	public static void vibrator(Context context) {

		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);

		long[] pattern = { 1000, 100, 400, 100 }; // OFF/ON/OFF/ON...
		vibrator.vibrate(pattern, -1);
	}

	public static void set_no_title_fullscreen(Activity activity) {
		/** 全屏设置，隐藏窗口所有装饰 */
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * 控制屏幕，键盘灯，呼吸灯
	 * 
	 * @return
	 */
	public static WakeLock screenControl(Activity activity) {
		powerManager = (PowerManager) activity
				.getSystemService(Context.POWER_SERVICE);// 获得电源服务
		wakeLock = powerManager.newWakeLock(
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "XYTEST");// 屏幕亮，键盘灯关，不休眠
		wakeLock.acquire();// 加鎖
		return wakeLock;
	}

	/**
	 * 设置静音
	 * 
	 * @param activity
	 * @return
	 */
	public static void audioControl(Activity activity, boolean control) {
		if (control) {

			audioManager = (AudioManager) activity
					.getSystemService(Context.AUDIO_SERVICE);// 获得声音系统服务
			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);// 设置静音模式
			audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
			audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);// 设置静音模式
		} else {
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}

	}
	
	
	
	public static void  get (Activity activity){
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		Log.i("","屏幕分辨率为:"+dm.widthPixels+" * "+dm.heightPixels);
	}
}
