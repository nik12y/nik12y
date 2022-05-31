package com.luv2code.springdemo.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

	@RequestMapping("/showHello")
	public String helloWorld() {
		return "hellowWorld-form";
	}

	@RequestMapping("/processForm")
	public String proceedFrom() {
		return "helloStudentName";
	}

//using HttpServletRequest we solve 
	@RequestMapping("/processFormVersion2")
	public String addFromData(HttpServletRequest request, Model model) {
		
		// read the request parameter from the jsp page
		String studentName = request.getParameter("studentName");
		String courseName = request.getParameter("courseName");
		
		// name must be in Upper Case
		String name = studentName.toUpperCase();
		String course = courseName.toUpperCase();
		
		// it should show some Message
		String nameStudent = "YO!" + name;
		String resultCourse = "YO!" + course;
		
		// Add to the model page
		model.addAttribute("name", nameStudent);
		model.addAttribute("course", resultCourse);
		
		return "helloStudentName";
	}

	@RequestMapping("/processFormVersion3")
	public String usingReqestParam(@RequestParam("studentName") String theName
			                                                           , Model model) {
		// read the jsp data with @RequestParam
				
				// name must be in Upper Case
				String name = theName.toUpperCase();
				
				// it should show some Message
				String nameStudent = "Hey Buddy how are you Now" + name;
				
				// Add to the model page
				model.addAttribute("name", nameStudent);
				
		return "helloStudentName";

	}

}
