package com.fingerRunnerSample.www;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class FingerRunnerMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater layout = LayoutInflater.from(this);
		View view = layout.inflate(R.layout.main, null);
		setContentView(view);

		//사용자 화면이 이벤트를 받을수 있도록 처리
		view.setFocusable(true); 
		view.setFocusableInTouchMode(true);

	}


	@Override
	protected void onPause() {
		super.onPause();
		onStop();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}