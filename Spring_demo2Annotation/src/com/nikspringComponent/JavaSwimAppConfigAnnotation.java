package com.nikspringComponent;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaSwimAppConfigAnnotation {

	public static void main(String[] args) {
		// create the classPath
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SprotConfig.class);

		// Retrieve the bean d=from spring container
		// Coach theCoach = context.getBean("swimCoach" , Coach.class);
		SwimCoach swimCoach = context.getBean("swimCoach", SwimCoach.class);
		
		/*
		 * System.out.println(theCoach.getDailyWorkout());
		 * 
		 * System.out.println(theCoach.getDailyFortune());
		 * 
		 * System.out.println(theCoach.nikPractice());
		 */
		System.out.println("email  :"+ swimCoach.getEmail());
		System.out.println("Team : " +swimCoach.getTeam());
		context.close();
	}
}
