package com.management.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.management.entity.Employee;
import com.management.entity.User;
import com.management.service.EmployeeService;
import com.management.service.UserService;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("/")
	public String list(Model model, Principal principal) {
		List<User> users = userService.findAll();
		model.addAttribute("Users", users);
		List<Employee> employees = employeeService.findAll();
		model.addAttribute("Employees", employees);
		if (principal != null)
			model.addAttribute("loggedInUser", principal.getName());
		else
			model.addAttribute("loggedInUser", null);
		return "index";
	}
}
