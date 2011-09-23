package com.wucl.candid_camera;

import android.content.Context;
import android.os.Vibrator;

public class UtilHelp {
	Context context;
	public UtilHelp(Context context){
		this.context = context;
	}
	
	//<uses-permission android:name="android.permission.VIBRATE" />
	/**
	 *Õñ¶¯Æ÷¿ØÖÆ 
	 */
	public void vibrator(){
		
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		
		         long[] pattern = {  
		                 800, 50, 400, 30  
		         }; // OFF/ON/OFF/ON...  
		         vibrator.vibrate(pattern, 2);  
	}
	 
}
