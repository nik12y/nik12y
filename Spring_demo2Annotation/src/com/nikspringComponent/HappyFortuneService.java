package com.nikspringComponent;

import org.springframework.stereotype.Component;

@Component
public class HappyFortuneService implements FortuneService {

	@Override
	public String getFortune() {
		return "U are lucky one ";
	}

	@Override
	public String nikPracticeDEmo() {
		return "practice is done";
	}

	

}
