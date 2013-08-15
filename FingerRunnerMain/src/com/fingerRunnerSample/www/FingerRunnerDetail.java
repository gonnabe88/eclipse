package com.fingerRunnerSample.www;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class FingerRunnerDetail extends GameEngine {
	// 휴대폰에 출력되는 게임 이름 설정
	public static final String NAME = "Finger Runner";

	private static final long UPDATE_DELAY = 50;
	private Context mContext;

	private Paint mBitmapPaint = new Paint();
	boolean bMan1;
	boolean bMan2;
	Bitmap man1;
	Bitmap man2;
	
	Bitmap red;
	Bitmap blue;
	
	
	//시간, 거리 관련 변수 선언
	private Paint mTextPaint = new Paint();
	private Paint tTextPaint = new Paint();
	long score;
	int runCount;
	long startTime;
	long calcuTime;
	int finalCount;
	long waitTime;
	
	/**
	 * 생성자
	 */
	public FingerRunnerDetail(Context context) {
		super(context);
		mContext = context;
		super.setUpdatePeriod(UPDATE_DELAY);
	}

	public FingerRunnerDetail(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		super.setUpdatePeriod(UPDATE_DELAY);
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			if(!ingame)
			{	
				ingame = true;	
				GameStart();
			}
			else
			{
				keyDown();
			}
		}
		return true;
		
	}
	
	boolean ingame = false;
	
	public boolean keyDown() 
	{
		if(ingame)
		{
			charMoveBool();
		}
		
		if(calcuTime >= 10)
		{
			gameOver();
		}
		
		return true;
	}

	public void gamePaint(Canvas g)
	{
		if(calcuTime >= 10)
		{
			gameOver();
			ingame = false;
		}
		if(ingame)
		{
			playGame(g);
		}
		else
		{
			showFirstScreen(g);
		}
	}
	
	public void playGame(Canvas c)
	{
		drawSprite(c);
		showTime(c);
	}
	
	public void showFirstScreen(Canvas canvas)
	{
		runCount = 0;
		startTime = System.currentTimeMillis();
		drawSpriteOver(canvas);
		showTime(canvas);
	}
	
	public void charMoveBool()
	{
		runCount++;
		if(bMan2 == true)
		{
			bMan2 = false;
			bMan1 = true;
		}
		else
		{
			bMan1 = false;
			bMan2 = true;
		}
	}
	
	public void drawSpriteOver(Canvas canvas)
	{
		playGame(canvas);
	}
	
	public void showTime(Canvas goff)
	{
		String s = "meter:" + Integer.toString(runCount)+" M";
		goff.drawText(s, 80, 80, mTextPaint);
		
		long nowTime = System.currentTimeMillis();
		
		calcuTime = nowTime - startTime;
		finalCount = runCount;
		calcuTime = calcuTime / 1000;
		
		if(finalCount < 1)
		{
			waitTime = calcuTime;
			calcuTime = 0;
		}
		else
		{
			calcuTime = calcuTime - waitTime;
		}
		
		String sTime = "time: " + Long.toString(calcuTime);
		goff.drawText(sTime, 80,  120,  tTextPaint);
		
		if(calcuTime >= 10)
		{
			gameOver();
		}
	}
	
	public void drawSprite(Canvas canvas)
	{
		if(bMan2==true)
		{
			canvas.drawBitmap(man1, 120,  150, mBitmapPaint);
		}
		else
		{
			canvas.drawBitmap(man2,  120,  150, mBitmapPaint);
		}
		
		canvas.drawBitmap(red, 50, 320, mBitmapPaint);
		canvas.drawBitmap(blue, 180, 320, mBitmapPaint);
		
	}
	
	/**
	 * onDraw
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		drawSprite(canvas);
	}


	@Override
	protected void updateSprites() {
	}


	@Override
	protected long getScore() {
		
		// TODO Auto-generated method stub
		return score;
	}


	public void init() {
		
		bMan1 = true;
		bMan2 = false;
		
		man1 = getImage(R.drawable.man1);
		man2 = getImage(R.drawable.man2);
		
		red = getImage(R.drawable.red);
		blue = getImage(R.drawable.blue);
		// TODO Auto-generated method stub
		
		//시간, 거리 기록
		runCount = 0;
		startTime = System.currentTimeMillis();
		finalCount = 0;
		waitTime = 0;
		
		mTextPaint.setARGB(255, 0, 0,0);
		mTextPaint.setTextSize(35);
		
		tTextPaint.setARGB(255,0,0,0);
		tTextPaint.setTextSize(35);
		
	}

	
	
	public void GameStart()
	{
		score = 0;
	}
	
	public void GameOver()
	{
		ingame = false;
	}
	
	@Override
	protected boolean gameOver() {
		// TODO Auto-generated method stub
		return ingame;
	}

}
