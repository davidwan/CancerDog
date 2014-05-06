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
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class WheelActivity extends Activity {
	
	public static final int ButtonClickActivity_ID = 3;
	
	// UI elements
	private TextView expLabel, controlLabel;
	private WheelView wheelView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wheel);
		
		expLabel = (TextView) findViewById(R.id.experimentalLabel);
		controlLabel = (TextView) findViewById(R.id.controlLabel);
		wheelView = (WheelView) findViewById(R.id.wheelView);
		
		Trial t = Trial.getCurrentTrial(this);
		setupLabels(t);
		wheelView.init(t);
	}
	
	private void saveTrial() {
		Trial t = Trial.getCurrentTrial(this);
		t.addRotatedAngle(wheelView.getRotatedAngle());
		t.save();
	}
	
	public void onExitButtonClick (View v) {
		saveTrial();
		finish();
        System.exit(0);
	}
	
	public void onNextButtonClick (View v) {
		saveTrial();
		Intent i = new Intent(this, TrialData.class);
		startActivityForResult(i,ButtonClickActivity_ID);
	}
	
	public void setupLabels(Trial t) {
		StringBuffer expText = new StringBuffer();
		expText.append("Slot: " + (t.getExperimentalSlot()+1) + "\n");
		expText.append("Name: " + t.getExperimentalName() + "\n");
		
		HashMap<Integer, String> controls = t.getControls();
		StringBuffer controlText = new StringBuffer();
		if (controls.isEmpty()) {
			controlText.append("None");
		}
		else {
			for (Integer i: controls.keySet()) {
				controlText.append("Slot " + (i+1) + ": " + controls.get(i) + "\n");
			}
		}
		
		expLabel.setText(expLabel.getText() + expText.toString());
		controlLabel.setText(controlLabel.getText() + controlText.toString());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}
}
