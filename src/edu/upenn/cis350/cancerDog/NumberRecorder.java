// Inspired by Jeffery F. Cole's NumberPicker

package edu.upenn.cis350.cancerDog;

import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NumberRecorder extends LinearLayout {
	
	private final int MAX = 99;
	private final int MIN = 0;
	
	int value;
	Button plus, minus;
	EditText valueText;
	int height;

	public NumberRecorder(Context context, int height) {
		super(context);
		
		this.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		this.setOrientation(HORIZONTAL);
		
		value = 0;
		this.height = height;
		initPlusButton(context);
		initMinusButton(context);
		initValueText(context);
		
		LayoutParams elementParams = new LinearLayout.LayoutParams(height, height);
		addView(minus, elementParams);
		addView(valueText, new LinearLayout.LayoutParams((int)(1.5*height), height));
		addView(plus, elementParams);
	}
	
	private void initPlusButton(Context context) {
		plus = new Button(context);
		plus.setTextSize((int)(0.5*height));
		plus.setText("+");
		
		plus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (value < MAX) {
					value++;
					valueText.setText("" + value);
				}
			}
		});
	}
	
	private void initMinusButton(Context context) {
		minus = new Button(context);
		minus.setTextSize((int)(0.5*height));
		minus.setText("-");
		
		minus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (value > MIN) {
					value--;
					valueText.setText("" + value);
				}
			}
		});
	}
	
	private void initValueText(Context context) {
		valueText = new EditText(context);
		valueText.setTextSize((int)(0.5*height));
		valueText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		valueText.setText("" + value);
		valueText.setInputType( InputType.TYPE_CLASS_NUMBER );
		
		valueText.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int arg1, KeyEvent event) {
				int backupValue = value;
				try {
					value = Integer.parseInt( ((EditText)v).getText().toString() );
				} catch(NumberFormatException e){
					value = backupValue;
				}
				return false;
			}
		});
		
		// Highlight the number when we get focus
		valueText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					((EditText)v).selectAll();
				}
			}
		});
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int v) {
		if (v > MAX) {
			value = MAX;
		} else if (v < MIN) {
			value = MIN;
		} else {
			value = v;
		}
		valueText.setText("" + value);
	}

}
