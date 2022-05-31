package com.nikspring.basicJavacode;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanLifeCycle {

	public static void main(String[] args) {

		// create classPathXMl
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanLifeCycle-applicationContext.xml");

		Coach theCoach = context.getBean("myCoach", Coach.class);
 
		System.out.println( theCoach.getDailyWorkout());
		
		context.close();
}
	
}
