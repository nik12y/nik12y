package com.springDataRest.SpringDataRest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springDataRest.SpringDataRest.entity.GradleUser;
import com.springDataRest.SpringDataRest.repository.GradleUserRepository;

@SpringBootTest
class SpringDataRestApplicationTests {

	@Autowired
	 GradleUserRepository gradleRepo;
	
	@Test
	void createuser() {
		GradleUser user=new GradleUser();
	user.setId(1);
	user.setFirstName("Nikhil");
	user.setLastName("Kumar");
	user.setEmail("nik@gmail.com");
	gradleRepo.save(user);
	}

}
