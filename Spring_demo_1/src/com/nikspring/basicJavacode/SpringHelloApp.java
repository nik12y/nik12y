package com.nikspring.basicJavacode;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringHelloApp {

	public static void main(String[] args) {

		// load the spring configure container
		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("applicationContext.xml");

		// Retrieve the beans from container
		Coach theCoach = context.getBean("myCoach", Coach.class);

		// call the method on the bean
		System.out.println(theCoach.getDailyWorkout());

		//call the fortune service
		System.out.println(theCoach.getDailyFortune());
	
		// close the context path
		context.close();
	}
}
