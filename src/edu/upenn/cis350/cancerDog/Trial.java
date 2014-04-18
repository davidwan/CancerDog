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
	private int expSlot;
	private String expName;
	private HashMap<Integer, String> controls = new HashMap<Integer, String>();
	private String handler;
	private String dog;
	private String videographer;
	private String observers;
	private String time;
	private String notes;
	private ArrayList<String[]> trialResults = new ArrayList<String[]>();
	private ArrayList<Integer> rotatedAngles = new ArrayList<Integer>(); 
	
	public static Trial getTrial(int num) {
		if(cache.containsKey(num)) {
			return cache.get(num);
		}
		Trial t = new Trial();
		SharedPreferences preferences = context.getSharedPreferences("edu.upenn.cis350.cancerDog.trial"+num, Context.MODE_PRIVATE);
		t.setTrialNumber(num);
		
		String defaultStr = "Not Given";
		t.setExperimentalSlot(preferences.getInt("experimentalSlot", 0));
		t.setExperimentalName(preferences.getString("experimentalName", defaultStr));
		
		int numControls = preferences.getInt("numControls", 0);
		for (int i=0; i<numControls; i++) {
			int slot = preferences.getInt("controlSlot" + i, 0);
			String name = preferences.getString("controlName" + i, defaultStr);
			t.addControl(slot, name);
		}
		
		t.setHandler(preferences.getString("handler", defaultStr));
		t.setDog(preferences.getString("dog", defaultStr));
		t.setVideographer(preferences.getString("videographer", defaultStr));
		t.setObservers(preferences.getString("observers", defaultStr));
		t.setTime(preferences.getString("time", defaultStr));
		
		int numResults = preferences.getInt("numResults", 0);
		for(int i = 0; i < numResults; i++) {
			String[] result = new String[12];
			for(int j = 0; j < 12; j++) {
				result[j] = preferences.getString("results" + i + j, defaultStr);
			}
			t.addTrialResult(result);
			int angle = preferences.getInt("rotatedAngle" + i, 0);
			t.addRotatedAngle(angle);
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
		return getTrial(numTrials);
	}
	
	public static Trial getCurrentTrial(Context c) {
		context = c;
		return getTrial(getNumTrials());
	}
	
	public void save() {
		SharedPreferences preferences = context.getSharedPreferences("edu.upenn.cis350.cancerDog.trial"+trialNumber, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("experimentalSlot", expSlot);
		editor.putString("experimentalName", expName);
		
		if (controls != null) {
			editor.putInt("numControls", controls.size());
			int ind = 0;
			for(Integer i : controls.keySet()) {
				editor.putInt("controlSlot" + ind, i);
				editor.putString("controlName" + ind, controls.get(i));
				ind++;
			}
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
			editor.putInt("rotatedAngle" + i, rotatedAngles.get(i));
		}
		
		editor.commit();
	}
	
	public Integer getTrialNumber() {
		return trialNumber;
	}
	
	public void setTrialNumber(Integer preferenceNumber) {
		this.trialNumber = preferenceNumber;
	}
	
	public int getExperimentalSlot() {
		return expSlot;
	}
	
	public void setExperimentalSlot(int s) {
		expSlot = s;
	}
	
	public String getExperimentalName() {
		return expName;
	}
	
	public void setExperimentalName(String n) {
		expName = n;
	}
	
	public HashMap<Integer, String> getControls() {
		return controls;
	}
	
	public void addControl(int s, String n) {
		controls.put(s,  n);
	}
	
	public void setControls(HashMap<Integer, String> c) {
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
	
	public ArrayList<Integer> getRotatedAngles() {
		return rotatedAngles;
	}
	
	public void addRotatedAngle(int a) {
		rotatedAngles.add(a);
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("trialNumber: " + trialNumber + "\n");
		s.append("experimentalSlot: " + expSlot + "\n");
		s.append("experimentalName: " + expName + "\n");
		int ind = 0;
		for(Integer i: controls.keySet()) {
			s.append("controlSlot" + ind + ": " + i + "\n");
			s.append("controlName" + ind + ": " + controls.get(i) + "\n");
			ind++;
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
