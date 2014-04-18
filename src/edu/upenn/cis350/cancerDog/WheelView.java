package edu.upenn.cis350.cancerDog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.*;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class WheelView extends View {
	
	private int wheelSize = 12;
	private Paint paint = new Paint();
	private Paint textPaint = new Paint();
	protected ShapeDrawable[] cir;
	
	private double prevAngle = 0;
	private double rotatedAngle = 0;
	private float centerX, centerY;

	public WheelView(Context c) {
		super(c);
	}
	
	public WheelView(Context c, AttributeSet a) {
		super(c, a);
	}

	protected void init(Trial t) {
		cir = new ShapeDrawable[wheelSize];
		for (int i=0; i<wheelSize; ++i) {
			cir[i] = new ShapeDrawable(new OvalShape());
			cir[i].getPaint().setColor(Color.GRAY);
		}
		
		int expSlot = t.getExperimentalSlot();
		HashMap<Integer, String> controls = t.getControls();
		cir[expSlot].getPaint().setColor(Color.RED);
		for (Integer i: controls.keySet()) {
			cir[i].getPaint().setColor(Color.BLUE);
		}
		
		ArrayList<Integer> angles = t.getRotatedAngles();
		if (angles.isEmpty()) {
			rotatedAngle = 0;
		}
		else {
			rotatedAngle = angles.get(angles.size()-1) * Math.PI / 180;
		}
		
		paint.setColor(Color.BLACK);	
		paint.setStrokeWidth(10);
		
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(50);
		
		centerX = 350;
		centerY = 280;
	}
	
	protected int getRotatedAngle() {
		return ((int)(rotatedAngle * 180 / Math.PI)) % 360;
	}
	
	public void onDraw(Canvas canvas) {
		
		for (int i=0; i<6; ++i) {
			double angle = Math.PI * i / 6 - Math.PI / 2 + rotatedAngle;
			int startX = (int) (400 + 250 * Math.cos(angle));
			int startY = (int) (330 + 250 * Math.sin(angle));
			int endX = (int) (400 + 250 * Math.cos(angle + Math.PI));
			int endY = (int) (330 + 250 * Math.sin(angle + Math.PI));
			canvas.drawLine(startX, startY, endX, endY, paint);
		}
		
		for (int i=0; i<wheelSize; ++i) {
			double angle = 2 * Math.PI * i / wheelSize - Math.PI / 2 + rotatedAngle;
			int left = (int) (350 + 250 * Math.cos(angle));
			int top = (int) (280 + 250 * Math.sin(angle));
			cir[i].setBounds(left, top, left+100, top+100);
			cir[i].draw(canvas);
			int x = (int) (385 + 250 * Math.cos(angle));
			int y = (int) (345 + 250 * Math.sin(angle));
			canvas.drawText("" + (i+1), x, y, textPaint);
		}
	}
	
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		double angle;
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			prevAngle = Math.atan((y-centerY)/(x-centerX));
			return true;
		case MotionEvent.ACTION_MOVE:
			angle = Math.atan((y-centerY)/(x-centerX));
			if (angle-prevAngle > Math.PI/2) {
				prevAngle += Math.PI;
			}
			else if (angle-prevAngle < -Math.PI/2) {
				prevAngle -= Math.PI;
			}
			rotatedAngle += angle - prevAngle;
			prevAngle = angle;
			invalidate();
			return true;
		case MotionEvent.ACTION_UP:
			angle = Math.atan((y-centerY)/(x-centerX));
			if (angle-prevAngle > Math.PI/2) {
				prevAngle += Math.PI;
			}
			else if (angle-prevAngle < -Math.PI/2) {
				prevAngle -= Math.PI;
			}
			rotatedAngle += angle - prevAngle;
			invalidate();
			return true;	
		default:
			return false;
		}
	}
}
