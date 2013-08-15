package com.fingerRunnerSample.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class FingerRunnerDetail extends GameEngine {
	String sdcardPath = "/sdcard/fingerRunnerSample/";

	// 휴대폰에 출력되는 게임 이름 설정
	public static final String NAME = "Finger Runner";

	// Refresh rate (ms)
	private static final long UPDATE_DELAY = 50;

	private Context mContext;

	private Paint mBitmapPaint = new Paint();
	boolean bMan1;
	boolean bMan2;
	boolean ingame = false;

	// -----------------------------------------
	// 스프라이트 이미지 선언
	// -----------------------------------------
	Bitmap man1; // 달리는 캐릭터 sprite1
	Bitmap man2; // 달리는 캐릭터 sprite2
	Bitmap red; // 빨간 동그라미
	Bitmap blue; // 파란 동그라미

	// -----------------------------------------
	// 시간과 거리 관련 선언
	// -----------------------------------------
	private Paint mTextPaint = new Paint(); // meter
	private Paint tTextPaint = new Paint(); // time
	long score; // 점수(핑거러너에서는 시간)
	int runCount;
	long startTime;
	long calcuTime;
	int finalCount; // 최종달린거리
	long waitTime; // 대기시간

	int x, y, mousex, mousey, oldx, oldy, dx = 0, dy = 0, count, shield = 0;
	// Game Stuff
	final int borderwidth = 0;
	final int sxsize = 90, sysize = 39, sxfire = 11, syfire = 6;
	final int movex = 10, movey = 5;
	final int scoreheight = 45;
	final int screendelay = 300;
	Bitmap coin;
	Bitmap machine;
	Bitmap board;
	Bitmap arrow;

	// db와 연동
	private Paint rTextPaint = new Paint(); // rank
	private Paint resultTextPaint = new Paint(); // rank
	private Paint board1TextPaint = new Paint(); // board
	private Paint board2TextPaint = new Paint(); // board

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

	/**
	 * onDraw
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		gamePaint(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// tx와 ty는 터치된 좌표
		int tx = (int) event.getX();
		int ty = (int) event.getY();

		// 터치된 좌표가 움직일 객체 주변에 위치한다면 움직일 좌표를 계산한다.
		if (tx >= x && tx <= x + coin.getWidth() && ty >= y
				&& ty <= y + coin.getHeight()) {
			x = tx - (coin.getWidth() / 2);
			y = ty - (coin.getHeight() / 2);

		}
		// else start game if not already started
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (!ingame) {
				ingame = true;
				GameStart();
			} else {
				keyDown();
			}
		}
		return true;
	}

	public void init() {
		bMan1 = true;
		bMan2 = false;

		man1 = getImage(R.drawable.man1);
		man2 = getImage(R.drawable.man2);

		blue = getImage(R.drawable.blue);
		red = getImage(R.drawable.red);

		// 시간 및 거리기록
		runCount = 0;
		startTime = System.currentTimeMillis();
		finalCount = 0;
		waitTime = 0;

		// 거리기록 Text Paints
		mTextPaint.setARGB(255, 0, 0, 0);
		mTextPaint.setTextSize(35); // 글자 크기 지정

		// 시간기록 Text Paints
		tTextPaint.setARGB(255, 0, 0, 0);
		tTextPaint.setTextSize(35); // 글자 크기 지정

		// db와 연동
		rTextPaint.setARGB(255, 0, 0, 0); // rank
		resultTextPaint.setARGB(255, 0, 0, 0); // db result rank

		board1TextPaint.setARGB(255, 255, 255, 255); // board1
		board1TextPaint.setTextSize(23); // 글자 크기 지정
		board2TextPaint.setARGB(255, 255, 255, 255); // board2
		board2TextPaint.setTextSize(23); // 글자 크기 지정

		coin = getImage(R.drawable.dollar100);
		machine = getImage(R.drawable.machine66);
		board = getImage(R.drawable.board280);
		arrow = getImage(R.drawable.arrow2);

	}

	/**
	 * Coin이 오락기에 입력되는 장면
	 * 
	 * @param canvas
	 */
	public void drawCoinInput(Canvas canvas) {

		// X, Y
		canvas.drawBitmap(arrow, 100, 30, mBitmapPaint); // 화살표
		canvas.drawBitmap(machine, 240, 5, mBitmapPaint); // 오락기
		canvas.drawBitmap(coin, x, y, mBitmapPaint); // paint ship 120,150

		// gameover인 상태에서 조금이라도 캐릭이 움직이면 시작하자
		if (x > 200) {
			x = 0;
			y = 0;
			playGame(canvas);
		}
	}

	/**
	 * 화면 터치 이벤트
	 */
	public boolean keyDown() {
		if (ingame) {

			charMoveBool(); // 스프라이트를 움직이기위한 boolean설정

			// 시간이 10초를 넘기면 게임 종료
			if (calcuTime >= 10) {
				gameOver();
			}
		}

		return true;
	}

	public void gamePaint(Canvas g) {
		// 시간이 10초를 넘기면 게임 종료
		if (calcuTime >= 10) {
			gameOver();
			ingame = false;
		}
		if (ingame) {
			playGame(g);
		} else {
			showFirstScreen(g);
		}
	}

	public void playGame(Canvas c) {
		drawSprite(c);
		showTime(c);
	}

	/**
	 * 게임 시작 화면
	 */
	public void showFirstScreen(Canvas canvas) {

		runCount = 0; // 미터 초기화
		startTime = System.currentTimeMillis(); // 달린길이 초기화

		// 달린 기록을 보여준다.
		String finalRecord = "Score: " + Integer.toString(finalCount) + " M";
		canvas.drawText(finalRecord, 80, 140, tTextPaint);

		// --------------------------------------------------------------------------
		// write my best
		// --------------------------------------------------------------------------
		String filePath = sdcardPath + "myLocalRankingSample.txt";
		String beforeRank = readFile(filePath, "myLocalRankingSample.txt");
		Log.v("rank", "beforeRank :" + beforeRank);
		Log.v("rank", "finalRank :" + Integer.toString(finalCount));
		String result = null;
		if (!"".equals(beforeRank)
				&& (Integer.parseInt(beforeRank) < finalCount)) {
			Log.v("rank", "your rank exceeded");
			saveFile(Integer.toString(finalCount), "myLocalRankingSample.txt");

			try {
				// name.txt를 읽어서 null이 아니면, db에 함께 보낸다.
				String nameFilePath = sdcardPath + "nameSample.txt";
				String nameResult = readFile(nameFilePath, "nameSample.txt");

				// 20110720 myLocalPhone.txt 를 읽어서 null이 아니면, db에 함께 보낸다.
				String phoneFilePath = sdcardPath + "myLocalPhoneSample.txt";
				String phoneResult = readFile(phoneFilePath,
						"myLocalPhoneSample.txt");

				if ((!"0".equals(nameResult)) && (!"".equals(nameResult))) {
					if ((!"0".equals(phoneResult)) && (!"".equals(phoneResult))) {
						result = sendData(Integer.toString(finalCount) + "_"
								+ nameResult + "_" + phoneResult);
					} else {
						result = sendData(Integer.toString(finalCount) + "_"
								+ nameResult);
					}
				} else {
					// 이때 한번만 db에 다녀온다.
					result = sendData(Integer.toString(finalCount));
				}
				// result 값을 파일에 쓰기
				saveFile(result, "resultSample.txt");
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Log.v("rank", "your rank didn't satisfied");
		}

		// 1.자기 최고 기록일때의 meter거리_2.자기 최고 기록일때의 세계 순위_3.세계 1등의 meter 수
		String rank = "your best is " + beforeRank + " meters";
		canvas.drawText(rank, 20, 260, rTextPaint);

		String dbRankFilePath = sdcardPath + "resultSample.txt";
		String dbRankResult = readFile(dbRankFilePath, "resultSample.txt");

		if (!"0".equals(dbRankResult)) {
			// db에서 돌아온 값의 형식:
			// 60_1_10
			String[] tokens = null;
			tokens = dbRankResult.split("_");

			int max = 3;
			String myBestMeter = null;
			String myBestRank = null;
			String worldRank = null;
			for (int i = 0; i <= max; i++) {
				// 예외처리
				if (i > (tokens.length - 1)) {
					break;
				}
				// i에 해당하는 토큰 받아오기
				String tokenString = tokens[i].trim();
				switch (i) {
				case (0):
					myBestMeter = tokenString;
					break;
				case (1):
					myBestRank = tokenString;
					break;
				case (2):
					worldRank = tokenString;
					break;
				default:
					break;
				}
			}

			if (worldRank == null) {
				worldRank = "";
			}
			if (myBestRank == null) {
				myBestRank = "";
			}

			String dbResult = "your best " + myBestMeter
					+ "M is world ranking " + myBestRank + "th(world 1th:"
					+ worldRank + "M)";
			canvas.drawText(dbResult, 20, 280, resultTextPaint);

			// board1
			canvas.drawBitmap(board, 20, 290, mBitmapPaint);

			String board1 = "yours " + myBestMeter + " M/Rank " + myBestRank
					+ "th";
			canvas.drawText(board1, 33, 330, board1TextPaint);

			String board2 = "world Best " + worldRank + "M";
			canvas.drawText(board2, 33, 380, board2TextPaint);

		}

		drawCoinInput(canvas);
	}

	private String sendData(String my_meter) throws ClientProtocolException,
			IOException {
		// TODO Auto-generated method stub
		HttpPost request = makeHttpPost(my_meter,
				"http://allmine.co.kr/runner_sample/rank_name.php");
		HttpClient client = new DefaultHttpClient();
		ResponseHandler<String> reshandler = new BasicResponseHandler();
		String result = client.execute(request, reshandler);
		return result;
	}

	// Post 방식일경우
	private HttpPost makeHttpPost(String my_meter, String url) {
		// TODO Auto-generated method stub
		HttpPost post = new HttpPost(url);
		Vector<NameValuePair> params = new Vector<NameValuePair>();
		params.add(new BasicNameValuePair("my_meter", my_meter));
		post.setEntity(makeEntity(params));
		return post;
	}

	private HttpEntity makeEntity(Vector<NameValuePair> params) {
		HttpEntity result = null;
		try {
			result = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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

	// read file 함수
	// fileNaeme 형식 예시 : a.txt
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
		// 파일 자체가 존재하지 않으면 0이라는 내용이 담긴 빈파일을 생성한다.
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

	public void charMoveBool() {
		runCount++;
		if (bMan2 == true) {
			bMan2 = false;
			bMan1 = true;
		} else {
			bMan1 = false;
			bMan2 = true;
		}
	}

	public void drawSprite(Canvas canvas) {

		// 스프라이트를 번갈아 화면에 출력한다
		// bMan2가 true이면 이제 man1을 출력할차례임
		if (bMan2 == true) {
			canvas.drawBitmap(man1, 120, 150, mBitmapPaint); // man1을 120,150
																// 좌표에 출력
		} else {
			canvas.drawBitmap(man2, 120, 150, mBitmapPaint);
		}

		// 버튼 출력
		canvas.drawBitmap(red, 50, 320, mBitmapPaint); // red button 50,320
		canvas.drawBitmap(blue, 180, 320, mBitmapPaint); // blue button
	}

	public void drawSpriteOver(Canvas canvas) {
		playGame(canvas);
	}

	/**
	 * 시간(점수) 출력
	 */
	public void showTime(Canvas goff) {

		String s = "meter: " + Integer.toString(runCount) + " M";
		goff.drawText(s, 80, 80, mTextPaint);

		// time
		long nowTime = System.currentTimeMillis(); // 현재시간

		calcuTime = nowTime - startTime;
		finalCount = runCount; // meter
		calcuTime = calcuTime / 1000;

		// 대기시간 계산
		// 아직 1m 도 달리지 않았다면 시간이 흐르지 않는다
		if (finalCount < 1) {
			waitTime = calcuTime;
			calcuTime = 0;
		} else {
			calcuTime = calcuTime - waitTime;
		}

		String sTime = "time: " + Long.toString(calcuTime);
		goff.drawText(sTime, 80, 120, tTextPaint);

		// 10초 달리기
		if (calcuTime >= 10) {
			gameOver();
		}
	}

	// Game Start
	public void GameStart() {
		score = 0;
	}

	// Game Over
	public void GameOver() {
		ingame = false;
	}

	@Override
	protected void updateSprites() {
	}

	@Override
	protected boolean gameOver() {
		return ingame;
	}

	@Override
	protected long getScore() {
		return score;
	}
}
