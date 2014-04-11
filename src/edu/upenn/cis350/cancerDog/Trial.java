package edu.upenn.cis350.cancerDog;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Trial {
	private static Integer numTrials;
	public static Context context;
	private static HashMap<Integer, Trial> cache = new HashMap<Integer, Trial>();
	private Integer trialNumber;
	private Integer numExperimentals;
	private Integer numControls;
	private ArrayList<String> experimentals = new ArrayList<String>();
	private ArrayList<String> controls = new ArrayList<String>();
	private String handler;
	private String videographer;
	private String observers;
	private String time;
	
	public static Trial getTrial(int num) {
		if(cache.containsKey(num)) {
			return cache.get(num);
		}
		Trial t = new Trial();
		SharedPreferences preferences = context.getSharedPreferences("edu.upenn.cis350.cancerDog.trial"+num, Context.MODE_PRIVATE);
		t.setTrialNumber(num);
		t.setNumExperimentals(preferences.getInt("numExperimentals", 0));
		
		String defaultStr = "Not Given";
		ArrayList<String> experimentals = t.getExperimentals();
		for(int i=0; i < t.getNumExperimentals(); i++) {
			experimentals.add(preferences.getString("experimentals" + i, defaultStr));
		}
		t.setNumControls(preferences.getInt("numControls", 0));
		ArrayList<String> controls = t.getControls();
		for(int i=0; i < t.getNumControls(); i++) {
			controls.add(preferences.getString("controls" + i, defaultStr));
		}
		t.setHandler(preferences.getString("handler", defaultStr));
		t.setVideographer(preferences.getString("videographer", defaultStr));
		t.setObservers(preferences.getString("observers", defaultStr));
		t.setTime(preferences.getString("time", defaultStr));
		
		cache.put(numTrials, t);
		
		return t;
	}
	
	public static int getNumTrials() {
		if(numTrials == null) {
			SharedPreferences mainPreferences = context.getSharedPreferences("edu.upenn.cis350.cancerDog", Context.MODE_PRIVATE);
			numTrials = mainPreferences.getInt("numTrials", 0);
		}
		return numTrials;
	}
	
	public static Trial getNewTrial() {
		SharedPreferences mainPreferences = context.getSharedPreferences("edu.upenn.cis350.cancerDog", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = mainPreferences.edit();
		editor.putInt("numTrials", getNumTrials() + 1);
		editor.commit();
		return getTrial(++numTrials);
	}
	
	public static Trial getCurrentTrial(Context c) {
		context = c;
		return getTrial(getNumTrials());
	}
	
	public void save() {
		SharedPreferences preferences = context.getSharedPreferences("edu.upenn.cis350.cancerDog.trial"+trialNumber, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("numExperimetals", numExperimentals);
		for(int i=0; i < experimentals.size(); i++) {
			editor.putString("experimentals" + i, experimentals.get(i));
		}
		editor.putInt("numControls", numControls);
		for(int i=0; i < controls.size(); i++) {
			editor.putString("controls" + i, controls.get(i));
		}
		editor.putString("handler", handler);
		editor.putString("videographer", videographer);
		editor.putString("observers", observers);
		editor.putString("time", time);
		
		editor.commit();
	}
	
	public Integer getTrialNumber() {
		return trialNumber;
	}
	public void setTrialNumber(Integer preferenceNumber) {
		this.trialNumber = preferenceNumber;
	}
	public Integer getNumExperimentals() {
		return numExperimentals;
	}
	public void setNumExperimentals(Integer numExperimentals) {
		this.numExperimentals = numExperimentals;
	}
	public Integer getNumControls() {
		return numControls;
	}
	public void setNumControls(Integer numControls) {
		this.numControls = numControls;
	}
	public ArrayList<String> getExperimentals() {
		return experimentals;
	}
	public void setExperimentals(ArrayList<String> e) {
		experimentals = e;
	}
	public ArrayList<String> getControls() {
		return controls;
	}
	public void setControls(ArrayList<String> c) {
		controls = c;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public String getVideographer() {
		return videographer;
	}
	public void setVideographer(String videographer) {
		this.videographer = videographer;
	}
	public String getObservers() {
		return observers;
	}
	public void setObservers(String observers) {
		this.observers = observers;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("trialNumber: " + trialNumber + "\n");
		s.append("numExperimetals: " + numExperimentals + "\n");
		for(int i=0; i < experimentals.size(); i++) {
			s.append("experimentals" + i + ": " + experimentals.get(i) + "\n");
		}
		s.append("numControls: " + numControls + "\n");
		for(int i=0; i < controls.size(); i++) {
			s.append("controls" + i + ": " + controls.get(i) + "\n");
		}
		s.append("handler: " + handler + "\n");
		s.append("videographer: " + videographer + "\n");
		s.append("observers: " + observers + "\n");
		s.append("time: " + time + "\n");
		
		return s.toString();
	}

}
