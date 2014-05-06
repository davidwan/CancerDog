package edu.upenn.cis350.cancerDog.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.NumberPicker;
import android.widget.TextView;
import edu.upenn.cis350.cancerDog.R;
import edu.upenn.cis350.cancerDog.TrialActivity;

public class TrialActivityTest extends ActivityInstrumentationTestCase2<TrialActivity>{
	private TrialActivity tAct;
	private NumberPicker mPicker1;
	private NumberPicker mPicker2;
	private TextView label1;
	private TextView label2;
	private TextView label3;
	private TextView label4;
	private TextView label5;
	private TextView label6;
	
	public TrialActivityTest () {
        super(TrialActivity.class);
    }
	
	protected void setUp() throws Exception {
        super.setUp();
        tAct = getActivity();
        label1 = (TextView) tAct.findViewById(R.id.textView1);
        label2 = (TextView) tAct.findViewById(R.id.textView2);
        label3 = (TextView) tAct.findViewById(R.id.textView3);
        label4 = (TextView) tAct.findViewById(R.id.textView4);
        label5 = (TextView) tAct.findViewById(R.id.textView5);
        label6 = (TextView) tAct.findViewById(R.id.textView6);
    }
	
	public void testPreconditions() {
	    assertNotNull(tAct);
	    assertNotNull(label1);
	    assertNotNull(label2);
	    assertNotNull(label3);
	    assertNotNull(label4);
	    assertNotNull(label5);
	    assertNotNull(label6);
	}
	
	public void testTextView1_labelText() {
	    final String expected = "Session Information";
	    final String actual = label1.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testTextView2_labelText() {
	    final String expected = "Handler";
	    final String actual = label2.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testTextView3_labelText() {
	    final String expected = "Dog";
	    final String actual = label3.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testTextView4_labelText() {
	    final String expected = "Videographer";
	    final String actual = label4.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testTextView5_labelText() {
	    final String expected = "Observers";
	    final String actual = label5.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testTextView6_labelText() {
	    final String expected = "Date";
	    final String actual = label6.getText().toString();
	    assertEquals(expected, actual);
	}
}
