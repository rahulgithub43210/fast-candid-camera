package com.wucl.candid_camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 拍摄动作控制
 * 
 * @author wuchenliang
 * 
 */
public class CameraControlActivity extends Activity {
	public ImageView mImagePlayback;
	private static final String PATHDIVISION = "/";
	private static final int SettingActivityRequestCode = 2;
	private static final int CameraActivityRequestCode = 1;
	private Button mTakePictureButton;
	private Button mTakeVideoButton;
	private File sdcardFilePatch;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_control);
		mTakePictureButton = (Button) findViewById(R.id.btnTakePicture);
		mTakeVideoButton = (Button) findViewById(R.id.btnTakeVideo);
		mImagePlayback = (ImageView) findViewById(R.id.imageview);

		mTakePictureButton.setVisibility(8);
		mTakeVideoButton.setVisibility(8);
		sdcardFilePatch = android.os.Environment.getExternalStorageDirectory();
		// android view的setVisibility方法值的意思
		// 有三个值 visibility One of VISIBLE, INVISIBLE, or GONE.
		//
		// 常量值为0，意思是可见的
		// 常量值为4，意思是不可见的
		// 常量值为8，意思是不可见的，而且不占用布局空间

		mTakePictureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CameraControlActivity.this,
						CameraPreviewActivity.class);
				startActivityForResult(intent, 1);
				// showToast("开启拍摄");

			}
		});
		mTakeVideoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 开启视频拍摄

			}
		});

		showToast("按上键拍摄");
	}

	// 返回拍摄结果处理
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CameraActivityRequestCode) {
			if (resultCode == 20) {
				Bitmap cameraBitmap = readDataForBitmap(data);
				savePic(cameraBitmap);
			}
		} else if (requestCode == SettingActivityRequestCode) {

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 从data中获取bitmap数据
	 * 
	 * @param data
	 * @return
	 */
	public Bitmap readDataForBitmap(Intent data) {
		Bitmap cameraBitmap;
		byte[] bytes = data.getExtras().getByteArray("bytes");
		cameraBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		// imageView.setImageBitmap(cameraBitmap);
		if (getWindowManager().getDefaultDisplay().getOrientation() == 0) {
			Matrix matrix = new Matrix();
			matrix.setRotate(90);
			cameraBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0,
					cameraBitmap.getWidth(), cameraBitmap.getHeight(), matrix,
					true);
		}
		return cameraBitmap;
	}

	private String setPicPath(String path, String name, String expanded_name) {
		StringBuffer filePath = new StringBuffer();
		long time = System.currentTimeMillis();

		filePath.append(sdcardFilePatch);
		filePath.append(PATHDIVISION);
		filePath.append(path);
		filePath.append(PATHDIVISION);
		filePath.append(name);
		filePath.append(time);
		filePath.append(expanded_name);
		return filePath.toString();
	}

	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	private void savePic(Bitmap cameraBitmap) {
		File dir = UtilHelp.openDir(sdcardFilePatch, "FastCandidCamera");
		if (!dir.exists()) {
			// 判断文件夹是否存在,如果不存在则创建文件夹
			dir.mkdir();
		}
		File myCaptureFile = new File(setPicPath("FastCandidCamera", "camera",
				".jpg"));
		try {
			myCaptureFile.createNewFile();
			FileOutputStream fileStream = new FileOutputStream(myCaptureFile);
			cameraBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileStream);
			UtilHelp.vibrator(this);
			showToast("文件已经保存，按上键继续拍摄");
			fileStream.flush();
			fileStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		mImagePlayback.setImageBitmap(cameraBitmap);
	}

	/*
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 * 重写按键监听事件，向上按键为拍摄，向下按键为推出 三次点击返回键退出拍摄
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			int action = event.getAction();
			if (keyCode == KeyEvent.KEYCODE_VOLUME_UP
					|| keyCode == KeyEvent.KEYCODE_CAMERA) {
				Intent intent = new Intent(CameraControlActivity.this,
						CameraPreviewActivity.class);
				startActivityForResult(intent, CameraActivityRequestCode);
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "退出相机");
		menu.add(0, 1, 0, "功能设置");
		menu.add(0, 2, 0, "退出菜单");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			System.exit(0);
			break;
		case 1:
			Intent intent = new Intent(CameraControlActivity.this,
					SettingActivity.class);
			startActivityForResult(intent, SettingActivityRequestCode);
			break;
		case 2:
			this.closeOptionsMenu();// 关闭菜单
			break;
		}
		return false;
	}

}
