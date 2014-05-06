package edu.upenn.cis350.cancerDog;

public class Result {
	
	int numMiss;
	int numFalse;
	int numSuccess;
	
	public Result() {
		reset();
	}
	
	public Result(Result r) {
		numMiss = r.numMiss;
		numFalse = r.numFalse;
		numSuccess = r.numSuccess;
	}
	
	void reset() {
		numMiss = 0;
		numFalse = 0;
		numSuccess = 0;
	}
}
