package edu.upenn.cis350.cancerDog;

import java.util.ArrayList;
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

	public WheelView(Context c) {
		super(c);
		init();
	}
	
	public WheelView(Context c, AttributeSet a) {
		super(c, a);
		init();
	}
	
	int wheelSize = 8;
	Paint paint = new Paint();
	Paint textPaint = new Paint();
	protected ShapeDrawable[] cir;
	private ArrayList<Integer> sampleSlots = new ArrayList<Integer>();
	private ArrayList<Integer> controlSlots = new ArrayList<Integer>();
	
	private double prevAngle = 0;
	private double angleChange = 0;
	private float centerX, centerY;

	protected void init() {
		paint.setColor(Color.BLACK);	
		paint.setStrokeWidth(10);
		cir = new ShapeDrawable[wheelSize];
		
		for (int i=0; i<wheelSize; ++i) {
			double angle = 2 * Math.PI * i / wheelSize - Math.PI / 2 + angleChange;
			cir[i] = new ShapeDrawable(new OvalShape());
			cir[i].getPaint().setColor(Color.GRAY);
			int left = (int) (350 + 250 * Math.cos(angle));
			int top = (int) (280 + 250 * Math.sin(angle));
			cir[i].setBounds(left, top, left+100, top+100);
		}
		
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(50);
		
		centerX = 350;
		centerY = 280;
	}
	
	public void onDraw(Canvas canvas) {
		
		for (int i=0; i<4; ++i) {
			double angle = Math.PI * i / 4 - Math.PI / 2 + angleChange;
			int startX = (int) (400 + 250 * Math.cos(angle));
			int startY = (int) (330 + 250 * Math.sin(angle));
			int endX = (int) (400 + 250 * Math.cos(angle + Math.PI));
			int endY = (int) (330 + 250 * Math.sin(angle + Math.PI));
			canvas.drawLine(startX, startY, endX, endY, paint);
		}
		
		for (int i=0; i<wheelSize; ++i) {
			double angle = 2 * Math.PI * i / wheelSize - Math.PI / 2 + angleChange;
			int left = (int) (350 + 250 * Math.cos(angle));
			int top = (int) (280 + 250 * Math.sin(angle));
			cir[i].setBounds(left, top, left+100, top+100);
			cir[i].draw(canvas);
			int x = (int) (385 + 250 * Math.cos(angle));
			int y = (int) (345 + 250 * Math.sin(angle));
			canvas.drawText("" + (i+1), x, y, textPaint);
		}
	}

	void randomize(int numSamples, int numControls) {
		List<Integer> availableCircles = new ArrayList<Integer>();
		for (int i=0; i<8; ++i) {
			cir[i].getPaint().setColor(Color.GRAY);
			availableCircles.add(i);
		}
		Random random = new Random();
		
		// Color sample circles red
		for (int i=0; i<numSamples; ++i) {
			int index = random.nextInt(8-i);  //random index in availableCircles
			int pos = availableCircles.remove(index);  //index of sample circle
			sampleSlots.add(pos+1);
			cir[pos].getPaint().setColor(Color.RED);
		}
		
		// Color control circles blue
		for (int i=0; i<numControls; ++i) {
			int index = random.nextInt(8-numSamples-i);
			int pos = availableCircles.remove(index);
			controlSlots.add(pos+1);
			cir[pos].getPaint().setColor(Color.BLUE);
		}
		
		invalidate();
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
			angleChange += angle - prevAngle;
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
			angleChange += angle - prevAngle;
			invalidate();
			return true;	
		default:
			return false;
		}
	}
	
	int[] getSampleSlots() {
		int[] sampleSlotArray = new int[sampleSlots.size()];
		for (int i=0; i<sampleSlots.size(); ++i) {
			sampleSlotArray[i] = sampleSlots.get(i);
		}
		return sampleSlotArray;
	}
	
	int[] getControlSlots() {
		int[] controlSlotArray = new int[controlSlots.size()];
		for (int i=0; i<controlSlots.size(); ++i) {
			controlSlotArray[i] = controlSlots.get(i);
		}
		return controlSlotArray;
	}
}
