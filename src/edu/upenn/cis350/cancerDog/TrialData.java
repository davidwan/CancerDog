package edu.upenn.cis350.cancerDog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TrialData extends Activity {
	
	public static final int ButtonClickActivity_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trialdata);
		ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.results, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner s1 = (Spinner) findViewById(R.id.spinner1);
		s1.setAdapter(adapter);
		Spinner s2 = (Spinner) findViewById(R.id.spinner2);
		s2.setAdapter(adapter);
		Spinner s3 = (Spinner) findViewById(R.id.spinner3);
		s3.setAdapter(adapter);
		Spinner s4 = (Spinner) findViewById(R.id.spinner4);
		s4.setAdapter(adapter);
		Spinner s5 = (Spinner) findViewById(R.id.spinner5);
		s5.setAdapter(adapter);
		Spinner s6 = (Spinner) findViewById(R.id.spinner6);
		s6.setAdapter(adapter);
		Spinner s7 = (Spinner) findViewById(R.id.spinner7);
		s7.setAdapter(adapter);
		Spinner s8 = (Spinner) findViewById(R.id.spinner8);
		s8.setAdapter(adapter);
	}
	
	public void onNextButtonClick (View v) {
		//setContentView(new WheelView(this));
		finish();
		Intent i = new Intent(this, TrialData.class);
		
		startActivityForResult(i,ButtonClickActivity_ID);
	}
	
	public void onNewSessionButtonClick (View v) {
		Intent i = new Intent(this, LauncherActivity.class);
		startActivityForResult(i,ButtonClickActivity_ID);
	}
	
	public void onExitButtonClick (View v) {
		finish();
        System.exit(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}

}
