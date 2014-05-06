package edu.upenn.cis350.cancerDog;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DisplayEditActivity extends ListActivity {
	int trialNum;
	String key;
	String val;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		Trial.context = this;
		trialNum = extras.getInt("sessionNumber");
		String[] trialData = Trial.getTrial(trialNum).toString().split("\n");
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_displayedit, trialData));
 
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
				String edit = ((TextView) view).getText().toString();
				key = edit.split(":")[0];
				
				if (!key.equals("sessionNumber") && !key.equals("experimentalSlot") && !key.contains("controlSlot") && !key.equals("experimentalSlot") && !key.equals("time") && !key.equals("date")) {
					//Toast.makeText(DisplayEditActivity.this, key, Toast.LENGTH_SHORT).show();
				    
				    final EditText input = new EditText(DisplayEditActivity.this);
				    
				    AlertDialog.Builder temp = new AlertDialog.Builder(DisplayEditActivity.this);
					temp.setTitle("Change value for " + key);
					
					temp.setView(input);
					
					temp.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					  val = input.getText().toString();
					  updateTrial();
					  }
					});

					temp.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
					    // Canceled.
					  }
					});
						
					AlertDialog tempDialog = temp.create();
					
				    tempDialog.setView(input);
				    
					tempDialog.show();
				}
			}
		});
	}
	
	protected void updateTrial () {
		Trial.edit(trialNum, key, val);
		//Toast.makeText(DisplayEditActivity.this, key + ":" + val, Toast.LENGTH_SHORT).show();
		String[] trialData = Trial.getTrial(trialNum).toString().split("\n");
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_displayedit, trialData));
	}
}
