package org.example.WhackAMole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class GameView extends View {

	private int y = 0;
	private Bitmap flyAvatar;
	private Bitmap background;
	private int score;
	private int screenWidth;
	private int screenHeight;

	private Mole[] flys;

	private Vibrator vibrator;

	public GameView(Context context) {
		super(context);
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);

		flys = new Mole[2];

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();

		for(int i = 0; i < flys.length; i ++){
			flys[i] = new Mole(screenWidth, screenHeight, this);
		}


		flyAvatar = BitmapFactory.decodeResource(getResources(), R.drawable.fly);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.background);

		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawBitmap(background, 0, 0, null);

		for(int i = 0; i < flys.length; i ++){
			if(flys[i].getVisibility()){
				canvas.drawBitmap(flyAvatar, flys[i].getX(), flys[i].getY(), null);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		for(int i = 0; i < flys.length; i ++){
			if(flys[i].isHit(event.getX(), event.getY(), flyAvatar.getWidth(), flyAvatar.getHeight())){
				score ++;
				vibrator.vibrate(200);
			}
		}
		return super.onTouchEvent(event);
	}

	public void updateView()
	{
		for(int i = 0; i < flys.length; i ++){
			flys[i].update();
		}
	}

	public void invalidateView()
	{
		invalidate();
	}

	public void newGame()
	{
		score = 0;
	}

	public int getScore()
	{
		return score;
	}


}