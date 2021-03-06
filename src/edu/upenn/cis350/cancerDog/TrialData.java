package edu.upenn.cis350.cancerDog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class TrialData extends Activity {
	
	public static final int ButtonClickActivity_ID = 1;
	private Spinner[] spinners = new Spinner[9];
	private String notes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trialdata);
		ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.results, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		int[] spinnerIds = {R.id.spinner1, R.id.spinner2, R.id.spinner3, R.id.spinner4, R.id.spinner5, R.id.spinner6, R.id.spinner7, R.id.spinner8};
		for(int i = 0; i < spinnerIds.length; i++) {
			spinners[i] = (Spinner) findViewById(spinnerIds[i]);
			spinners[i].setAdapter(adapter);
		}
		ArrayAdapter <CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.directions, android.R.layout.simple_spinner_item);
		Spinner s2 = (Spinner) findViewById(R.id.spinner9);
		s2.setAdapter(adapter2);
	}
	
	private void saveTrial(boolean doneWithTrial) {
		Trial t = Trial.getCurrentTrial(this);
		String[] trialResults = new String[8];
		for(int i = 0; i < trialResults.length; i++) {
			trialResults[i] = spinners[i].getSelectedItem().toString();
		}
		notes = "";
		notes = ((EditText) findViewById(R.id.editNotes)).getText().toString();
		//t.addTrialResult(trialResults);
		t.addNotes(notes);
		t.save(doneWithTrial, true);
	}
	
	public void onNextButtonClick (View v) {
		Log.i("GRTTrial", "here in next");
		saveTrial(false);
		finish();
		System.exit(0);
	}
	
	public void onNewSessionButtonClick (View v) {
		saveTrial(false);
		Intent i = new Intent(this, LauncherActivity.class);
		startActivityForResult(i,ButtonClickActivity_ID);
	}
	
	public void onExitButtonClick (View v) {
		saveTrial(true);
		finish();
		Intent intent = new Intent(getApplicationContext(), LauncherActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}

}
