package edu.upenn.cis350.cancerDog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.GsonBuilder;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

public class Trial {
	private static Integer numTrials;
	public static Context context;
	private static HashMap<Integer, Trial> cache = new HashMap<Integer, Trial>();
	private Integer sessionNumber;
	private int expSlot;
	private String expName;
	private HashMap<Integer, String> controls = new HashMap<Integer, String>();
	private String handler;
	private String dog;
	private String videographer;
	private String observers;
	private String time;
	private String date;
	private ArrayList<String> notes = new ArrayList<String> ();
	private ArrayList<String> directions = new ArrayList<String> ();
	private ArrayList<Result[]> trialResults = new ArrayList<Result[]>();
	private ArrayList<Integer> topArms = new ArrayList<Integer>(); // the arms that are on the top in trials

	public static Trial getTrial(int num) {
		if (cache.containsKey(num)) {
			return cache.get(num);
		}
		Trial t = new Trial();
		SharedPreferences preferences = context.getSharedPreferences(
				"edu.upenn.cis350.cancerDog.trial" + num, Context.MODE_PRIVATE);
		t.setTrialNumber(num);

		String defaultStr = "Not Given";
		t.setExperimentalSlot(preferences.getInt("experimentalSlot", 0));
		t.setExperimentalName(preferences.getString("experimentalName",
				defaultStr));

		int numControls = preferences.getInt("numControls", 0);
		for (int i = 0; i < numControls; i++) {
			int slot = preferences.getInt("controlSlot[" + i + "]", 0);
			String name = preferences.getString("controlName[" + i + "}", defaultStr);
			t.addControl(slot, name);
		}

		t.setHandler(preferences.getString("handler", defaultStr));
		t.setDog(preferences.getString("dog", defaultStr));
		t.setVideographer(preferences.getString("videographer", defaultStr));
		t.setObservers(preferences.getString("observers", defaultStr));
		t.setTime(preferences.getString("time", defaultStr));
		t.setDate(preferences.getString("date", defaultStr));

		int numResults = preferences.getInt("numResults", 0);
		for(int i = 0; i < numResults; i++) {
			Result[] result = new Result[12];
			for(int j = 0; j < 12; j++) {
				result[j] = new Result();
				String r = preferences.getString("results[" + i + "][" + j + "]", defaultStr);
				if (!r.equals(defaultStr)) {
					String[] tokens = r.split(" ");
					if (tokens.length == 3) {
						if (tokens[0].startsWith("Miss")) {
							result[j].numMiss = Integer.parseInt(tokens[0].substring(4));
						}
						if (tokens[1].startsWith("False")) {
							result[j].numFalse = Integer.parseInt(tokens[1].substring(5));
						}
						if (tokens[2].startsWith("Success")) {
							result[j].numSuccess = Integer.parseInt(tokens[2].substring(7));
						}
					}
				}
			}
			t.addTrialResult(result);
			int angle = preferences.getInt("topArm[" + i + "]", 0);
			t.addTopArm(angle);
			t.addNotes(preferences.getString("notes[" + i + "]", defaultStr));
			//t.addDirection(preferences.getString("direction[" + i + "]", defaultStr));
		}

		cache.put(num, t);

		return t;
	}

	public static int getNumTrials() {
		if (numTrials == null) {
			SharedPreferences mainPreferences = context.getSharedPreferences(
					"edu.upenn.cis350.cancerDog", Context.MODE_PRIVATE);
			numTrials = mainPreferences.getInt("numTrials", 0);
		}
		return numTrials;
	}

	public static Trial getNewTrial() {
		return getTrial(numTrials);
	}

	public static Trial getCurrentTrial(Context c) {
		context = c;
		return getTrial(getNumTrials());
	}
	
	private HashMap<String, Object> toHashMap() {
		HashMap<String, Object> trial = new HashMap<String, Object>();
		trial.put("experimentalSlot", expSlot);
		trial.put("experimentalName", expName);
		if (controls != null) {
			trial.put("controls", controls);
		}
		trial.put("handler", handler);
		trial.put("dog", dog);
		trial.put("videographer", videographer);
		trial.put("observers", observers);
		trial.put("time", time);
		trial.put("date", date);
		trial.put("results", trialResults);
		trial.put("notes", notes);
		trial.put("topArm", topArms);
		trial.put("sessionNumber", sessionNumber);
		trial.put("direction", directions);
		return trial;
	}

