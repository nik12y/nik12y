package com.nikspringComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//without giving the bean name we can use class name with lower  case 
@Component
@Scope("prototype")
public class TennisCoach implements Coach {
	
	@Autowired
	@Qualifier("randomFortuneService")
	private FortuneService fortuneService;//--->1 spring Injection type

	
	// create default constructor
	public TennisCoach() {
		System.out.println("TennisCoach : inside it default constructor work ");
	}

	// using any method ---->2  spring Injection type
	/*@Autowired   
	public void callByMethod(FortuneService theFortuneService) {
		System.out.println("callByMethod : inside it callByMethod work ");
		fortuneService = theFortuneService;
	}*/
	
	// setter method we use now ---->3  spring Injection type
//	@Autowired
//	public void setFortuneService(FortuneService theFortuneService) {
//		System.out.println("setFortuneService : inside it setFortuneService work ");
//		fortuneService=theFortuneService;
//	}
	// this is done using constructor
//	@Autowired
//	public TennisCoach(FortuneService theFortuneService) {
//		fortuneService=theFortuneService;
//	}

	@Override
	public String getDailyWorkout() {
		return "Practice daily for match at least 2hours ";
	}

	@Override
	public String getDailyFortune() {
		return fortuneService.getFortune();
	}

	@Override
	public String nikPractice() {
		return fortuneService.nikPracticeDEmo();
	}

}
