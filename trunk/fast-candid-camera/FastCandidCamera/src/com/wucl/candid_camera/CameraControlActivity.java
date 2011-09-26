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
public class CameraControlActivity extends Activity
{
	public ImageView mImagePlayback;
	private static final String PATHDIVISION = "/";
	private Button mTakePictureButton;
	private Button mTakeVideoButton;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_control);
		mTakePictureButton = (Button) findViewById(R.id.btnTakePicture);
		mTakeVideoButton = (Button) findViewById(R.id.btnTakeVideo);
		mImagePlayback = (ImageView) findViewById(R.id.imageview);

		mTakePictureButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(CameraControlActivity.this, CameraPreview.class);
				startActivityForResult(intent, 1);
				showToast("开启拍摄");

			}
		});
		mTakeVideoButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO 开启视频拍摄
				showToast("开启拍摄");

			}
		});
	}

	// 返回拍摄结果处理
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 1)
		{
			if (resultCode == 20)
			{
				Bitmap cameraBitmap;
				byte[] bytes = data.getExtras().getByteArray("bytes");
				cameraBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
				// imageView.setImageBitmap(cameraBitmap);
				if (getWindowManager().getDefaultDisplay().getOrientation() == 0)
				{
					Matrix matrix = new Matrix();
					matrix.setRotate(90);
					cameraBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0, cameraBitmap.getWidth(), cameraBitmap.getHeight(), matrix, true);
				}
				savePic(cameraBitmap);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private String setPicPath(String path, String name, String expanded_name)
	{
		StringBuffer filePath = new StringBuffer();
		long time = System.currentTimeMillis();
		filePath.append(PATHDIVISION);
		filePath.append(path);
		filePath.append(PATHDIVISION);
		filePath.append(name);
		filePath.append(time);
		filePath.append(expanded_name);
		return filePath.toString();
	}

	private void showToast(String message)
	{
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	private void savePic(Bitmap cameraBitmap)
	{
		File myCaptureFile = new File(setPicPath("sdcard", "camera", ".jpg"));
		// myCaptureFile.mkdirs();
		// if (myCaptureFile.canRead())
		// Log.v("EagleTag", "very bad-canRead");
		//
		// if (myCaptureFile.canWrite())
		// Log.v("EagleTag", "very bad-canRead");
		try
		{
			myCaptureFile.createNewFile();
			FileOutputStream fileStream = new FileOutputStream(myCaptureFile);
			cameraBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileStream);
			// Toast.makeText(getBaseContext(), "文件已经保存",
			// Toast.LENGTH_LONG);
			showToast("文件已经保存");
			fileStream.flush();
			fileStream.close();

			mImagePlayback.setImageBitmap(cameraBitmap);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			// Toast.makeText(getBaseContext(), e.toString(),
			// Toast.LENGTH_LONG);
			showToast(e.toString());
		}
	}

}
