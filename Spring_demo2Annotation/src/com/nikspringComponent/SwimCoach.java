package com.nikspringComponent;

import org.springframework.beans.factory.annotation.Value;

public class SwimCoach implements Coach {

	private FortuneService fortuneService;

	public SwimCoach(FortuneService theFortuneService) {
		fortuneService = theFortuneService;
	}

	@Value("${foo.email}")
	private String email;

	@Value("${foo.team}")
	private String team;

	public String getEmail() {
		return email;
	}

	public String getTeam() {
		return team;
	}

	@Override
	public String getDailyWorkout() {
		return "Swim 1000 meters is a warm up";
	}

	@Override
	public String getDailyFortune() {
		return "Today is sad feeling";
	}

	@Override
	public String nikPractice() {
		return null;
	}

}
