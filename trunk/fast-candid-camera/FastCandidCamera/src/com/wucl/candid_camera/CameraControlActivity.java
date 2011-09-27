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
 * ���㶯������
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
		// android view��setVisibility����ֵ����˼
		// ������ֵ visibility One of VISIBLE, INVISIBLE, or GONE.
		//
		// ����ֵΪ0����˼�ǿɼ���
		// ����ֵΪ4����˼�ǲ��ɼ���
		// ����ֵΪ8����˼�ǲ��ɼ��ģ����Ҳ�ռ�ò��ֿռ�

		mTakePictureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CameraControlActivity.this,
						CameraPreviewActivity.class);
				startActivityForResult(intent, 1);
				// showToast("��������");

			}
		});
		mTakeVideoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO ������Ƶ����

			}
		});

		showToast("���ϼ�����");
	}

	// ��������������
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
	 * ��data�л�ȡbitmap����
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
			// �ж��ļ����Ƿ����,����������򴴽��ļ���
			dir.mkdir();
		}
		File myCaptureFile = new File(setPicPath("FastCandidCamera", "camera",
				".jpg"));
		try {
			myCaptureFile.createNewFile();
			FileOutputStream fileStream = new FileOutputStream(myCaptureFile);
			cameraBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileStream);
			UtilHelp.vibrator(this);
			showToast("�ļ��Ѿ����棬���ϼ���������");
			fileStream.flush();
			fileStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		mImagePlayback.setImageBitmap(cameraBitmap);
	}

	/*
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 * ��д���������¼������ϰ���Ϊ���㣬���°���Ϊ�Ƴ� ���ε�����ؼ��˳�����
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
		menu.add(0, 0, 0, "�˳����");
		menu.add(0, 1, 0, "��������");
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
			Intent intent = new Intent(CameraControlActivity.this,
					SettingActivity.class);
			startActivityForResult(intent, SettingActivityRequestCode);
			break;
		case 2:
			this.closeOptionsMenu();// �رղ˵�
			break;
		}
		return false;
	}

}
