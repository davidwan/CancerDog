package edu.upenn.cis350.cancerDog;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class DisplayTrialActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		Trial.context = this;
		String[] trialData = Trial.getTrial(extras.getInt("trialNum")).toString().split("\n");
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_displaytrial, trialData));
 
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
	}
}
