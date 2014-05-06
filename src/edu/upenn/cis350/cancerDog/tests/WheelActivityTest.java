package edu.upenn.cis350.cancerDog.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import edu.upenn.cis350.cancerDog.R;
import edu.upenn.cis350.cancerDog.Trial;
import edu.upenn.cis350.cancerDog.WheelActivity;

public class WheelActivityTest extends ActivityInstrumentationTestCase2<WheelActivity>{
	private WheelActivity wAct;
	private TextView trialLabel, experimentalLabel, controlLabel;
	
	public WheelActivityTest () {
        super(WheelActivity.class);
    }
	
	protected void setUp() throws Exception {
        super.setUp();
        wAct = getActivity();
        trialLabel = (TextView) wAct.findViewById(R.id.trialLabel);
        experimentalLabel = (TextView) wAct.findViewById(R.id.experimentalLabel);
        controlLabel = (TextView) wAct.findViewById(R.id.controlLabel);
    }
	
	public void testPreconditions() {
		assertNotNull(wAct);
		assertNotNull(trialLabel);
		assertNotNull(experimentalLabel);
		assertNotNull(controlLabel);
	}
	
	public void testTrialLabel () {
		assertTrue(trialLabel.getText().toString().contains("Trial "));
	}
	
	public void testExperimentalLabel () {
		assertTrue(experimentalLabel.getText().toString().contains("Red: experimentals\n"));
	}
	
	public void testControlLabel () {
		assertTrue(controlLabel.getText().toString().contains("Blue: controls\n"));
	}
}