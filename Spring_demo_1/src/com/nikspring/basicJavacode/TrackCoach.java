package com.nikspring.basicJavacode;

public class TrackCoach implements Coach {

	private FortuneService fortuneService;

	public TrackCoach(FortuneService fortuneService) {
		// super();
		this.fortuneService = fortuneService;
	}

	public TrackCoach() {
	}

//	@Override
//	public String getDailyWorkout() {
//		return "Spend 50 minutes in running";
//	}

//	@Override
//	public String trackCoachStatus() {
//		return "Run a hard upto 5km";
//	}

	@Override
	public String getDailyFortune() {
		return "Lets do it  :: "+fortuneService.getFortune();
	}

	@Override
	public String getDailyWorkout() {
		return null;
	}
	
	public void doMyStuffReady() {
		System.out.println("Intit method run : +doMyStuffReady");
	}

	public void doMyStuffDestroy() {
		System.out.println("Destroy method run : +doMyStuffDestroy");
	}
}










