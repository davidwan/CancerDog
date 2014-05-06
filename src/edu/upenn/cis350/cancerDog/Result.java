package edu.upenn.cis350.cancerDog;

public class Result {
	
	int numMiss;
	int numFalse;
	int numSuccess;
	
	public Result() {
		reset();
	}
	
	void reset() {
		numMiss = 0;
		numFalse = 0;
		numSuccess = 0;
	}
}
