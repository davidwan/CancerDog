package edu.upenn.cis350.cancerDog;


import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class RandomizeActivity extends Activity implements NumberPicker.OnValueChangeListener{
	
	public static final int ButtonClickActivity_ID = 3;
	
	private ViewSwitcher switcher;
	private WheelView wheelView;
	private NumberPicker sampleNumberPicker, controlNumberPicker;
	private TextView sampleName, controlName;
	private ArrayList<String> experiments;
	private ArrayList<String> controls;
	private int sampleCount;
	private int controlCount;
	private AlertDialog sampleAlert;
	private AlertDialog controlAlert;
	private AlertDialog.Builder sampleBuilder;
	private AlertDialog.Builder controlBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_randomize);
		
		switcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
		
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
		controlNumberPicker.setMaxValue(3);
		controlNumberPicker.setValue(0);
		sampleNumberPicker.setOnValueChangedListener(this);
		controlNumberPicker.setOnValueChangedListener(this);
		
		sampleName = (TextView) findViewById(R.id.sampleName);
		controlName = (TextView) findViewById(R.id.controlName);
		wheelView = (WheelView) findViewById(R.id.wheelView);
	}
	
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(this, "change", Toast.LENGTH_SHORT).show();
    }
	
	private void saveTrial() {
		Trial t = Trial.getCurrentTrial(this);
		t.setNumControls(sampleNumberPicker.getValue());
		t.setControls(controls);
		t.setNumExperimentals(controlNumberPicker.getValue());
		t.setExperimentals(experiments);
		t.save();
	}
	
	public void onExitButtonClick (View v) {
		saveTrial();
		finish();
        System.exit(0);
	}
	
	public void onNextButtonClick (View v) {
		saveTrial();
		Intent i = new Intent(this, TrialActivity.class);
		startActivityForResult(i,ButtonClickActivity_ID);
	}
	 
	public void goToPreRandomizeView (View v) {
		switcher.showPrevious();
	}
	
	public void goToRandomizeView (View v) {
		int numSamples = sampleNumberPicker.getValue();
		int numControls = controlNumberPicker.getValue();
		wheelView.randomize(numSamples, numControls);
		sampleName.setText("Red: " + "blah");
		if (numControls > 0) {
			controlName.setText("Blue: " + "blah");
		}
		else {
			controlName.setText("Blue: None");
		}
		
		switcher.showNext();
		
		experiments = new ArrayList<String> ();
		controls = new ArrayList<String> ();
		int counter = 0;
		
		sampleCount = numSamples - 1;
		
		ArrayList<AlertDialog.Builder> experimentBuilders = new ArrayList<AlertDialog.Builder> ();
		ArrayList<AlertDialog> experimentDialogs = new ArrayList<AlertDialog> ();
		
		for (int i=numControls; i>0; i--) {
			AlertDialog.Builder temp = new AlertDialog.Builder(this);
			temp.setTitle("Pick control for #" + i);
			temp.setItems(R.array.controls, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
		                String[] temp = getResources().getStringArray(R.array.controls);
		                controls.add(temp[which]);
		                saveTrial();
		            }
		     });
			AlertDialog tempDialog = temp.create();
			tempDialog.show();
		}
		
		for (int i=numSamples; i>0; i--) {
			AlertDialog.Builder temp = new AlertDialog.Builder(this);
			temp.setTitle("Pick experimental for #" + i);
			temp.setItems(R.array.experimentals, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
		                String[] temp = getResources().getStringArray(R.array.experimentals);
		                experiments.add(temp[which]);
		                saveTrial();
		            }
		     });
			AlertDialog tempDialog = temp.create();
			tempDialog.show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}
}
