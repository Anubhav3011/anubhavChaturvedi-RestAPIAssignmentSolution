package com.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.entity.Employee;
import com.management.service.EmployeeService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Employee Controller", tags = { "employee-controller" }, description = "Employee API")
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/save")
	public String save(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("email") String email) {
		Employee employee = Employee.builder().firstName(firstName).lastName(lastName).email(email).build();
		employeeService.save(employee);
		return "Successfully saved - " + employee.toString();
	}

	@GetMapping("/findAll")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}

	@GetMapping("/findById")
	public String findById(@RequestParam("employeeId") int id) {
		if (employeeService.existsById(id)) {
			return employeeService.findById(id).toString();
		} else {
			return "No employee found with id - " + id;
		}
	}

	@PutMapping("/updateById")
	public String updateById(@RequestBody Employee employee) {
		if (employeeService.existsById(employee.getId())) {
			employeeService.save(employee);
			return "Successfully updated - " + employeeService.findById(employee.getId()).toString();
		} else {
			return "No employee found with id - " + employee.getId();
		}
	}

	@DeleteMapping("/deleteById")
	public String deleteById(@RequestParam("employeeId") int id) {
		if (employeeService.existsById(id)) {
			employeeService.deleteById(id);
			return "Deleted employee id - " + id;
		} else
			return "No employee found with id - " + id;
	}

	@GetMapping("/findByFirstNameContainsAllIgnoreCase")
	public List<Employee> findByFirstNameContainsAllIgnoreCase(@RequestParam("firstName") String firstName) {
		return employeeService.findByFirstNameContainsAllIgnoreCase(firstName);
	}

	@GetMapping("/sortByFirstNameDirection")
	public List<Employee> sortByFirstNameDirection(@RequestParam Direction direction) {
		return employeeService.sortByFirstNameDirection(direction);
	}

}
