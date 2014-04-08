package edu.upenn.cis350.cancerDog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.util.AttributeSet;
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
	protected ShapeDrawable[] cir;

	protected void init() {
		paint.setColor(Color.BLACK);	
		paint.setStrokeWidth(10);
		cir = new ShapeDrawable[wheelSize];
		
		for (int i=0; i<wheelSize; ++i) {
			double angle = 2 * Math.PI * i / wheelSize;
			cir[i] = new ShapeDrawable(new OvalShape());
			cir[i].getPaint().setColor(Color.GRAY);
			int left = (int) (350 + 250 * Math.cos(angle));
			int top = (int) (450 + 250 * Math.sin(angle));
			cir[i].setBounds(left, top, left+100, top+100);
		}
	}
	
	public void onDraw(Canvas canvas) {
		canvas.drawLine(200, 300, 600, 700, paint);
		canvas.drawLine(600, 300, 200, 700, paint);
		canvas.drawLine(400, 220, 400, 780, paint);
		canvas.drawLine(120, 500, 680, 500, paint);
		
		for (int i=0; i<8; ++i) {
			cir[i].draw(canvas);
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
			cir[pos].getPaint().setColor(Color.RED);
		}
		
		// Color control circles blue
		for (int i=0; i<numControls; ++i) {
			int index = random.nextInt(8-numSamples-i);
			int pos = availableCircles.remove(index);
			cir[pos].getPaint().setColor(Color.BLUE);
		}
		
		invalidate();
	}

}
