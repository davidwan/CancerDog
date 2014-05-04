package edu.upenn.cis350.cancerDog;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class RandomizeActivity extends Activity implements NumberPicker.OnValueChangeListener{
	
	public static final int ButtonClickActivity_ID = 3;
	
	// UI elements
	private NumberPicker sampleNumberPicker, controlNumberPicker;
	
	private int numExperimentals, numControls;
	private int numSelectedExperimentals, numSelectedControls;
	private ArrayList<String> experiments;
	private ArrayList<String> controlNames;
	private Button nextButton;
	
	private int expSlot;
	private String expName;
	private HashMap<Integer, String> controls = new HashMap<Integer, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_randomize);
		
		// Set up pre-randomize spinners
		ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.experimentals, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//sampleSpinner = (Spinner) findViewById(R.id.spinner1);
		//sampleSpinner.setAdapter(adapter);
		adapter = ArrayAdapter.createFromResource(this,R.array.controls, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//controlSpinner = (Spinner) findViewById(R.id.spinner2);
		//controlSpinner.setAdapter(adapter);
		
		// Set up number pickers
		sampleNumberPicker = (NumberPicker) findViewById(R.id.sampleNumberPicker);
		sampleNumberPicker.setMinValue(1);
		sampleNumberPicker.setMaxValue(3);
		sampleNumberPicker.setValue(1);
		controlNumberPicker = (NumberPicker) findViewById(R.id.controlNumberPicker);
		controlNumberPicker.setMinValue(0);
		controlNumberPicker.setMaxValue(11);
		controlNumberPicker.setValue(0);
		sampleNumberPicker.setOnValueChangedListener(this);
		controlNumberPicker.setOnValueChangedListener(this);
		nextButton = (Button) findViewById(R.id.next);
		nextButton.setEnabled(false);
		
	}
	
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        //Toast.makeText(this, "change", Toast.LENGTH_SHORT).show();
        nextButton.setEnabled(false);
    }
	
	private void saveTrial() {
		Trial t = Trial.getCurrentTrial(this);
		t.setExperimentalSlot(expSlot);
		t.setExperimentalName(expName);
		t.setControls(controls);
		t.save();
	}
	
	public void onExitButtonClick (View v) {
		saveTrial();
		finish();
        System.exit(0);
	}
	
	public void onNextButtonClick (View v) {
		if ((numExperimentals + numControls) != (experiments.size() + controlNames.size())) {
			Toast.makeText(this, "Each control and experiment must be defined. Try selecting again.", Toast.LENGTH_SHORT).show();
			nextButton.setEnabled(false);
		}
		else {
			saveTrial();
			Intent i = new Intent(this, TrialActivity.class);
			startActivityForResult(i,ButtonClickActivity_ID);
		}
	}
	
	public void makeSelections (View v) {
		numExperimentals = sampleNumberPicker.getValue();
		numControls = controlNumberPicker.getValue();
		
		experiments = new ArrayList<String> ();
		controlNames = new ArrayList<String> ();
		
		numSelectedExperimentals = 0;
		numSelectedControls = 0;
		
		for (int i=numControls; i>0; i--) {
			AlertDialog.Builder temp = new AlertDialog.Builder(this);
			temp.setTitle("Pick control for #" + i);
			temp.setItems(R.array.controls, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
	                String[] temp = getResources().getStringArray(R.array.controls);
	                controlNames.add(temp[which]);
	                numSelectedControls++;
	                randomize();
	            }
		     });
			AlertDialog tempDialog = temp.create();
			tempDialog.show();
		}
		
		for (int i=numExperimentals; i>0; i--) {
			AlertDialog.Builder temp = new AlertDialog.Builder(this);
			temp.setTitle("Pick experimental for #" + i);
			temp.setItems(R.array.experimentals, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
	                String[] temp = getResources().getStringArray(R.array.experimentals);
	                experiments.add(temp[which]);
	                numSelectedExperimentals++;
	                randomize();
	            }
		     });
			AlertDialog tempDialog = temp.create();
			tempDialog.show();
		}
		nextButton.setEnabled(true);
	}
	
	public void randomize() {
		if (numSelectedExperimentals == numExperimentals && numSelectedControls == numControls) {
			List<Integer> availableCircles = new ArrayList<Integer>();
			for (int i=0; i<12; ++i) {
				availableCircles.add(i);
			}
			Random random = new Random();
			
			// Get experimental slot
			int index = random.nextInt(12);  //random index in availableCircles
			int slot = availableCircles.remove(index);  //index of sample circle
			expSlot = slot;
			expName = experiments.get(0);
			
			// Set control slots
			for (int i=0; i<numControls; ++i) {
				index = random.nextInt(12-numExperimentals-i);
				slot = availableCircles.remove(index);
				controls.put(slot, controlNames.get(i));
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}
}
