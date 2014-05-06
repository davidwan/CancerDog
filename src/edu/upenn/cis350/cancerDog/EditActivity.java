package edu.upenn.cis350.cancerDog;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Trial.getNumTrials() == 0)
			return;
		Trial.context = this;
		String[] trials = new String[Trial.getNumTrials()];
		for(int i = 0; i < trials.length; i++) {
			trials[i] = "Session " + (i + 1);
		}
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_edit, trials));
 
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
 
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
			    int trialNum = Integer.parseInt(((TextView) view).getText().toString().split(" ")[1]) - 1;
				Intent i = new Intent(parent.getContext(), DisplayEditActivity.class);
				i.putExtra("trialNum", trialNum);
				startActivityForResult(i,1);
			}
		});
	}
	
	public void onExitButtonClick (View v) {
		finish();
        System.exit(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}

}