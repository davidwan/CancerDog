package edu.upenn.cis350.cancerDog;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
	
	public static final int ButtonClickActivity_ID = 2;
	
	private ViewSwitcher switcher;
	private WheelView wheelView;
	private NumberPicker sampleNumberPicker, controlNumberPicker;
	private TextView sampleName, controlName;
	private Spinner sampleSpinner, controlSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_randomize);
		
		switcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
		
		// Set up pre-randomize spinners
		ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.experimentals, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sampleSpinner = (Spinner) findViewById(R.id.spinner1);
		sampleSpinner.setAdapter(adapter);
		adapter = ArrayAdapter.createFromResource(this,R.array.controls, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		controlSpinner = (Spinner) findViewById(R.id.spinner2);
		controlSpinner.setAdapter(adapter);
		
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
        if (picker == sampleNumberPicker) {
        	// 1. Instantiate an AlertDialog.Builder with its constructor
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);

        	// 2. Chain together various setter methods to set the dialog characteristics
        	builder.setMessage(R.string.dialog_message)
        	       .setTitle(R.string.dialog_title);

        	// 3. Get the AlertDialog from create()
        	AlertDialog dialog = builder.create();
        	
        	
        }
    }
	
	public void onExitButtonClick (View v) {
		finish();
        System.exit(0);
	}
	
	public void onNextButtonClick (View v) {
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
		sampleName.setText("Red: " + sampleSpinner.getSelectedItem().toString());
		if (numControls > 0) {
			controlName.setText("Blue: " + controlSpinner.getSelectedItem().toString());
		}
		else {
			controlName.setText("Blue: None");
		}
		switcher.showNext();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}
}
