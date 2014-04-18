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
	public static final int ButtonClickActivity_ID = 2;
	private EditText time, videographer, observers, dog;
	private Spinner handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trial);
		ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.handlers, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		handler = (Spinner) findViewById(R.id.spinner1);
		handler.setAdapter(adapter);
		dog = (EditText) findViewById(R.id.editText0);
		videographer = (EditText) findViewById(R.id.editText1);
		observers = (EditText) findViewById(R.id.editText2);
		time = (EditText) findViewById(R.id.editText3);
		Calendar c = Calendar.getInstance();
		time.setText(c.get(Calendar.HOUR) + ":" +  c.get(Calendar.MINUTE) + (c.get(Calendar.AM_PM)==0 ? "am" : "pm"));
		time.setEnabled(false);
	}
	
	private void saveTrial() {
		Trial t = Trial.getCurrentTrial(this);
		t.setTime(time.getText().toString());
		t.setDog(dog.getText().toString());
		t.setVideographer(videographer.getText().toString());
		t.setObservers(observers.getText().toString());
		t.setHandler((String) handler.getSelectedItem());
		t.save();
	}
	
	public void onExitButtonClick (View v) {
		saveTrial();
		finish();
        System.exit(0);
	}
	
	public void onNextButtonClick (View v) {
		saveTrial();
		Intent i = new Intent(this, WheelActivity.class);
		startActivityForResult(i,ButtonClickActivity_ID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}
}
