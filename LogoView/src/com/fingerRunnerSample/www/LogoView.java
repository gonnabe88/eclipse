package com.fingerRunnerSample.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import net.daum.mobilead.AdConfig;
import net.daum.mobilead.AdHttpListener;
import net.daum.mobilead.MobileAdView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 메인 로고 화면이 출력된다.
 */
public class LogoView extends Activity implements AdHttpListener {
	// cauly
	public RelativeLayout test;
	
	String sdcardPath = "/sdcard/fingerRunnerSample/";
	Intent intent = null;
	private EditText inputAddress = null;
	boolean start_flag = false;
	String text = null;
	// WebView mWeb;
	private boolean mActivityInPause = true;

	/** adam Called when the activity is first created. */
	private MobileAdView adView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.gc();

		setContentView(R.layout.view);

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

		// 메인 로고 이미지 로딩
		ImageView loadingIv = (ImageView) findViewById(R.id.logo);
		BitmapDrawable bitmapDrawableLogo = (BitmapDrawable) loadingIv
				.getResources().getDrawable(R.drawable.loading);
		loadingIv.setImageDrawable(bitmapDrawableLogo);

		// text
		TextView tv = (TextView) findViewById(R.id.explain);
		tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		tv.setTextSize(20);
		tv.setTextColor(0xff000000); // black
		String contents = "Run!!!! As fast as you can, for 10 Seconds, with your fingers.";
		tv.setText(contents);

		// on your mark
		ImageView markIv = (ImageView) findViewById(R.id.mark);
		BitmapDrawable bitmapDrawableMark = (BitmapDrawable) markIv
				.getResources().getDrawable(R.drawable.onyourmark100);
		markIv.setImageDrawable(bitmapDrawableMark);
		// mark touch
		markIv.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					start_flag = true;
					intent = new Intent(getApplicationContext(),
							FingerRunnerMain.class);
					startActivity(intent);
					return true; // 액션에서 return true를 해줘야 다음 액션을 받을 수 있음.
				} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
					return true;
				}
				return true;
			}
		});

		// mark text
		TextView tv_mark = (TextView) findViewById(R.id.mark_explain);
		tv_mark.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		tv_mark.setTextSize(15);
		tv_mark.setTextColor(0xff000000); // black
		String contents_mark = "[Click Start Block] On your mark!";
		tv_mark.setText(contents_mark);

		// 이름 입력하기
		SharedPreferences pref = getSharedPreferences("pref",
				Activity.MODE_PRIVATE);
		inputAddress = (EditText) findViewById(R.id.inputAddr);

		// 저장된 값들을 불러옵니다.
		text = pref.getString("editText", "");
		inputAddress.setText(text);

		// name text
		TextView tv_name = (TextView) findViewById(R.id.ok);
		tv_name.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		tv_name.setTextSize(15);
		tv_name.setTextColor(0xff000000); // black
		String contents_ok = "input name";
		tv_name.setText(contents_ok);

		// 기네스북 이미지 로딩
		ImageView bookIv = (ImageView) findViewById(R.id.book);
		BitmapDrawable bitmapDrawableBook = (BitmapDrawable) bookIv
				.getResources().getDrawable(R.drawable.book100);
		bookIv.setImageDrawable(bitmapDrawableBook);
		// book touch
		bookIv.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					intent = new Intent(getApplicationContext(),
							BrowserView.class);
					intent.putExtra("result", ""); // 인식결과
					startActivity(intent);
					return true; // 액션에서 return true를 해줘야 다음 액션을 받을 수 있음.
				} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
					return true;
				}
				return true;
			}
		});

		// text
		TextView tv_book = (TextView) findViewById(R.id.book_explain);
		tv_book.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		tv_book.setTextSize(15);
		tv_book.setTextColor(0xff000000); // black
		String contents_book = "Click book to see rank";
		tv_book.setText(contents_book);

		// 일정시간 후에 Intent를 보낸다.
		Handler x = new Handler();
		if (start_flag == false) {
			x.postDelayed(new splashhandler(), 12000);
		}
	}

	public void onStop() { // 어플리케이션이 화면에서 사라질때
		super.onStop();
		SharedPreferences pref = getSharedPreferences("pref",
				Activity.MODE_PRIVATE); // UI 상태를 저장합니다.
		SharedPreferences.Editor editor = pref.edit(); // Editor를 불러옵니다.

		inputAddress = (EditText) findViewById(R.id.inputAddr);
		editor.putString("editText", inputAddress.getText().toString());
		editor.commit(); // 저장합니다.

		// 파일에도 이름을 무조건 쓴다.
		// result 값을 파일에 쓰기
		saveFile(inputAddress.getText().toString(), "nameSample.txt");
	}

	// 스플레쉬
	public class splashhandler implements Runnable {
		public void run() {
			Intent intent = new Intent(getApplicationContext(),
					FingerRunnerMain.class);
			if (start_flag == false) {
				startActivity(intent);
			}
		}
	}

	public void saveFile(String input, String fileName) {
		String filePath = sdcardPath;

		// directory 존재여부 체크 한번
		File dir = new File(sdcardPath);
		if (!dir.exists()) {
			dir.mkdir();
		}

		String fileFullPath = filePath + fileName;
		File f = new File(fileFullPath);
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			fw.write(input);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// read file
	// fileNaeme : myLocalRanking.txt
	public String readFile(String path, String fileName) {
		String filePath = sdcardPath;

		// directory 존재여부 체크 한번
		File dir = new File(sdcardPath);
		if (!dir.exists()) {
			Log.v("file", "file dir not exist");
			dir.mkdir();
			saveFile("0", fileName);
		} else {
			Log.v("file", "file dir exist");
		}
		// 파일 자체가 존재하지 않아도 다녀온다.
		File f = new File(path);
		if (!f.exists()) {
			Log.v("file", "file not exist");
			saveFile("0", fileName);
		} else {
			Log.v("file", "file is exist");
		}

		StringBuffer sb = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(path);
			int n;
			while ((n = fis.available()) > 0) {
				byte b[] = new byte[n];
				if (fis.read(b) == -1)
					break;
				sb.append(new String(b));
			}
			fis.close();
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!mActivityInPause) {
			return;
		}

		mActivityInPause = false;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mActivityInPause) {
			return;
		}

		mActivityInPause = true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	// adam
	@Override
	public void failedDownloadAd_AdListener(int errorno, String errMsg) {
		// fail to receive Ad
		// default로 아담을 띄우다가, 아담 광고 실패시 아담은 닫고, cauly를 띄운다.
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
