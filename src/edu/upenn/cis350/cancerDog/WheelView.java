package edu.upenn.cis350.cancerDog;

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
	
	Paint paint = new Paint();
	protected ShapeDrawable cir1, cir2, cir3, cir4, cir5, cir6, cir7, cir8;

	
	protected void init() {
		paint.setColor(Color.BLACK);	
		paint.setStrokeWidth(10);
		cir1 = new ShapeDrawable(new OvalShape());
		cir1.getPaint().setColor(Color.GRAY);
		cir1.setBounds(150, 250, 250, 350);
		cir2 = new ShapeDrawable(new OvalShape());
		cir2.getPaint().setColor(Color.GRAY);
		cir2.setBounds(550, 250, 650, 350);
		cir3 = new ShapeDrawable(new OvalShape());
		cir3.getPaint().setColor(Color.GRAY);
		cir3.setBounds(350, 170, 450, 270);
		cir4 = new ShapeDrawable(new OvalShape());
		cir4.getPaint().setColor(Color.GRAY);
		cir4.setBounds(70, 450, 170, 550);
		cir5 = new ShapeDrawable(new OvalShape());
		cir5.getPaint().setColor(Color.GRAY);
		cir5.setBounds(550, 650, 650, 750);
		cir6 = new ShapeDrawable(new OvalShape());
		cir6.getPaint().setColor(Color.GRAY);
		cir6.setBounds(150, 650, 250, 750);
		cir7 = new ShapeDrawable(new OvalShape());
		cir7.getPaint().setColor(Color.GRAY);
		cir7.setBounds(350, 730, 450, 830);
		cir8 = new ShapeDrawable(new OvalShape());
		cir8.getPaint().setColor(Color.GRAY);
		cir8.setBounds(630, 450, 730, 550);
	}
	
	public void onDraw(Canvas canvas) {
		canvas.drawLine(200, 300, 600, 700, paint);
		canvas.drawLine(600, 300, 200, 700, paint);
		canvas.drawLine(400, 220, 400, 780, paint);
		canvas.drawLine(120, 500, 680, 500, paint);
		cir1.draw(canvas);
		cir2.draw(canvas);
		cir3.draw(canvas);
		cir4.draw(canvas);
		cir5.draw(canvas);
		cir6.draw(canvas);
		cir7.draw(canvas);
		cir8.draw(canvas);
		
		
	}
	

}
