package edu.upenn.cis350.cancerDog;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditDefaultActivity extends Activity{
	public static final int ButtonClickActivity_ID = 1;
	static ArrayList<String> handlers = new ArrayList<String> ();
	static ArrayList<String> dogs = new ArrayList<String> ();
	static ArrayList<String> experimentals = new ArrayList<String> ();
	static ArrayList<String> controls = new ArrayList<String> ();
	private Spinner hSpinner, dSpinner, eSpinner, cSpinner;
	private ArrayAdapter<CharSequence> hAdapter, dAdapter, eAdapter, cAdapter;
	boolean hSet, dSet, eSet, cSet = false; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editdefaults);
		handlers = getGroup (this, "handlers");
		dogs = getGroup (this, "dogs");
		experimentals = getGroup (this, "experimentals");
		controls = getGroup (this, "controls");
		
		handlers.add("fatty");
		handlers.add("doggie");
		hAdapter = new ArrayAdapter (this, android.R.layout.simple_spinner_dropdown_item);
		//hAdapter.add("Click to edit");
		for (String s: handlers) {
			hAdapter.add(s);
		}
		hAdapter.add("Add +");
		
		hSpinner = (Spinner) findViewById(R.id.handlerSpinner);
		hSpinner.setAdapter(hAdapter);
		hSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				if (!hSet) {
					hSet = true;
				}
				else {
					Toast.makeText(EditDefaultActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
					if (position < handlers.size()) {
						deletePrompt(handlers, position, hSet);
					}
					else {
						addPrompt(handlers);
					}
				}
		    }
			
			@Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		});
	}
	
	public void deletePrompt (ArrayList<String> list, final int position, boolean set) {
		AlertDialog.Builder temp = new AlertDialog.Builder(EditDefaultActivity.this);
		temp.setMessage("Are you sure you want to delete " + list.get(position) );
		temp.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               handlers.remove(position);
               refreshAdapters();
           }
        });
		temp.setNegativeButton("No", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   // User cancelled the dialog
               }
        });
		AlertDialog tempDialog = temp.create();
		tempDialog.show();
	}
	
	public void addPrompt (ArrayList<String> list) {
		final EditText input = new EditText(EditDefaultActivity.this);
	    
	    AlertDialog.Builder temp = new AlertDialog.Builder(EditDefaultActivity.this);
		temp.setTitle("Add");
		
		temp.setView(input);
		
		temp.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  
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
	
	public void refreshAdapters () {
		hAdapter.clear();
		for (String s: handlers) {
			hAdapter.add(s);
		}
		hAdapter.add("Add +");
		
		/*
		dAdapter.clear();
		for (String s: dogs) {
			dAdapter.add(s);
		}
		
		eAdapter.clear();
		for (String s: experimentals) {
			eAdapter.add(s);
		}
		
		cAdapter.clear();
		for (String s: controls) {
			cAdapter.add(s);
		}*/
	}
	
	public static ArrayList<String> getGroup (Context c, String group) {
		String defaultStr = "Null";
		SharedPreferences preferences = c.getSharedPreferences("edu.upenn.cis350.cancerDog." + group, Context.MODE_PRIVATE);
		ArrayList<String> temp = new ArrayList<String> ();
		int i = 0;
		String x = defaultStr;
		while ((x = preferences.getString(String.valueOf(i), defaultStr)) != defaultStr) {
			i++;
			temp.add(x);
		}
		return temp;
	}
	
	
	
	public void onPreviousButtonClick (View v) {
		finish();
        System.exit(0);
	}
	
	
}
