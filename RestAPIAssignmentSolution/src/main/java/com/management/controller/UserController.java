package com.management.controller;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

import com.management.entity.User;
import com.management.service.RoleService;
import com.management.service.UserService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "User Controller", tags = { "user-controller" }, description = "User API")
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@PostMapping("/save")
	public User save(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestBody String... userRoles) {
		if (!roleService.getByNames(userRoles).isEmpty()) {
			User user = User.builder().username(username).password(password)
					.userRoles(roleService.getByNames(userRoles)).build();
			if (!userService.existsById(user.getId())) {
				try {
					userService.save(user);
					return user;
				} catch (DataIntegrityViolationException e) {
					throw new ResponseStatusException(HttpStatus.CONFLICT,
							"User with username - " + user.getUsername() + " already exists");
				}
			} else {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"User with id - " + user.getId() + " already exists");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY,
					"No role(s) found with name(s) - " + Arrays.toString(userRoles));
		}
	}

	@GetMapping("/findAll")
	public List<User> findAll() {
		return userService.findAll();
	}

	@GetMapping("/findById")
	public User findById(@RequestParam("userId") int id) {
		try {
			return userService.findById(id);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with id - " + id);
		}
	}

	@PutMapping("/updateById")
	public User updateById(@RequestParam("userId") int id, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestBody String... userRoles) {
		if (userService.existsById(id)) {
			User user = userService.findById(id);
			if (!roleService.getByNames(userRoles).isEmpty()) {
				user.setUsername(username);
				user.setPassword(password);
				user.setUserRoles(roleService.getByNames(userRoles));
				try {
					userService.save(user);
					return user;
				} catch (DataIntegrityViolationException e) {
					throw new ResponseStatusException(HttpStatus.CONFLICT,
							"User with username - " + user.getUsername() + " already exists");
				}
			} else {
				throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY,
						"No role(s) found with name(s) - " + Arrays.toString(userRoles));
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with id - " + id);
		}
	}

	@DeleteMapping("/deleteById")
	public String deleteById(@RequestParam("userId") int id) {
		try {
			userService.deleteById(id);
			return "Deleted user id - " + id;
		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with id - " + id);
		}
	}

}
