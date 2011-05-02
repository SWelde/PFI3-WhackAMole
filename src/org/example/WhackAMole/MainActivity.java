package org.example.WhackAMole;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Callback {
    
	private GameThread gameThread;
	private Handler handler;
	private GameView gameView;
	private TextView timeText;
	private TextView scoreText;
	private AlertDialog.Builder alertbox;
	private Vibrator vibrator;

	private boolean running;
	private double secondsLeft = 30;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.main);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        handler = new Handler(this);
        gameThread = new GameThread(handler);
        gameThread.start();
        
        gameView = (GameView) findViewById(R.id.view);
        timeText = (TextView) findViewById(R.id.timeText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        
        alertbox = new AlertDialog.Builder(this);
        
        alertbox.setNeutralButton("Play again", new DialogInterface.OnClickListener() {
 
                public void onClick(DialogInterface arg0, int arg1) {
                    newGame();
                }
            });
        
        newGame();
    }

	@Override
	public boolean handleMessage(Message arg0) {

		if(running){
			secondsLeft -= .1;
			timeText.setText( Integer.toString( (int) secondsLeft) );
			scoreText.setText( Integer.toString( (int) gameView.getScore()) );

			gameView.updateView();

			if(secondsLeft < 1){
				endGame();
			}
		}

		return false;
	}

	private void newGame()
	{
		gameView.newGame();
		secondsLeft = 20;
		running = true;
	}

	private void endGame()
	{
		running = false;
		alertbox.setMessage("Times up! You hit " + gameView.getScore() + " stars!");
		alertbox.show();
	}

}