package edu.upenn.cis350.cancerDog.tests;

import edu.upenn.cis350.cancerDog.R;
import edu.upenn.cis350.cancerDog.RandomizeActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

public class RandomizeActivityTest extends ActivityInstrumentationTestCase2<RandomizeActivity>{
	private RandomizeActivity rAct;
	private Spinner mSpinner;
	private NumberPicker mPicker;
	private TextView label1;
	private TextView label2;
	
	public RandomizeActivityTest () {
        super(RandomizeActivity.class);
    } 
	
	protected void setUp() throws Exception {
        super.setUp();
        rAct = getActivity();
        mSpinner =(Spinner) rAct.findViewById(R.id.spinner);
        mPicker =(NumberPicker) rAct.findViewById(R.id.controlNumberPicker);
        label1 = (TextView) rAct.findViewById(R.id.textView1);
        label2 = (TextView) rAct.findViewById(R.id.textView2);
    }
	
	public void testPreconditions() {
	    assertNotNull(rAct);
	    assertNotNull(mSpinner);
	    assertNotNull(mPicker);
	}
	
	public void testTextView1_labelText() {
	    final String expected = "Number of Experimentals";
	    final String actual = label1.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testTextView2_labelText() {
	    final String expected = "Number of Controls";
	    final String actual = label2.getText().toString();
	    assertEquals(expected, actual);
	}
	
}
