package com.WebApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.WebApp.Dao.UserDAO;
import com.WebApp.entity.User;

@Controller
public class  UserController {

	@Autowired
	UserDAO userDao;

	@RequestMapping("/list")
	public String listCustomer(Model model) {

		List<User> user = userDao.getUsers();
		model.addAttribute("user", user);
		return "listCustomer";
	}
}
