package com.nikspring.basicJavacode;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScopeBeanRunner {

	public static void main(String[] args) {

		// create classPathXMl
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("scopeBean-applicationContext.xml");

		Coach theCoach = context.getBean("myCoach", Coach.class);

		Coach alphaCoach = context.getBean("myCoach", Coach.class);

		// check these two are same
		boolean result = (theCoach == alphaCoach);
		// for singleton request
		System.out.println("\nPointing to the same object :" + result);// output-->true

		// for prototype(it create the new object on every request) request
		System.out.println("\nPointing to the same object :" + result); // output-->false
		
		System.out.println("\nThe Memory address of theCoach : " + theCoach);

		System.out.println("\nThe Memory address of alphaCoach : " + alphaCoach);

	}
}