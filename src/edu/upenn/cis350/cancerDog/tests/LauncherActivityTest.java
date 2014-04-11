package edu.upenn.cis350.cancerDog.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import edu.upenn.cis350.cancerDog.LauncherActivity;
import edu.upenn.cis350.cancerDog.R;

public class LauncherActivityTest extends ActivityInstrumentationTestCase2<LauncherActivity>{
	private LauncherActivity lAct;
	private Button b1;
	private Button b2;
	private Button b3;
	private Button b4;
	private Button b5;
	
	public LauncherActivityTest () {
        super(LauncherActivity.class);
    } 
	
	protected void setUp() throws Exception {
        super.setUp();
        lAct = getActivity();
        b1 =(Button) lAct.findViewById(R.id.launch);
        b2 =(Button) lAct.findViewById(R.id.view);
        b3 =(Button) lAct.findViewById(R.id.edit);
        b4 =(Button) lAct.findViewById(R.id.export);
        b5 =(Button) lAct.findViewById(R.id.exit);
    }
	
	public void testPreconditions() {
	    assertNotNull(lAct);
	    assertNotNull(b1);
	    assertNotNull(b2);
	    assertNotNull(b3);
	    assertNotNull(b4);
	    assertNotNull(b5);
	}
	
	public void testb1_labelText() {
	    final String expected = "Start New Session";
	    final String actual = b1.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testb2_labelText() {
	    final String expected = "View Data";
	    final String actual = b2.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testb3_labelText() {
	    final String expected = "Edit Past Data";
	    final String actual = b3.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testb4_labelText() {
	    final String expected = "Export Data";
	    final String actual = b4.getText().toString();
	    assertEquals(expected, actual);
	}
	
	public void testb5_labelText() {
	    final String expected = "Exit";
	    final String actual = b5.getText().toString();
	    assertEquals(expected, actual);
	}
}
