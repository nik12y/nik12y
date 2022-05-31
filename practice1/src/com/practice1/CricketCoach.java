package com.practice1;

public class CricketCoach implements HCoach {

	private FortuneService fortuneService;

	public CricketCoach() {
		System.out.println("Fortune Sevice with no-arg constructor");
	}

	public void setFortuneService(FortuneService fortuneService) {
		System.out.println("Fortune Service with setter method -setFortuneService");
		this.fortuneService = fortuneService;

	}

	@Override
	public String getGoals() {
		// TODO Auto-generated method stub
		return "DO daily practice to acheive goals";
	}

	@Override
	public String getDailyFortune() {
		return fortuneService.getFortune();
	}

}
