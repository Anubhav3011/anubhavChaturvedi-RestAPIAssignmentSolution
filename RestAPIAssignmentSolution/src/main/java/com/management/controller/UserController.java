package com.management.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public String save(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestBody String... userRoles) {
		if (!roleService.getByNames(userRoles).isEmpty()) {
			User user = User.builder().username(username).password(password)
					.userRoles(roleService.getByNames(userRoles)).build();
			userService.save(user);
			return "Successfully saved - " + user.toString();
		} else
			return "No roles found with names - " + Arrays.toString(userRoles);
	}

	@GetMapping("/findAll")
	public List<User> findAll() {
		return userService.findAll();
	}

	@GetMapping("/findById")
	public String findById(@RequestParam("userId") int id) {
		if (userService.existsById(id)) {
			return userService.findById(id).toString();
		} else {
			return "No user found with id - " + id;
		}
	}

	@PutMapping("/updateById")
	public String updateById(@RequestParam("userId") int id, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestBody String... userRoles) {
		if (userService.existsById(id)) {
			if (!roleService.getByNames(userRoles).isEmpty()) {
				User user = userService.findById(id);
				user.setUsername(username);
				user.setPassword(password);
				user.setUserRoles(roleService.getByNames(userRoles));
				userService.save(user);
				return "Successfully updated - " + userService.findById(user.getId()).toString();
			} else
				return "No roles found with names - " + Arrays.toString(userRoles);
		} else {
			return "No user found with id - " + id;
		}
	}

	@DeleteMapping("/deleteById")
	public String deleteById(@RequestParam("userId") int id) {
		if (userService.existsById(id)) {
			userService.deleteById(id);
			return "Deleted user id - " + id;
		} else
			return "No user found with id - " + id;
	}

}
