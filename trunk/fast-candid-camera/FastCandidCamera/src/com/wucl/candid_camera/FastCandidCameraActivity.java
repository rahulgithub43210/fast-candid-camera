package com.wucl.candid_camera;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 第一屏
 * @author wuchenliang
 *
 */
public class FastCandidCameraActivity extends Activity {
    /** Called when the activity is first created. */
	Intent noviceGuideIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button openNoviceGuide = (Button) findViewById(R.id.openNoviceGuide);
        Button openCameraButton = (Button) findViewById(R.id.openCamera);
        Button setting =(Button)findViewById(R.id.setting);
        noviceGuideIntent = new Intent();
        
        openNoviceGuide.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		        //添加新手导航页
		        //提示显示新手导航
//		        Toast.makeText(this, "开启新手导航", Toast.LENGTH_SHORT).show();
		        Toast.makeText(getApplicationContext(), "开启新手导航", Toast.LENGTH_SHORT).show();
		        
		        
//		        noviceGuideIntent.setClass(FastCandidCameraActivity.this,Test.class);
//		        noviceGuideIntent.setClassName(getApplication(), "Test");
		        Class<?> NoviceGuideActivity = null ;
		        try {
		        	NoviceGuideActivity = Class.forName("com.wucl.candid_camera.NoviceGuideActivity");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Log.d("sssssss", NoviceGuideActivity.getName());
		        noviceGuideIntent.setClass(FastCandidCameraActivity.this,
		        		NoviceGuideActivity);
				startActivity(noviceGuideIntent);
			}
		});

        //转入 拍摄
		openCameraButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent camera = new Intent();
				camera.setClass(FastCandidCameraActivity.this,
						CameraControlActivity.class);
				startActivity(camera);
				finish();
				
			}
		});
		//转入设置
		setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent settingPage = new Intent();
				settingPage.setClass(FastCandidCameraActivity.this,
						SettingActivity.class);
				startActivity(settingPage);
				finish();
				
			}
		});
      
        
        
        
    }
}