package com.nikspring.basicJavacode;

public class MyAppMain {

	public static void main(String[] args) {

		// creating the object of Base class

		BaseBallCoach withOutInterface = new BaseBallCoach(); 
		
	//	System.out.println(withOutInterface.getDailyWorkOut());

		// now for best practice use interface for method so no need to
		// create the object of base class

//		Coach withInterface = new BaseBallCoach();
	//	System.out.println(withInterface.getDailyWorkOut());
		
		Coach track = new TrackCoach();
	//	System.out.println(track.trackCoachStatus());
	}
}
