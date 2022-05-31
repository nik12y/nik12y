package com.practice1;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext hContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		HockeyCoach hockeyCoach = hContext.getBean("HCoachid", HockeyCoach.class);
		System.out.println(hockeyCoach.getGoals());
		hContext.close();
	}
}
