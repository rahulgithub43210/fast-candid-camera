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
 * ��������߼� �ϼ���ʼ���㡣 �¼���������������1��5��10��15��20��ѭ��ѡ��
 * 
 * @author wuchenliang
 */
public class CameraPreview extends Activity
{
	private Preview mPreview;
	private ImageView mIvFocus;
	private int mRepeatCount = 0;// ��¼������ؼ��Ĵ���
	private long lastTime = 0;// ��һ�ε�����ؼ���ʱ��
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
	 * ���ϰ��������㰴�����������㣬���°�������ָ������ (1��5��10��15��20)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent) ��д���������¼������ϰ���Ϊ���㣬���°���Ϊ�Ƴ�
	 * ���ε�����ؼ��˳�����
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
		{
			int action = event.getAction();
			if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_CAMERA)
			{
				// �Ƿ�����Ϊ����
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
				// �������3�˷����˳�����
				// event.getRepeatCount() �޷�ʹ�ã�������ô��������ص�int = 0��
				System.out.println("repeatcount " + event.getRepeatCount() + " +" + event.getDownTime() + " +" + lastTime);
				if (event.getDownTime() - lastTime < 1000)
				{// ���ε��������1��
					System.out.println("time : " + (event.getDownTime() - lastTime));
					mRepeatCount = mRepeatCount + 1;
				}
				else
				{
					mRepeatCount = 0;
				}
				if (mRepeatCount == 2)
				{// ���ε��
					Toast.makeText(getApplicationContext(), "�˳����C", Toast.LENGTH_SHORT);
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
	 * �������3�˷����˳�����
	 * 
	 * @param event
	 */
	public void clickToBack(KeyEvent event)
	{

		if (event.getDownTime() - lastTime < 1000)
		{// ���ε��������1��
			mRepeatCount = mRepeatCount + 1;
		}
		else
		{
			mRepeatCount = 0;
		}
		if (mRepeatCount == 2)
		{// ���ε��
			System.exit(0);
		}
		lastTime = event.getDownTime();
	}

	/**
	 * ������Ƭ�ķ�װ
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
	 * ͨ��������������
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
		menu.add(0, 0, 0, "�˳����");
		menu.add(0, 1, 0, "�����л�");
		menu.add(0, 2, 0, "�˳��˵�");
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
				// ��������
				break;
			case 2:
				this.closeOptionsMenu();// �رղ˵�
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
			UtilHelp.audioControl(this, false);// ����������ȡ������
		}
		catch (Exception e)
		{
			UtilHelp.audioControl(this, true);
			UtilHelp.audioControl(this, false);
		}
		wakeLock.release();// �ͷ���
	}
}