	public static void edit(int sessionNumber, String key, String val) {
		SharedPreferences preferences = context.getSharedPreferences(
				"edu.upenn.cis350.cancerDog.trial" + sessionNumber,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		
		if (key.contains("topArm")) {
			try {
				editor.putInt(key, Integer.parseInt(val));
			} catch (NumberFormatException e) {
				
			}
		}
		else {
			editor.putString(key, val);
		}
		
		cache.remove(sessionNumber);

		editor.commit();

		HashMap<String, Object> trial = getTrial(sessionNumber).toHashMap();
		trial.put("edit", true);
		PostJson task = new PostJson();
		task.execute((HashMap<String, Object>[]) (new HashMap[] { trial }));
	}

	public void save(boolean doneWithTrial) {
		SharedPreferences preferences = context.getSharedPreferences("edu.upenn.cis350.cancerDog.trial"+sessionNumber, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();

		editor.putInt("experimentalSlot", expSlot);
		editor.putString("experimentalName", expName);

		if (controls != null) {
			editor.putInt("numControls", controls.size());
			int ind = 0;
			for (Integer i : controls.keySet()) {
				editor.putInt("controlSlot[" + ind + "]", i);
				editor.putString("controlName[" + ind + "]", controls.get(i));
				ind++;
			}
		}

		editor.putString("handler", handler);
		editor.putString("dog", dog);
		editor.putString("videographer", videographer);
		editor.putString("observers", observers);
		editor.putString("time", time);
		editor.putString("date", date);
		editor.putInt("numResults", trialResults.size());
		for(int i = 0; i < trialResults.size(); i++) {
			for(int j = 0; j < trialResults.get(i).length; j++) {
				Result r = trialResults.get(i)[j];
				editor.putString("results[" + i + "][" + j +"]", "Miss" + r.numMiss
						+ " False" + r.numFalse + " Success" + r.numSuccess);
			}
			editor.putInt("topArm[" + i + "]", topArms.get(i));
			editor.putString("notes[" + i + "]", notes.get(i));
			editor.putString("direction[" + i + "]", directions.get(i));
		}
		editor.commit();

		if (doneWithTrial) {
			SharedPreferences mainPreferences = context.getSharedPreferences(
					"edu.upenn.cis350.cancerDog", Context.MODE_PRIVATE);
			editor = mainPreferences.edit();
			editor.putInt("numTrials", getNumTrials() + 1);
			numTrials += 1;
			editor.commit();
			HashMap<String, Object> trial = toHashMap();
			PostJson task = new PostJson();
			task.execute((HashMap<String, Object>[]) (new HashMap[] { trial }));
		}
	}

	public Integer getTrialNumber() {
		return sessionNumber;
	}

	public void setTrialNumber(Integer preferenceNumber) {
		this.sessionNumber = preferenceNumber;
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
		controls.put(s, n);
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

	public String getDate() {
		return date;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDog() {
		return dog;
	}

	public void setDog(String dog) {
		this.dog = dog;
	}
	
	public ArrayList<Result[]> getTrialResults() {
		return trialResults;
	}
	
	public void addTrialResult(Result[] result) {
		Result[] copy = new Result[result.length];
		for (int i=0; i<result.length; ++i) {
			copy[i] = new Result(result[i]);
		}
		trialResults.add(copy);
	}
	
	public ArrayList<Integer> getTopArms() {
		return topArms;
	}
	
	public void addTopArm(int a) {
		topArms.add(a);
	}

	public ArrayList<String> getNotes() {
		return notes;
	}

	public void addNotes(String n) {
		notes.add(n);
	}
	
	public ArrayList<String> getDirections() {
		return directions;
	}
	
	public void addDirection(String d) {
		directions.add(d);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("sessionNumber: " + (sessionNumber + 1) + "\n");
		s.append("date: " + date + "\n");
		s.append("time: " + time + "\n");
		s.append("handler: " + handler + "\n");
		s.append("dog: " + dog + "\n");
		s.append("videographer: " + videographer + "\n");
		s.append("observers: " + observers + "\n");
		s.append("experimentalName: " + expName + "\n");
		s.append("experimentalSlot: " + expSlot + "\n");
		int ind = 0;
		for (Integer i : controls.keySet()) {
			s.append("controlName[" + ind + "]: " + controls.get(i) + "\n");
			s.append("controlSlot[" + ind + "]: " + i + "\n");
			ind++;
		}
		
		for (int i = 0; i < trialResults.size(); i++) {
			s.append("topArm[" + i + "]: " + topArms.get(i) + "\n");
			if(i < directions.size()) {
				s.append("direction[" + i + "]:" + directions.get(i) + "\n");
			}
			for (int j = 0; j < trialResults.get(i).length; j++) {
				Result r = trialResults.get(i)[j];
				s.append("results[" + i + "][" + j + "]: " + " Result: " + "Miss"
						+ r.numMiss + "   False" + r.numFalse + "   Success" 
						+ r.numSuccess + "\n");
			}
			s.append("notes[" + i + "]:" + notes.get(i) + "\n");
		}

		return s.toString();
	}

	private static class PostJson extends
			AsyncTask<HashMap<String, Object>, Void, Void> {

		@Override
		protected Void doInBackground(HashMap<String, Object>... arg0) {
			String json = new GsonBuilder().create().toJson(arg0[0], Map.class);
			try {
				HttpPost httpPost = new HttpPost(
						"http://pennvet.herokuapp.com/");
				httpPost.setEntity(new StringEntity(json));
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-type", "application/json");
				new DefaultHttpClient().execute(httpPost);
				Log.i("GRTTrial", "Posted");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
