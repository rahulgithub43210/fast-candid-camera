package com.wucl.candid_camera;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 设置菜单
 * 提供1，界面伪装 2，新手教程开关 3，振动开关
 * @author wuchenliang
 *
 */
public class SettingActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

	}

//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
//			System.out.println("==========================");
////			Toast.makeText(this, "拍摄成功", Toast.LENGTH_SHORT);
//			return true;
//		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
//			System.out.println("==========================");	
//			System.out.println("==========================");
////			Toast.makeText(this, "拍摄成功", Toast.LENGTH_SHORT);
//			return true;
//		} else {
//			System.out.println("==========================");
//			System.out.println(keyCode);
//			System.out.println("==========================");
//		}
//
//		return false;
//	}

}
