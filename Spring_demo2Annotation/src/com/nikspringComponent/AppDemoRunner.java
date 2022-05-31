package com.nikspringComponent;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppDemoRunner {

	public static void main(String[] args) {
	//create the classPath
	ClassPathXmlApplicationContext context =new ClassPathXmlApplicationContext("applicationContext.xml");
	
	//Retrieve the bean d=from spring  container 
	   Coach theCoach = context.getBean("tennisCoach" , Coach.class);
	   
	   System.out.println(theCoach.getDailyWorkout());
	   System.out.println(theCoach.getDailyFortune());
	   System.out.println(theCoach.nikPractice());
	   context.close();
}
}
