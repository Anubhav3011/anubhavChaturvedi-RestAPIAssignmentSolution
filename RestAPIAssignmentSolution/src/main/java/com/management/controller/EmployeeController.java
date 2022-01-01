package com.management.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	public Employee save(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("email") String email) {
		Employee employee = Employee.builder().firstName(firstName).lastName(lastName).email(email).build();
		if (!employeeService.existsById(employee.getId())) {
			employeeService.save(employee);
			return employee;
		} else {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Employee with id - " + employee.getId() + " already exists");
		}
	}

	@GetMapping("/findAll")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}

	@GetMapping("/findById")
	public Employee findById(@RequestParam("employeeId") int id) {
		try {
			return employeeService.findById(id);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee found with id - " + id);
		}
	}

	@PutMapping("/updateById")
	public Employee updateById(@RequestBody Employee employee) {
		if (employeeService.existsById(employee.getId())) {
			employeeService.save(employee);
			return employee;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee found with id - " + employee.getId());
		}
	}

	@DeleteMapping("/deleteById")
	public String deleteById(@RequestParam("employeeId") int id) {
		try {
			employeeService.deleteById(id);
			return "Deleted employee id - " + id;
		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee found with id - " + id);
		}
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
