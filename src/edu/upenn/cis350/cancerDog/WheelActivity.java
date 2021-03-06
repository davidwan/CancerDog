package edu.upenn.cis350.cancerDog;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;

public class WheelActivity extends Activity {
	
	public static final int ButtonClickActivity_ID = 3;
	
	// UI elements
	private TextView trialLabel;
	private TextView expLabel, controlLabel;
	private WheelView wheel;
	private Button recordButton, nextButton;
	private Spinner dirSpinner;
	private EditText editNotes;
	
	// Trial results
	private Result[] results = new Result[12];
	private int currTrial = 1;
	
	// For recording slot results
	private int currSlot = -1;
	private SlotRecorder sr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wheel);
		
		trialLabel = (TextView) findViewById(R.id.trialLabel);
		expLabel = (TextView) findViewById(R.id.experimentalLabel);
		controlLabel = (TextView) findViewById(R.id.controlLabel);
		wheel = (WheelView) findViewById(R.id.wheelView);
		recordButton = (Button) findViewById(R.id.record);
		nextButton = (Button) findViewById(R.id.nextTrial);
		
		ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.directions, android.R.layout.simple_spinner_item);
		dirSpinner = (Spinner) findViewById(R.id.directionSpinner);
		dirSpinner.setAdapter(adapter);
		
		editNotes = (EditText) findViewById(R.id.notesEditor);
		editNotes.setText("");
		
		nextButton.setEnabled(false);
		dirSpinner.setEnabled(false);
		editNotes.setEnabled(false);
		
		Trial t = Trial.getCurrentTrial(this);
		setupLabels(t);
		wheel.init(this, t);
		
		for (int i=0; i<12; ++i) {
			results[i] = new Result();
		}
	}
	
	private void saveWheel() {
		Trial t = Trial.getCurrentTrial(this);
		t.addTopArm(wheel.getTopArm());
		t.save(false, false);
	}
	
	private void saveTrial(boolean doneWithSession) {
		Trial t = Trial.getCurrentTrial(this);
		
		if (!(doneWithSession && !wheel.isFixed())) {
			String notes = editNotes.getText().toString();
			t.addTrialResult(results);
			t.addDirection(dirSpinner.getSelectedItem().toString());
			t.addNotes(notes);
		}
		t.save(doneWithSession, true);
	}
	
	public void endSession (View v) {
		saveTrial(true);
		finish();
        Intent intent = new Intent(getApplicationContext(), LauncherActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	public void record (View v) {
		saveWheel();
		wheel.fix();
		nextButton.setEnabled(true);
		recordButton.setEnabled(false);
		dirSpinner.setEnabled(true);
		editNotes.setEnabled(true);
	}
	
	public void onNextButtonClick (View v) {
		saveTrial(false);
		for (int i=0; i<12; ++i) {
			results[i].reset();
		}
		editNotes.setText("");
		currTrial++;
		trialLabel.setText("Trial " + currTrial);
		wheel.unfix();
		nextButton.setEnabled(false);
		recordButton.setEnabled(true);
		dirSpinner.setEnabled(false);
		editNotes.setEnabled(false);
	}
	
	public void setupLabels(Trial t) {
		trialLabel.setText("Trial " + currTrial);
		
		StringBuffer expText = new StringBuffer();
		expText.append("Slot: " + t.getExperimentalSlot() + "\n");
		expText.append("Name: " + t.getExperimentalName() + "\n");
		
		HashMap<Integer, String> controls = t.getControls();
		StringBuffer controlText = new StringBuffer();
		if (controls.isEmpty()) {
			controlText.append("None");
		}
		else {
			for (Integer i: controls.keySet()) {
				controlText.append("Slot " + i + ": " + controls.get(i) + "\n");
			}
		}
		
		expLabel.setText(expLabel.getText() + expText.toString());
		controlLabel.setText(controlLabel.getText() + controlText.toString());
	}
	
	protected void recordSlot(int slot) {
		// Pop up a dialog that allows user to record misses, falses and successes for each slot
		//Toast.makeText(this, "Slot " + (slot+1), Toast.LENGTH_SHORT).show();
		currSlot = slot;
		AlertDialog.Builder temp = new AlertDialog.Builder(this);
		temp.setTitle("Slot " + slot);
		
		sr = new SlotRecorder(this, results[slot]);
		temp.setView(sr);
		
		temp.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				writeResult();
			}
		});
		
		AlertDialog tempDialog = temp.create();
		tempDialog.show();
	}
	
	void writeResult() {
		if (currSlot != -1) {
			results[currSlot] = sr.getResult();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}
}
