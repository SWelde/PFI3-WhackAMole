package org.example.WhackAMole;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GameThread extends Thread {

	private Handler handler;
	private MainActivity parent;
	private boolean running;

	public static final int DELAY = 100;

	public GameThread(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {

		if(running){
			super.run();

			while(true){
			try {
				Thread.sleep(DELAY);
				handler.sendMessage(handler.obtainMessage());
				//Log.i("thread", "running");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
		}
	}

	@Override
	public synchronized void start() {
		super.start();
		running = true;

		Log.i("thread", "started");
	}

}
