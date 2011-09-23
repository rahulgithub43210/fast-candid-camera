package com.wucl.candid_camera;

import java.io.InputStream;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 新手教学
 * 可以通过设置中，进行开关，第一次默认打开。
 * 当此设置，下次开启时生效
 * @author wuchenliang
 *
 */
public class NoviceGuideActivity extends Activity {
	int click_num = 1;
	ImageView manuals;
	ImageButton backspace;
	ImageButton advance;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novice_guide1);
		manuals = (ImageView) findViewById(R.id.manual1);
		backspace = (ImageButton) findViewById(R.id.backspace);
		advance = (ImageButton) findViewById(R.id.advance);

		backspace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				click_num--;
//				Toast.makeText(getApplicationContext(), click_num + "",
//						Toast.LENGTH_SHORT).show();

				if (0 == click_num) {
					click_num = 0;
				}
				if (1 == click_num) {
					InputStream is = getResources().openRawResource(
							R.drawable.manual1);
					Bitmap mBitmap = BitmapFactory.decodeStream(is);
					manuals.setImageBitmap(mBitmap);
					backspace.setVisibility(View.INVISIBLE);
				}
				if (2 == click_num) {
					InputStream is = getResources().openRawResource(
							R.drawable.manual2);
					Bitmap mBitmap = BitmapFactory.decodeStream(is);
					manuals.setImageBitmap(mBitmap);
				}
				if (3 == click_num) {
					InputStream is = getResources().openRawResource(
							R.drawable.manual3);
					Bitmap mBitmap = BitmapFactory.decodeStream(is);
					manuals.setImageBitmap(mBitmap);
				}

			}
		});
		advance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				click_num++;
//				Toast.makeText(getApplicationContext(), click_num + "",
//						Toast.LENGTH_SHORT).show();
				if (2 == click_num) {
					InputStream is = getResources().openRawResource(
							R.drawable.manual2);
					Bitmap mBitmap = BitmapFactory.decodeStream(is);
					manuals.setImageBitmap(mBitmap);
					backspace.setVisibility(View.VISIBLE);

				}
				if (3 == click_num) {
					InputStream is = getResources().openRawResource(
							R.drawable.manual3);
					Bitmap mBitmap = BitmapFactory.decodeStream(is);
					manuals.setImageBitmap(mBitmap);
				}
				if (4 == click_num) {
					// open activity
					Intent camera = new Intent();
					camera.setClass(NoviceGuideActivity.this,
							CameraControlActivity.class);
					startActivity(camera);
					finish();
				}

			}
		});

	}

}
