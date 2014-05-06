package edu.upenn.cis350.cancerDog.tests;

import java.util.ArrayList;

import edu.upenn.cis350.cancerDog.EditDefaultActivity;
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
	private Button select;
	private Button next;
	
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
        select = (Button) rAct.findViewById(R.id.randomize);
        next = (Button) rAct.findViewById(R.id.next);
    }
	
	public void testPreconditions() {
	    assertNotNull(rAct);
	    assertNotNull(mSpinner);
	    assertNotNull(mPicker);
	    assertNotNull(select);
	    assertNotNull(next);
	}
	
	public void testTextView1_labelText() {
	    final String expected = "Experimental";
	    final String actual = label1.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testTextView2_labelText() {
	    final String expected = "Number of Controls";
	    final String actual = label2.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testmSpinner_labels () {
		String expected = EditDefaultActivity.getGroup(rAct, "experimentals").get(0);
		String actual = mSpinner.getSelectedItem().toString();
		assertEquals(expected, actual);
	}
	
	public void testMakeSelections_zero () {
		rAct.runOnUiThread(
			new Runnable() {
				public void run() {
					mPicker.setValue(0);
					select.performClick();
				}
			}
		);
		assertFalse(next.isEnabled());
	}
	
	public void testMakeSelections_morethanzero () {
		rAct.runOnUiThread(
			new Runnable() {
				public void run() {
					mPicker.setValue(1);
					assertEquals(1, mPicker.getValue());
					select.performClick();
				}
			}
		);
		assertFalse(next.isEnabled());
	}
	
	public void testMakeSelections_noControls () {
		rAct.runOnUiThread(
			new Runnable() {
				public void run() {
					rAct.controlsArray.clear();
					mPicker.setValue(1);
					next.setEnabled(false);
					assertEquals(1,mPicker.getValue());
					select.performClick();
				}
			}
		);
		assertFalse(next.isEnabled());
	}
	
	public void testMakeSelections_positiveControls () {
		rAct.controlsArray.add("test");
		rAct.runOnUiThread(
			new Runnable() {
				public void run() {
					mPicker.setValue(1);
					assertEquals(mPicker.getValue(),1);
					select.performClick();
				}
			}
		);
		assertTrue(rAct.controlsArray.size() > 1);
	}
	
	public void testMakeSelections_twoPositiveControls () {
		rAct.controlsArray.add("test");
		rAct.controlsArray.add("more");
		rAct.runOnUiThread(
			new Runnable() {
				public void run() {
					mPicker.setValue(1);
					assertEquals(mPicker.getValue(),1);
					select.performClick();
				}
			}
		);
		assertTrue(rAct.controlsArray.size() >2);
	}
	
	public void testValueChange () {
		new Runnable() {
			public void run() {
				next.setEnabled(true);
				mPicker.requestFocus();
				mPicker.setValue(11);
				assertFalse(next.isEnabled());
			}
		};
	}
	
	
	
}
