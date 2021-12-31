package com.management.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.management.entity.Employee;

public interface EmployeeService {

	public boolean existsById(int id);

	public List<Employee> findAll();

	public Employee findById(int id);

	public void save(Employee employee);

	public void deleteById(int id);

	public List<Employee> findByFirstNameContainsAllIgnoreCase(String firstName);

	public List<Employee> sortByFirstNameDirection(Direction direction);

}