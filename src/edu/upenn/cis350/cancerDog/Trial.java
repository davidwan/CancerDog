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
	private ArrayList<String> experimentals = new ArrayList<String>();
	private ArrayList<String> controls = new ArrayList<String>();
	private String handler;
	private String dog;
	private String videographer;
	private String observers;
	private String time;
	private ArrayList<String[]> trialResults = new ArrayList<String[]>();
	
	public static Trial getTrial(int num) {
		if(cache.containsKey(num)) {
			return cache.get(num);
		}
		Trial t = new Trial();
		SharedPreferences preferences = context.getSharedPreferences("edu.upenn.cis350.cancerDog.trial"+num, Context.MODE_PRIVATE);
		t.setTrialNumber(num);
		
		String defaultStr = "Not Given";
		int numExperimentals = preferences.getInt("numExperimentals", 0);
		ArrayList<String> experimentals = t.getExperimentals();
		for(int i=0; i < numExperimentals; i++) {
			experimentals.add(preferences.getString("experimentals" + i, defaultStr));
		}
		int numControls = preferences.getInt("numControls", 0);
		ArrayList<String> controls = t.getControls();
		for(int i=0; i < numControls; i++) {
			controls.add(preferences.getString("controls" + i, defaultStr));
		}
		t.setHandler(preferences.getString("handler", defaultStr));
		t.setDog(preferences.getString("dog", defaultStr));
		t.setVideographer(preferences.getString("videographer", defaultStr));
		t.setObservers(preferences.getString("observers", defaultStr));
		t.setTime(preferences.getString("time", defaultStr));
		int numResults = preferences.getInt("numResults", 0);
		for(int i = 0; i < numResults; i++) {
			String[] result = new String[8];
			for(int j = 0; j < 8; j++) {
				result[j] = preferences.getString("results" + i + j, defaultStr);
			}
			t.trialResults.add(result);
		}
		
		cache.put(num, t);
		
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
		editor.putInt("numExperimentals", experimentals.size());
		for(int i=0; i < experimentals.size(); i++) {
			editor.putString("experimentals" + i, experimentals.get(i));
		}
		editor.putInt("numControls", controls.size());
		for(int i=0; i < controls.size(); i++) {
			editor.putString("controls" + i, controls.get(i));
		}
		editor.putString("handler", handler);
		editor.putString("dog", dog);
		editor.putString("videographer", videographer);
		editor.putString("observers", observers);
		editor.putString("time", time);
		editor.putInt("numResults", trialResults.size());
		for(int i = 0; i < trialResults.size(); i++) {
			for(int j = 0; j < trialResults.get(i).length; j++) {
				editor.putString("results" + i + j, trialResults.get(i)[j]);
			}
		}
		
		editor.commit();
	}
	
	public Integer getTrialNumber() {
		return trialNumber;
	}
	
	public void setTrialNumber(Integer preferenceNumber) {
		this.trialNumber = preferenceNumber;
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

	public String getDog() {
		return dog;
	}

	public void setDog(String dog) {
		this.dog = dog;
	}
	
	public ArrayList<String[]> getTrialResults() {
		return trialResults;
	}
	
	public void addTrialResult(String[] result) {
		trialResults.add(result);
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("trialNumber: " + trialNumber + "\n");
		for(int i=0; i < experimentals.size(); i++) {
			s.append("experimentals" + i + ": " + experimentals.get(i) + "\n");
		}
		for(int i=0; i < controls.size(); i++) {
			s.append("controls" + i + ": " + controls.get(i) + "\n");
		}
		s.append("handler: " + handler + "\n");
		s.append("dog: " + dog + "\n");
		s.append("videographer: " + videographer + "\n");
		s.append("observers: " + observers + "\n");
		s.append("time: " + time + "\n");
		for(int i = 0; i < trialResults.size(); i++) {
			for(int j = 0; j < trialResults.get(i).length; j++) {
				s.append("results" + i + j + ": " + trialResults.get(i)[j] + "\n");
			}
		}
		
		return s.toString();
	}

}
