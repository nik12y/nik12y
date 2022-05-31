package com.practice1;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterAppRunner {

	public static void main(String[] args) {
		
		//create the class path
		ClassPathXmlApplicationContext context =new ClassPathXmlApplicationContext("applicationContext.xml");
		
		context.getBean("myCricketCoach" , CricketCoach.class);
		
		context.close();
	}
}
