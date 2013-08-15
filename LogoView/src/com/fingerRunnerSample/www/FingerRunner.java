package com.fingerRunnerSample.www;

import com.fingerRunnerSample.www.R;

import net.daum.mobilead.AdConfig;
import net.daum.mobilead.AdHttpListener;
import net.daum.mobilead.MobileAdView;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class FingerRunner extends Activity implements AdHttpListener {
	// InterstitialAdListener {
	private View view;

	// cauly
	public RelativeLayout test;

	private boolean mActivityInPause = true;

	/** adam Called when the activity is first created. */
	private MobileAdView adView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//
		LayoutInflater factory = LayoutInflater.from(this);
		//
		// // Set game layout
		view = factory.inflate(R.layout.main, null);
		setContentView(view);

		// adadm
		// 할당 받은 clientId 설정
		AdConfig.setClientId("52cZ0QT12f26ebdb22");
		// Ad@m sdk 초기화 시작
		adView = (MobileAdView) findViewById(R.id.adview);
		adView.setAdListener(this);
		adView.setVisibility(View.VISIBLE);

		// cauly
		test = (RelativeLayout) findViewById(R.id.ad);
		test.setVisibility(View.INVISIBLE);

		//
		// Enable view key events
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);

	}

	// cauly
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// Hidden, Show
			if (test.getVisibility() == View.VISIBLE) {
				test.setVisibility(View.GONE);
				test.removeView(test);
			} else if (test.getVisibility() == View.GONE) {
				test.setVisibility(View.VISIBLE);
			}
		}
	};


	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		onStop();

		if (mActivityInPause) {
			return;
		}
		mActivityInPause = true;
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	private void clearApplicationCache(java.io.File dir) {
		if (dir == null)
			dir = getCacheDir();
		else
			;
		if (dir == null)
			return;
		else
			;
		java.io.File[] children = dir.listFiles();
		try {
			for (int i = 0; i < children.length; i++)
				if (children[i].isDirectory())
					clearApplicationCache(children[i]);
				else
					children[i].delete();
		} catch (Exception e) {
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		clearApplicationCache(null);
		
		//adam은 종료시, 광고 종료하도록 명시되어 있다.
		if (adView != null) {
			adView.destroy();
			adView = null;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//		
		if (!mActivityInPause) {
			return;
		}

		mActivityInPause = false;

	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		Log.e("Activity", "onConfigurationChanged()");
	}

	// adam
	@Override
	public void failedDownloadAd_AdListener(int errorno, String errMsg) {
		// fail to receive Ad
		//default로 아담을 띄우다가, 아담 광고 실패시 아담은 닫고, cauly를 띄운다.
		adView.setVisibility(View.INVISIBLE);
		test.setVisibility(View.VISIBLE);
	}
	
	public void didDownloadAd_AdListener() {
		Log.e("Activity", "didDownloadAd_AdListener()");
		if (!adView.isEnabled()) {
			adView.setEnabled(true);
		}
	}

}