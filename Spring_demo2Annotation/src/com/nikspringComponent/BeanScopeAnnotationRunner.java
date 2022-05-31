package com.nikspringComponent;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanScopeAnnotationRunner {

	public static void main(String[] args) {
		
		
		ClassPathXmlApplicationContext context =new ClassPathXmlApplicationContext("applicationContext.xml");
		
		Coach theCoach = context.getBean("tennisCoach" , Coach.class);
		
		Coach alphaCoach = context.getBean("tennisCoach" , Coach.class);
		
		boolean result=(theCoach==alphaCoach);
		
		System.out.println("\nPointer call to the same object : "+result);
		
		
	}
}
