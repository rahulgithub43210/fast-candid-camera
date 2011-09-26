package com.wucl.candid_camera;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

public class UtilHelp {
	Context context;
	private static PowerManager powerManager;
	private static WakeLock wakeLock;
	private static AudioManager audioManager;

	public UtilHelp(Context context) {
		this.context = context;
	}

	// <uses-permission android:name="android.permission.VIBRATE" />
	/**
	 * ��������
	 */
	public void vibrator() {

		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);

		long[] pattern = { 800, 50, 400, 30 }; // OFF/ON/OFF/ON...
		vibrator.vibrate(pattern, 2);
	}

	public static void set_no_title_fullscreen(Activity activity) {
		/** ȫ�����ã����ش�������װ�� */
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * ������Ļ�����̵ƣ�������
	 * 
	 * @return
	 */
	public static WakeLock screenControl(Activity activity) {
		powerManager = (PowerManager) activity
				.getSystemService(Context.POWER_SERVICE);// ��õ�Դ����
		wakeLock = powerManager.newWakeLock(
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "XYTEST");// ��Ļ�������̵ƹأ�������
		wakeLock.acquire();// ���i
		return wakeLock;
	}

	/**
	 * ���þ���
	 * 
	 * @param activity
	 * @return
	 */
	public static void audioControl(Activity activity, boolean control) {
		if (control) {

			audioManager = (AudioManager) activity
					.getSystemService(Context.AUDIO_SERVICE);// �������ϵͳ����
			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);// ���þ���ģʽ
			audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
			audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);// ���þ���ģʽ
		} else {
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}

	}
}
