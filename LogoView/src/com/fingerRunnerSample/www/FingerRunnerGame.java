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

import com.fingerRunnerSample.www.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class FingerRunnerGame extends ArcadeGame {
	// Game name
	public static final String NAME = "Finger Runner";
	String sdcardPath = "/sdcard/fingerRunnerSample/";

	// Refresh rate (ms)
	private static final long UPDATE_DELAY = 40;

	private Context mContext;

	// For text
	private Paint mTextPaint = new Paint();
	private Paint tTextPaint = new Paint(); // time
	private Paint gTextPaint = new Paint(); // guide
	private Paint rTextPaint = new Paint(); // rank
	private Paint resultTextPaint = new Paint(); // rank
	private Paint board1TextPaint = new Paint(); // board
	private Paint board2TextPaint = new Paint(); // board

	// For Bitmaps
	private Paint mBitmapPaint = new Paint();
	boolean bShip1;
	boolean bShip2;
	int runCount;
	long startTime;
	long calcuTime;
	int finalCount; // 최종달린수
	long waitTime; // 대기시간

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public FingerRunnerGame(Context context) {
		super(context);
		mContext = context;
		super.setUpdatePeriod(UPDATE_DELAY);
	}

	public FingerRunnerGame(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		super.setUpdatePeriod(UPDATE_DELAY);
	}

	/**
	 * Android Key events
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		keyUp(keyCode); // event,
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		keyDown(keyCode); // event,
		return true;
	}

	/**
	 * Main Draw Sub
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		paint(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int tx = (int) event.getX();
		int ty = (int) event.getY();

		// Has the ship been touched. if so move it
		if (tx >= x && tx <= x + ship.getWidth() && ty >= y && ty <= y + ship.getHeight()) {
			x = tx - (ship.getWidth() / 2);
			y = ty - (ship.getHeight() / 2);

		}
		// else start game if not already started
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (!ingame) {
				ingame = true;
				GameStart();
			} else {
				// fire gun
				keyDown(KeyEvent.KEYCODE_SPACE);
			}
		}
		return true;
	}

	/*************************************************************
	 * GAME Code
	 ************************************************************/
	boolean ingame = false;

	int x, y, mousex, mousey, oldx, oldy, dx = 0, dy = 0, count, shield = 0;
	boolean showtitle = true;
	Bitmap ship;
	Bitmap ship2;
	Bitmap ship3;
	Bitmap machine;
	Bitmap board;
	Bitmap arrow;
	Bitmap red;
	Bitmap blue;

	long score;

	// Game Stuff
	final int borderwidth = 0;
	final int sxsize = 90, sysize = 39, sxfire = 11, syfire = 6;
	final int movex = 10, movey = 5;
	final int scoreheight = 45;
	final int screendelay = 300;

	/**
	 * Overload to initialize game elements
	 */
	public void initialize() {
		int n;
		bShip1 = true;
		bShip2 = false;
		runCount = 0;
		startTime = System.currentTimeMillis();
		finalCount = 0;
		waitTime = 0;

		// Screen size
		int width = getWidth();
		int height = getHeight();

		// Text Paints
		mTextPaint.setARGB(255, 0, 0, 0);
		mTextPaint.setTextSize(35); // 글자 크기 지정

		tTextPaint.setARGB(255, 0, 0, 0);
		tTextPaint.setTextSize(35); // 글자 크기 지정

		gTextPaint.setARGB(255, 0, 0, 0); // guide
		rTextPaint.setARGB(255, 0, 0, 0); // rank
		resultTextPaint.setARGB(255, 0, 0, 0); // db result rank

		board1TextPaint.setARGB(255, 255, 255, 255); // board1
		board1TextPaint.setTextSize(23); // 글자 크기 지정
		board2TextPaint.setARGB(255, 255, 255, 255); // board2
		board2TextPaint.setTextSize(23); // 글자 크기 지정

		ship = getImage(R.drawable.man1);
		ship2 = getImage(R.drawable.man2);
		ship3 = getImage(R.drawable.dollar100);
		machine = getImage(R.drawable.machine66);
		board = getImage(R.drawable.board280);
		arrow = getImage(R.drawable.arrow2);
		blue = getImage(R.drawable.blue);
		red = getImage(R.drawable.red);

	}


	/**
	 * Process key down event
	 * 
	 * @param key
	 * Android Key code
	 * @return
	 */
	public boolean keyDown(int key) {
		if (ingame) {
			mousex = -1;
			if (key == KeyEvent.KEYCODE_DPAD_LEFT || key == KeyEvent.KEYCODE_Q)
				dx = -1;
			if (key == KeyEvent.KEYCODE_DPAD_RIGHT || key == KeyEvent.KEYCODE_W)
				dx = 1;
			if (key == KeyEvent.KEYCODE_DPAD_UP || key == KeyEvent.KEYCODE_O)
				dy = -1;
			if (key == KeyEvent.KEYCODE_DPAD_DOWN || key == KeyEvent.KEYCODE_L)
				dy = 1;
			if ((key == KeyEvent.KEYCODE_SPACE) || (key == 23)) {
				chaMove();
				// 50미터 달리기
				if (calcuTime >= 10) {
					gameOver();
				}
			}
		} else {
			if (key == KeyEvent.KEYCODE_S) {
			}
		}
		if (key == KeyEvent.KEYCODE_E) {
			ingame = false;
		}

		if (key == KeyEvent.KEYCODE_Q) {
			// Arggg!! There should be a better wayt to quit!
			System.exit(0);
		}
		return true;
	}

	/**
	 * Process key up event
	 * 
	 * @param e
	 * Key event
	 * @param key
	 * key code
	 * @return
	 */
	public boolean keyUp(int key) { // KeyEvent e,
		if (key == KeyEvent.KEYCODE_DPAD_LEFT || key == KeyEvent.KEYCODE_DPAD_RIGHT || key == KeyEvent.KEYCODE_Q || key == KeyEvent.KEYCODE_W)
			dx = 0;
		if (key == KeyEvent.KEYCODE_DPAD_UP || key == KeyEvent.KEYCODE_DPAD_DOWN || key == KeyEvent.KEYCODE_O || key == KeyEvent.KEYCODE_L)
			dy = 0;
		return true;
	}

	public void paint(Canvas g) {
		if (calcuTime >= 10) {
			gameOver();
			ingame = false;
		}
		if (ingame) {
			playGame(g);
		} else {
			showIntroScreen(g);
		}
	}

	public void playGame(Canvas c) {
		moveShip();
		drawPlayField(c);

		showScore(c);
		score += 100;
		
	}

	/**
	 * 게임 오버 화면
	 * 인트로가 될 수도 있다.
	 * 
	 * @param canvas
	 */
	public void showIntroScreen(Canvas canvas) {
		String s;
		int width = getWidth();
		int height = getHeight();

		runCount = 0; // 미터 초기화
		startTime = System.currentTimeMillis(); // 달린길이 초기화
		s = null;

		if (finalCount > 0) {
			s = "You Finished";
		} else {
			s = "to run, touch!";
		}

		canvas.drawText(s, (width - (s.length() * mTextPaint.getTextSize() / 2)) / 2, (height - scoreheight - borderwidth) / 2, mTextPaint);
		count--;
		if (count <= 0) {
			count = screendelay;
			//showtitle = !showtitle;
		}

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
		if (!"".equals(beforeRank)&& (Integer.parseInt(beforeRank) < finalCount)) {
			Log.v("rank", "your rank exceeded");
			saveFile(Integer.toString(finalCount), "myLocalRankingSample.txt");

			try {
				// name.txt를 읽어서 null이 아니면, db에 함께 보낸다.
				String nameFilePath = sdcardPath + "nameSample.txt";
				String nameResult = readFile(nameFilePath, "nameSample.txt");

				// 20110720 myLocalPhone.txt 를 읽어서 null이 아니면, db에 함께 보낸다.
				String phoneFilePath = sdcardPath + "myLocalPhoneSample.txt";
				String phoneResult = readFile(phoneFilePath, "myLocalPhoneSample.txt");

				if ((!"0".equals(nameResult)) && (!"".equals(nameResult))) {
					if ((!"0".equals(phoneResult)) && (!"".equals(phoneResult))) {
						result = sendData(Integer.toString(finalCount) + "_" + nameResult+ "_" + phoneResult);
					} else {
						result = sendData(Integer.toString(finalCount) + "_" + nameResult);
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

			if(worldRank==null){
				worldRank  = "";
			}
			if(myBestRank==null){
				myBestRank ="";
			}
			
			String dbResult = "your best " + myBestMeter + "M is world ranking " + myBestRank + "th(world 1th:" + worldRank + "M)";
			canvas.drawText(dbResult, 20, 280, resultTextPaint);

			// board1
			canvas.drawBitmap(board, 20, 290, mBitmapPaint);

			
			String board1 = "yours " + myBestMeter + " M/Rank " + myBestRank + "th";
			canvas.drawText(board1, 33, 330, board1TextPaint);

			String board2 = "world Best " + worldRank + "M";
			canvas.drawText(board2, 33, 380, board2TextPaint);

		}
		// to srtart
		s = "to start game, put coin here ----> .";
		canvas.drawText(s, 100, 100, gTextPaint);

		moveShip();
		drawPlayFieldOver(canvas);
	}

	private String sendData(String my_meter) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		HttpPost request = makeHttpPost(my_meter, "http://allmine.co.kr/runner_sample/rank_name.php");
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

	// wifi : 1, 3G: 0, checkbox on : 9
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

	public void chaMove() {
		runCount++;
		if (bShip2 == true) {
			bShip2 = false;
			bShip1 = true;
		} else {
			bShip1 = false;
			bShip2 = true;
		}
	}

	public void drawPlayField(Canvas canvas) {
		if (bShip2 == true) {
			canvas.drawBitmap(ship, 120, 150, mBitmapPaint); // paint ship
																// 120,150
		} else {
			canvas.drawBitmap(ship2, 120, 150, mBitmapPaint); // paint ship
		}

		canvas.drawBitmap(red, 50, 320, mBitmapPaint); // red button 50,320

		canvas.drawBitmap(blue, 180, 320, mBitmapPaint); // blue button
	}

	public void drawPlayFieldOver(Canvas canvas) {

		// X, Y
		canvas.drawBitmap(arrow, 100, 30, mBitmapPaint); // 화살표
		canvas.drawBitmap(machine, 240, 5, mBitmapPaint); // 오락기
		canvas.drawBitmap(ship3, x, y, mBitmapPaint); // paint ship 120,150

		// gameover인 상태에서 조금이라도 캐릭이 움직이면 시작하자
		if (x > 200) {
			x = 0;
			y = 0;
			playGame(canvas);
		}
	}

	/**
	 * 
	 * 게임 화면
	 * Show the laser shield and score values in the lower part of the screen
	 * 
	 * @param goff
	 */
	public void showScore(Canvas goff) {
		String s;
		int my;
		int height = getHeight();

		s = "meter: " + Integer.toString(runCount) + " M";
		goff.drawText(s, 80, 80, mTextPaint);

		// time
		long nowTime = System.currentTimeMillis(); // 현재시간

		calcuTime = nowTime - startTime;
		finalCount = runCount; // meter
		calcuTime = calcuTime / 1000;

		// 대기시간 계산
		// 아직 1m 도 달리지 않았다면 시간 계산하지 않는다.
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
			// X, Y
			gameOver();
		}
	}

	public void moveShip() {
		int width = getWidth();
		int height = getHeight();

		int xx, yy;

		oldx = x;
		oldy = y;

		xx = mousex;
		if (xx > 0) {
			yy = mousey;
			if (xx < x)
				dx = -1;
			if (xx > x + sxsize)
				dx = 1;
			if (yy < y)
				dy = -1;
			if (yy > y + sysize)
				dy = 1;
			if (xx > x && xx < x + sxsize && yy > y && yy < y + sysize) {
				dx = 0;
				dy = 0;
				mousex = -1;
			}
		}

		x += dx * movex;
		y += dy * movey;

		if (y <= borderwidth || y >= (height - sysize - scoreheight)) {
			dy = 0;
			y = oldy;
		}
		if (x >= (width - borderwidth - sxsize) || x <= borderwidth) {
			dx = 0;
			x = oldx;
		}

	}


	public Paint newColor() {
		int[] rgb;
		int t;
		rgb = new int[3];
		for (int i = 0; i < 3; i++)
			rgb[i] = 0;
		t = (int) (Math.random() * 3);
		rgb[t] = (int) (Math.random() * 128 + 1) + 127;
		Paint p = new Paint();
		p.setARGB(255, rgb[0], rgb[1], rgb[2]);
		return p;
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
	protected void updatePhysics() {
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
