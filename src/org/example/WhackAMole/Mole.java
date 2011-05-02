package org.example.WhackAMole;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class Mole {

	private int x;
	private int y;

	private int invisibleTime;
	private int visibleTime;

	private int screenWidth;
	private int screenHeight;

	private final int MARGIN = 100;

	private boolean visible;

	private GameView parent;

	public Mole(int screenWidth, int screenHeight, GameView parent) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.parent = parent;

		visible = true;
		newPosition();
	}

	public void update()
	{
		if(visible){
			visibleTime --;

			if(visibleTime < 0){
				newPosition();
			}
		} else {
			invisibleTime --;

			if(invisibleTime < 0){

				newPosition();
			}
		}
	}

	private void newPosition()
	{
		visibleTime =  3 + (int) (Math.random() * 7);
		invisibleTime = (int) (Math.random() * 15);

		x = MARGIN + (int) (Math.random() * (screenWidth - MARGIN * 2));
		y = MARGIN + (int) (Math.random() * (screenHeight - MARGIN * 2));

		visible = !visible;

		parent.invalidateView();
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
	public boolean getVisibility()
	{
		return visible;
	}

	public boolean isHit(float px, float py, int w, int h)
	{
		if(visible &&
			px > x && px < x + w &&
			py > y && py < y + h){

			newPosition();

			return true;
		} else {
			return false;
		}
	}
}
