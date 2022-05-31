package com.nikspring.basicJavacode;

public class CricketCoach implements Coach {

	private FortuneService fortuneService;	
	
	private String emailAddress;
	private String team;
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		System.out.println("CricketCoach: inside setter method - setEmailAddress");
		this.emailAddress = emailAddress;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		System.out.println("CricketCoach: inside setter method - setTeam");
		this.team = team;
	}

	// create a no-arg constructor
	public CricketCoach() {
		System.out.println("CricketCoach: inside no-arg constructor");
	}
	
	// our setter method
	public void setFortuneService(FortuneService fortuneService) {
		System.out.println("CricketCoach: inside setter method - setFortuneService");
		this.fortuneService = fortuneService;
	}

	@Override
	public String getDailyWorkout() {
		return "Practice fast bowling for 15 minutes";
	}

	@Override
	public String getDailyFortune() {
		return fortuneService.getFortune();
	}

//	private FortuneService fortuneService;
//
//	// create no-arg constructor
//	public CricketCoach() {
//		System.out.println("CricketCoach : inside no-arg constructor");
//	}
//
//	// create setter method
//	public void setFortuneService(FortuneService fortuneService) {
//		System.out.println("CricketCoac : inside  setter method : +setFortuneService");
//		this.fortuneService = fortuneService;
//	}
//
//	@Override
//	public String getDailyWorkOut() {
//
//		return "Practice daily to throw bowling upto 45 minutes";
//	}
//
//	@Override
//	public String trackCoachStatus() {
//		return "Run very fast to get bowl catch";
//	}
//
//	@Override
//	public String getDailyFortune() {
//		// TODO Auto-generated method stub
//		return fortuneService.getFortune();
//	}

}
