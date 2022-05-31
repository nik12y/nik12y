package com.nikWebDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nikWebDemo.DAO.CustomerDao;
import com.nikWebDemo.entity.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerDao customerDao;
	
	@RequestMapping("/list")
	public String showCustomers(ModelMap modelmap) {
		
		List<Customer> customers = customerDao.getCustomers();
		
		modelmap.addAttribute("customers", customers);
		return "list-customer";
	}
}
