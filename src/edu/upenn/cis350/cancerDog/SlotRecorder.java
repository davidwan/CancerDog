package edu.upenn.cis350.cancerDog;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

public class SlotRecorder extends GridLayout {
	
	private NumberRecorder[] nr;
	
	Result result = new Result();

	public SlotRecorder(Context context, Result r) {
		super(context);
		
		result = r;
		
		String[] results = this.getResources().getStringArray(R.array.results);
		nr = new NumberRecorder[3];
		
		this.setRowCount(3);
		this.setColumnCount(3);
		this.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		int nrHeight = 80;
		
		for (int i=0; i<3; ++i) {
			TextView tv = new TextView(context);
			tv.setTextSize((int)(0.3*nrHeight));
			tv.setText(results[i]);
			tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			nr[i] = new NumberRecorder(context, nrHeight);
			nr[i].setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
			switch (i) {
			case 0: nr[i].setValue(result.numMiss); break;
			case 1: nr[i].setValue(result.numFalse); break;
			case 2: nr[i].setValue(result.numSuccess); break;
			}
			this.addView(tv, new LayoutParams(spec(i), spec(0)));
			this.addView(nr[i], new LayoutParams(spec(i), spec(2)));
		}
	}
	
	Result getResult() {
		result.numMiss = nr[0].getValue();
		result.numFalse = nr[1].getValue();
		result.numSuccess = nr[2].getValue();
		return result;
	}

}
