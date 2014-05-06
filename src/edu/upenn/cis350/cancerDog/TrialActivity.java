package edu.upenn.cis350.cancerDog;

import java.util.ArrayList;
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
	private EditText time, videographer, observers, date;
	private Spinner handler, dog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trial);
		ArrayAdapter <CharSequence> adapterHandler = new ArrayAdapter<CharSequence> (this, android.R.layout.simple_spinner_item);
		ArrayList<String> handlers = EditDefaultActivity.getGroup(this, "handlers");
		for (String s: handlers) {
			adapterHandler.add(s);
		}
		adapterHandler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		ArrayAdapter <CharSequence> adapterDog = new ArrayAdapter<CharSequence> (this, android.R.layout.simple_spinner_item);
		ArrayList<String> dogs = EditDefaultActivity.getGroup(this, "dogs");
		for (String s: dogs) {
			adapterDog.add(s);
		}
		adapterDog.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		handler = (Spinner) findViewById(R.id.spinner1);
		handler.setAdapter(adapterHandler);
		dog = (Spinner) findViewById(R.id.spinner2);
		dog.setAdapter(adapterDog);
		
		videographer = (EditText) findViewById(R.id.editText1);
		observers = (EditText) findViewById(R.id.editText2);
		
		
		date = (EditText) findViewById(R.id.editText3);
		Calendar c = Calendar.getInstance();
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		if (month.length() < 2) {
			month = "0" + month;
		}
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		if (day.length() < 2) {
			day = "0" + day;
		}
		date.setText(month + "/" +  day + "/" + c.get(Calendar.YEAR));
		date.setEnabled(false);
		
		time = (EditText) findViewById(R.id.editText4);
		String hour = String.valueOf(c.get(Calendar.HOUR));
		if (hour.length() < 2) {
			hour = "0" + hour;
		}
		String minute = String.valueOf(c.get(Calendar.MINUTE));
		if (minute.length() < 2) {
			minute = "0" + minute;
		}
		time.setText(hour + ":" +  minute + (c.get(Calendar.AM_PM)==0 ? "am" : "pm"));
		time.setEnabled(false);
	}
	
	private void saveTrial() {
		Trial t = Trial.getCurrentTrial(this);
		t.setTime(time.getText().toString());
		t.setDog((String) dog.getSelectedItem());
		t.setVideographer(videographer.getText().toString());
		t.setObservers(observers.getText().toString());
		t.setHandler((String) handler.getSelectedItem());
		t.setDate(date.getText().toString());
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
