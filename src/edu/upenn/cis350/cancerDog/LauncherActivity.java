package edu.upenn.cis350.cancerDog;

import edu.upenn.cis350.cancerDog.RandomizeActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class LauncherActivity extends Activity {
	
	public static final int ButtonClickActivity_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		Log.d("GRTTrial", Trial.getCurrentTrial(this).toString());
	}
	
	public void onLaunchButtonClick (View v) {
		//setContentView(new WheelView(this));
		Trial.getNewTrial();
		Intent i = new Intent(this, RandomizeActivity.class);
		
		startActivityForResult(i,ButtonClickActivity_ID);
	}
	
	public void onViewButtonClick(View v) {
		Intent i = new Intent(this, ViewActivity.class);
		startActivityForResult(i, ButtonClickActivity_ID);
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
