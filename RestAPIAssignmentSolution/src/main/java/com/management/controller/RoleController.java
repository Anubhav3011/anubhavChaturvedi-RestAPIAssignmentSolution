package com.management.controller;

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

import com.management.entity.Role;
import com.management.service.RoleService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Role Controller", tags = { "role-controller" }, description = "Role API")
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/save")
	public String save(@RequestParam("name") String name) {
		Role role = Role.builder().name(name).build();
		roleService.save(role);
		return "Successfully saved - " + role.toString();
	}

	@GetMapping("/findAll")
	public List<Role> findAll() {
		return roleService.findAll();
	}

	@GetMapping("/findById")
	public String findById(@RequestParam("roleId") int id) {
		if (roleService.existsById(id)) {
			return roleService.findById(id).toString();
		} else {
			return "No role found with id - " + id;
		}
	}

	@PutMapping("/updateById")
	public String updateById(@RequestBody Role role) {
		if (roleService.existsById(role.getId())) {
			roleService.save(role);
			return "Successfully updated - " + roleService.findById(role.getId()).toString();
		} else {
			return "No role found with id - " + role.getId();
		}
	}

	@DeleteMapping("/deleteById")
	public String deleteById(@RequestParam("roleId") int id) {
		if (roleService.existsById(id)) {
			roleService.deleteById(id);
			return "Deleted role id - " + id;
		} else
			return "No role found with id - " + id;
	}

}
