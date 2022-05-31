package com.nikspringComponent;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomFortuneService implements FortuneService {

	private String[] data = { "hi i am Nikhil learning Spring Boot", 
			                                   "I set my target to complete the course",
			                                   "within the 10days" };
	//  private int [] val= {5,6,7,8,9,12,};		                                   

	// create the Random class to pick any string from string[]
	private Random myRandom = new Random();

	@Override
	public String getFortune() {
	//pick up the random values from array	
		int index = myRandom.nextInt(data.length);
		
		String theFortune = data[index];
		
		return theFortune;
	}

	@Override
	public String nikPracticeDEmo() {
		// TODO Auto-generated method stub
		return null;
	}

}
