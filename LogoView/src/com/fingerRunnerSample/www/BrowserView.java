package com.fingerRunnerSample.www;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class BrowserView extends Activity {
	private String url;
	WebView browser;
	private boolean mActivityInPause = true;

	String sdcardPath = "/sdcard/fingerRunnerSample/";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.gc();

		// 상단의 브라우져 프로그레스바
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		if (getParent() != null) {
			getParent().setProgressBarIndeterminateVisibility(true);
		}

		url = "http://allmine.co.kr/runner_sample/rank_board_m.html";

		setContentView(R.layout.web);

		browser = (WebView) findViewById(R.id.webkit);

		final Activity activity = this;
		browser.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				activity.setProgress(newProgress * 100);
			}
		});
		
		browser.getSettings().setJavaScriptEnabled(true);
		Log.v("url: ", url);
		browser.loadUrl(url); // url 인자로 브라우져 로드

		// 아래처럼 WebViewClient를 해주면, redirect를 하더라도 새창이 아닌 자신의 브라우져 webket안에서 띄우기
		// 때문에 back버튼을 한번만 눌러도 된다.
		browser.setWebViewClient(new WebView1Client());
	}

	private class WebView1Client extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!mActivityInPause) {
			return;
		}

		mActivityInPause = false;
		if (this.browser != null) {
			this.browser.resumeTimers();
		}

		browser.reload();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mActivityInPause) {
			return;
		}
		if (this.browser != null) {
			this.browser.pauseTimers();
		}
		mActivityInPause = true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private void clearApplicationCache(java.io.File dir) {
		if (dir == null)
			dir = getCacheDir();

		if (dir == null)
			return;

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
	}

}
