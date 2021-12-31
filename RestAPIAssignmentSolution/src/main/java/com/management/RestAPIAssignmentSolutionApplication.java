package com.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.management.entity.Role;
import com.management.entity.Employee;
import com.management.entity.User;
import com.management.service.EmployeeService;
import com.management.service.RoleService;
import com.management.service.UserService;

@SpringBootApplication(scanBasePackages = "com.management")
public class RestAPIAssignmentSolutionApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(RestAPIAssignmentSolutionApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(RestAPIAssignmentSolutionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Adding 2 roles - ADMIN, USER
		roleService.save(Role.builder().name("ADMIN").build());
		roleService.save(Role.builder().name("USER").build());

		// Adding 2 users - admin, user having password & role same as their username
		userService.save(
				User.builder().username("admin").password("admin").userRoles(roleService.getByNames("ADMIN")).build());
		userService.save(
				User.builder().username("user").password("user").userRoles(roleService.getByNames("USER")).build());

		// Adding 4 records as mentioned in the problem statement
		employeeService.save(Employee.builder().firstName("Harshit").lastName("Choudhary")
				.email("Harshit@greatlearning.in").build());
		employeeService
				.save(Employee.builder().firstName("Ankit").lastName("Garg").email("Ankit@greatlearning.in").build());
		employeeService.save(
				Employee.builder().firstName("Monica").lastName("Sharma").email("Monica@greatlearning.in").build());
		employeeService.save(
				Employee.builder().firstName("Bhavya").lastName("Shetty").email("Bhavya@greatlearning.in").build());

	}

}
