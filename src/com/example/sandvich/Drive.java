package com.example.sandvich;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

public class Drive extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FrameLayout frame = (FrameLayout) findViewById(R.id.control_area);
		PlayAreaView image = null;
		try {
			image = new PlayAreaView(this);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//The PlayAreaView is bassically the canvas that will be doing the meat of the work
		frame.addView(image);
	}
	
	//A view is anything that can be added to the layout of the app, like a textfield is considered
	//a view. And in this case we are making our own view

	private class PlayAreaView extends View {

		//Allows the view to detect a variety of gestures
		private GestureDetector gestures;
		//The position of the actual mouse on the screen
		private float x, y;
		//The position of the circle on the app
		private float cx, cy;
		//The stream which lets you write information through the socket to the server on the computer
		private PrintWriter out;
		//A "tunnel" from the android device to the computer
		private Socket socket;
		//The style of painting for the circle
		Paint circlePaint;
		//Animation stuff
		private Matrix animateStart;
		private Interpolator animateInterpolator;
		private long startTime;
		private long endTime;
		private float totalAnimDx;
		private float totalAnimDy;

		//Animating move
		public void onAnimateMove(float dx, float dy, long duration) {
			animateInterpolator = new OvershootInterpolator();
			startTime = System.currentTimeMillis();
			endTime = startTime + duration;
			totalAnimDx = dx;
			totalAnimDy = dy;
			post(new Runnable() {
				@Override
				public void run() {
					onAnimateStep();
				}
			});
		}

		//Each step of the animation
		private void onAnimateStep() {
			long curTime = System.currentTimeMillis();
			float percentTime = (float) (curTime - startTime)
					/ (float) (endTime - startTime);
			float percentDistance = animateInterpolator
					.getInterpolation(percentTime);
			float curDx = percentDistance * totalAnimDx;
			float curDy = percentDistance * totalAnimDy;

			// Log.v(DEBUG_TAG, "We're " + percentDistance +
			// " of the way there!");
			if (percentTime < 1.0f) {
				post(new Runnable() {
					@Override
					public void run() {
						onAnimateStep();
					}
				});
			}
		}

		//Doing something about a finger scroll or finger slide on the screen
		public void onMove(float dx, float dy, float x1, float y1)
				throws UnknownHostException, IOException {
			//Adding the distance the finger has moved to the total mouse position
			x += dx;
			y += dy;
			//not really nessesary but if the mouse is in the upper left corner of the screen then set the mouse position to 0
			if (x < 0)
				x = 0;
			if (y < 0)
				y = 0;
			//setting the circle on the screen to the fingers position x1 and y1 are the fingers position
			cx = x1;
			cy = y1;
			
			//Updates the screen to move the circle
			invalidate();
		}
		
		public void onResetLocation() {
			x = 0;
			y = 0;
		}

		public void onSetLocation(float dx, float dy) {
			x = dx;
			y = dy;
		}

		//Moving the circle to a certain position
		public void moveC(float x1, float y1) {
			cx = x1;
			cy = y1;
		}

		public PlayAreaView(Context context) throws UnknownHostException,
				IOException {
			super(context);
			//setting up gesture detector to make sure the gestures are going to affect stuff that happens in this class
			gestures = new GestureDetector(Drive.this,
					new GestureListener(this));
			circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// Drawing stuff 

			circlePaint.setColor(0xFF000000);
			circlePaint.setStyle(Style.STROKE);
			circlePaint.setStrokeWidth((float) 5.0);
			canvas.drawCircle(cx, cy, 15, circlePaint);
			canvas.drawCircle(cx, cy, 5, circlePaint);
			Matrix m = canvas.getMatrix();
		}

		@Override
		//When a finger touches you are going to return what the gesture detector says to do
		public boolean onTouchEvent(MotionEvent event) {
			return gestures.onTouchEvent(event);
		}

	}

	private class GestureListener implements GestureDetector.OnGestureListener,
			GestureDetector.OnDoubleTapListener {

		PlayAreaView view;

		public GestureListener(PlayAreaView view) {
			this.view = view;
		}

		@Override
		//When a finger is on the screen set the circle to where that is
		public boolean onDown(MotionEvent e) {
			view.moveC(e.getX(), e.getY());
			return true;
		}

		public boolean onFling1(MotionEvent e1, MotionEvent e2,
				final float velocityX, final float velocityY) {
			return true;
		}

		@Override
		//Scrolling is just sliding your finger on the screen and when you do that you are going to send the PlayArea class some info to send to the computer
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// Log.v(DEBUG_TAG, "onScroll");

			try {
				view.onMove(-distanceX, -distanceY, e2.getX(), e2.getY());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			
			return false;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			
			return false;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			
			return false;
		}

		@Override
		public boolean onDoubleTap(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			return false;
		}

	}

}