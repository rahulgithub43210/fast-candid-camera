package com.wucl.candid_camera;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 拍摄控制逻辑 上键开始拍摄。 下键控制拍摄张数，1，5，10，15，20。循环选择。
 * 
 * @author wuchenliang
 */
public class CameraPreview extends Activity
{
	private Preview mPreview;
	private ImageView mIvFocus;
	private int mRepeatCount = 0;// 记录点击返回键的次数
	private long lastTime = 0;// 上一次点击返回键的时间
	private WakeLock wakeLock;
	WindowManager.LayoutParams lp;
	private int countOfNext = 1;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		UtilHelp.set_no_title_fullscreen(this);
		lp = getWindow().getAttributes();
		try
		{
			mIvFocus = new ImageView(this);
		}
		catch (Exception e)
		{
		}
		mPreview = new Preview(this, this, mIvFocus);
		setContentView(mPreview);
		wakeLock = UtilHelp.screenControl(this);
	}

	/*
	 * 向上按键，拍摄按键，开启拍摄，向下按键调整指定张数 (1，5，10，15，20)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent) 重写按键监听事件，向上按键为拍摄，向下按键为推出
	 * 三次点击返回键退出拍摄
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
		{
			int action = event.getAction();
			if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_CAMERA)
			{
				// 是否设置为静音
				UtilHelp.audioControl(this, true);
				takePicture();
				// if (action == KeyEvent.ACTION_DOWN) {
				// }
				return true;
			}
			else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
			{
				if (countOfNext == 1)
				{
					countOfNext = 5;
				}
				else if (countOfNext == 20)
				{
					countOfNext = 1;
				}
				else
				{
					countOfNext += 5;
				}
				return true;
			}
			else if (keyCode == KeyEvent.KEYCODE_BACK)
			{
				// 连续点击3此返回退出程序
				// event.getRepeatCount() 无法使用，无论怎么点击，返回的int = 0；
				System.out.println("repeatcount " + event.getRepeatCount() + " +" + event.getDownTime() + " +" + lastTime);
				if (event.getDownTime() - lastTime < 1000)
				{// 两次点击不超过1秒
					System.out.println("time : " + (event.getDownTime() - lastTime));
					mRepeatCount = mRepeatCount + 1;
				}
				else
				{
					mRepeatCount = 0;
				}
				if (mRepeatCount == 2)
				{// 三次点击
					Toast.makeText(getApplicationContext(), "退出相機", Toast.LENGTH_SHORT);
					System.out.println("3repeatcount   " + mRepeatCount);
					System.exit(0);
				}
				lastTime = event.getDownTime();
				clickToBack(event);
				return true;
			}
			else
			{
				System.out.println("==========================");
				System.out.println(keyCode);
				System.out.println("==========================");
			}
		}
		return false;
	}

	/**
	 * 连续点击3此返回退出程序
	 * 
	 * @param event
	 */
	public void clickToBack(KeyEvent event)
	{

		if (event.getDownTime() - lastTime < 1000)
		{// 两次点击不超过1秒
			mRepeatCount = mRepeatCount + 1;
		}
		else
		{
			mRepeatCount = 0;
		}
		if (mRepeatCount == 2)
		{// 三次点击
			System.exit(0);
		}
		lastTime = event.getDownTime();
	}

	/**
	 * 拍摄照片的封装
	 */
	private void takePicture()
	{
		try
		{
			mPreview.takePicture();
		}
		catch (Exception e)
		{
			this.onDestroy();
		}
	}

	/*
	 * 通过触屏触发拍摄
	 * 
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			UtilHelp.audioControl(this, true);
			takePicture();
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(0, 0, 0, "退出相机");
		menu.add(0, 1, 0, "功能切换");
		menu.add(0, 2, 0, "退出菜单");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case 0:
				System.exit(0);
				break;
			case 1:
				// 进入设置
				break;
			case 2:
				this.closeOptionsMenu();// 关闭菜单
				break;
		}
		return false;
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		try
		{
			UtilHelp.audioControl(this, false);// 开启声音，取消静音
		}
		catch (Exception e)
		{
			UtilHelp.audioControl(this, true);
			UtilHelp.audioControl(this, false);
		}
		wakeLock.release();// 释放锁
	}
}