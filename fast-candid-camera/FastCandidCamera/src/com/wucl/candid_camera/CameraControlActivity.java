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
public class CameraControlActivity extends Activity {
	public ImageView imageView;
	Button takePictureButton;
	Button takeVideoButton;
	int i = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_control);
		takePictureButton = (Button) findViewById(R.id.btnTakePicture);
		takeVideoButton = (Button) findViewById(R.id.btnTakeVideo);
		imageView = (ImageView) findViewById(R.id.imageview);

		takePictureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CameraControlActivity.this,
						CameraPreview.class);
				startActivityForResult(intent, 1);
				Toast.makeText(getApplicationContext(), "开启拍摄",
						Toast.LENGTH_SHORT).show();

			}
		});
		takeVideoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 开启视频拍摄
				Toast.makeText(getApplicationContext(), "开启拍摄",
						Toast.LENGTH_SHORT).show();

			}
		});

	}

	// 返回拍摄结果处理
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == 20) {
				Bitmap cameraBitmap;
				byte[] bytes = data.getExtras().getByteArray("bytes");
				cameraBitmap = BitmapFactory.decodeByteArray(bytes, 0,
						bytes.length);
				// imageView.setImageBitmap(cameraBitmap);
				if (getWindowManager().getDefaultDisplay().getOrientation() == 0) {
					Matrix matrix = new Matrix();
					matrix.setRotate(90);
					cameraBitmap = Bitmap.createBitmap(cameraBitmap, 0, 0,
							cameraBitmap.getWidth(), cameraBitmap.getHeight(),
							matrix, true);
				}

				long time = System.currentTimeMillis();
				String File = "/sdcard/" + "camera" + time + ".jpg";
				// String File = "/sdcard/Snapshot/" + "camera" + time + ".jpg";
				File myCaptureFile = new File(File);
				// myCaptureFile.mkdirs();

				if (myCaptureFile.canRead())
					Log.v("EagleTag", "very bad-canRead");

				if (myCaptureFile.canWrite())
					Log.v("EagleTag", "very bad-canRead");

				try {
					myCaptureFile.createNewFile();
					FileOutputStream fileStream = new FileOutputStream(
							myCaptureFile);
					cameraBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileStream);
					Toast.makeText(getBaseContext(), "文件已经保存",
							Toast.LENGTH_LONG);
					fileStream.flush();
					fileStream.close();

					imageView.setImageBitmap(cameraBitmap);
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(getBaseContext(), e.toString(),
							Toast.LENGTH_LONG);
				}
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
