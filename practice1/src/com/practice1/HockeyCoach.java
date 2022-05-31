package com.practice1;


public class HockeyCoach implements HCoach {

	// create dependency object
		private FortuneService fortuneService;

		public HockeyCoach(FortuneService theFortuneService) {
			fortuneService = theFortuneService;
		}

		public HockeyCoach() {
		}
	@Override
	public String getGoals() {
		return "2 goals per match";

	}

	@Override
	public String getDailyFortune() {
		return "We will get forune";
	}

}
