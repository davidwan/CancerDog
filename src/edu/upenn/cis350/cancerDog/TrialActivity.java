package edu.upenn.cis350.cancerDog;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class TrialActivity extends Activity{
	public static final int ButtonClickActivity_ID = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trial);
		ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.handlers, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner s = (Spinner) findViewById(R.id.spinner1);
		s.setAdapter(adapter);
		EditText time = (EditText) findViewById(R.id.editText3);
		Calendar c = Calendar.getInstance();
		time.setText(c.get(Calendar.HOUR) + ":" +  c.get(Calendar.MINUTE) + (c.get(Calendar.AM_PM)==0 ? "am" : "pm"));
		time.setEnabled(false);
	}
	
	public void onExitButtonClick (View v) {
		finish();
        System.exit(0);
	}
	
	public void onNextButtonClick (View v) {
		Intent i = new Intent(this, TrialActivity.class);
		startActivityForResult(i,ButtonClickActivity_ID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}
}
