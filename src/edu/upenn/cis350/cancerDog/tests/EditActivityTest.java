package edu.upenn.cis350.cancerDog.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.NumberPicker;
import android.widget.TextView;
import edu.upenn.cis350.cancerDog.EditActivity;
import edu.upenn.cis350.cancerDog.R;
import edu.upenn.cis350.cancerDog.Trial;

public class EditActivityTest extends ActivityInstrumentationTestCase2<EditActivity>{
	private EditActivity eAct;
	private Trial t;
	
	public EditActivityTest () {
        super(EditActivity.class);
    }
	
	protected void setUp() throws Exception {
        super.setUp();
        eAct = getActivity();
		t = new Trial();
		t.getCurrentTrial(eAct);
    }
	
	public void testPreconditions() {
		assertNotNull(eAct);
		assertNotNull(t);
	}
}
