package com.google.android.gcm.demo.app;



import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class CheckActivity extends Activity {

	public final static int SIZE=16;	// dept 배열 크기
	
	// 선택사항들의 상태를 저장할 String 배열
	String[] deptStr=new String[SIZE];
	
	 AsyncTask<Void, Void, Void> mRegisterTask;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.check);
	}
	
	public void mOnClick(View view){
    	CheckBox cb=null;
    	int idx=0;
    	
    	cb=(CheckBox)findViewById(R.id.checkBox1);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox2);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox3);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox4);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox5);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox6);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox7);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox8);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox9);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox10);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox11);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox12);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox13);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox14);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox15);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	cb=(CheckBox)findViewById(R.id.checkBox16);
    	if(cb.isChecked()) deptStr[idx++]="T";
    	else deptStr[idx++]="F";
    	
    	final Context context = this;
    	
    	final String id=DemoActivity.getId();
    	Log.d("juvh", id);
    	
    	Toast.makeText(CheckActivity.this, id, Toast.LENGTH_SHORT).show();
    	
    	 mRegisterTask = new AsyncTask<Void, Void, Void>() {
             @Override
             protected Void doInBackground(Void... params) {
            	 boolean registered =
                         ServerUtilities.updateDept(context,id, deptStr);
                 // At this point all attempts to register with the app
                 // server failed, so we need to unregister the device
                 // from GCM - the app will try to register again when
                 // it is restarted. Note that GCM will send an
                 // unregistered callback upon completion, but
                 // GCMIntentService.onUnregistered() will ignore it.
                 if (!registered) {
                     
                 }
                 return null;
             }

             @Override
             protected void onPostExecute(Void result) {
                 mRegisterTask = null;
             }

         };
         mRegisterTask.execute(null, null, null);
    }
}
