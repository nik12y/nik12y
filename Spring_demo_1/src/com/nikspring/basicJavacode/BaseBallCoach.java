package com.nikspring.basicJavacode;

public class BaseBallCoach implements Coach {

	// create dependency object
	private FortuneService fortuneService;

	public BaseBallCoach(FortuneService theFortuneService) {
		fortuneService = theFortuneService;
	}

	public BaseBallCoach() {
	}

	// using interface
//	@Override
//	public String getDailyWorkOut() {
//		return "Spend the 45 minutes in daily Practice by interface";
//	}

	// without interface
	public String getDailyWorkOut1() {
		return "Spend the 30 minutes in daily Practice withOutInterface";
	}

//	@Override
//	public String trackCoachStatus() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public String getDailyFortune() {
		return fortuneService.getFortune();
	}

	@Override
	public String getDailyWorkout() {
		// TODO Auto-generated method stub
		return null;
	}

}
