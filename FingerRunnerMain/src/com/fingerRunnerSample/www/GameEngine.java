package com.fingerRunnerSample.www;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class GameEngine extends LinearLayout
{
	private Context ctx;
	private Timer updateTimer;
	private long timePeriod = 1000; //화면 갱신 주기
	
	/**
	 * @param context
	 */
	public GameEngine(Context context) {
		super(context);
		ctx = context;
	}
	public GameEngine(Context context, AttributeSet attrs) {
		super(context, attrs);
		ctx = context;
	}

	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		try {
			init(); //Init game
			startUpdater();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 이미지 로드 함수
	 */
	protected Bitmap getImage(int id) {
		return BitmapFactory.decodeResource(ctx.getResources(), id);
	}

	/**
	 * 게임에 사용될 추상 함수 선언
	 * 
	 */
	// 스프라이트 업데이트
	abstract protected void updateSprites();
	
	// 게임 초기화
	abstract protected void init();
	abstract protected boolean gameOver();
	abstract protected long getScore();
	
	/**
	 * 화면 갱신 함수
	 */
	private class Updater extends TimerTask {

		@Override
		public void run() {
			updateSprites();
			
			/**
			 * 일반적인 Invalidate() 메소드는 새롭게 View 화면을
* 갱신하고자 할때 사용하는 메소드 
			* 그러나 해당 UI쓰레드가 아닌 다른 별도의 쓰레드에서 
* 화면갱신을 하고자 Invalidate()를 사용하면
			 * 오류가 나는데 이럴경우는 postInvalidate()를 사용하면 된다	
			 * */
			postInvalidate();
		}

	}
	
	public void setUpdatePeriod(long period) {
		timePeriod = period;
	}
	
	protected void startUpdater() {
		updateTimer = new Timer();
		updateTimer.schedule(new Updater(), 0, timePeriod);
	}

	protected void stopUpdater() {
		if (updateTimer != null) {
			updateTimer.cancel();
		}
	}
	
	public void halt() {
		stopUpdater();
	}

	public void resume() {
		init();
		startUpdater();
	}
	
	public Context getContex() {
		return ctx;
	}
}
