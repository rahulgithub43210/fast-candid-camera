package com.wucl.candid_camera;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class Test extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

	}
	/*
	//Definition
	static Uri mTempUri = null;
	String sTempFilename = "tempfile.jpg";
//	File path = new File(android.os.Environment.getExternalStorageDirectory(),sTempFilename);
	mTempUri = Uri.fromFile(path);

	//startActivity with extra information
	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	      if(mTempUri != null) {
	         intent.putExtra(MediaStore.EXTRA_OUTPUT,mTempUri);
	      }
	startActivityForResult(intent, CAMERA_WITH_DATA);
}






//result
protected void onActivityResult(int requestCode, int resultCode, Intent data)
{
if (resultCode == RESULT_OK ) {
  if(mTempUri != null) {
      File file = new File(android.os.Environment.getExternalStorageDirectory(),sTempFilename);
      Uri uri = Uri.fromFile(file);
      // here you can decode Bitmap file using BitmapFactory.decodeFile()
  }
}         
}

*/

}


