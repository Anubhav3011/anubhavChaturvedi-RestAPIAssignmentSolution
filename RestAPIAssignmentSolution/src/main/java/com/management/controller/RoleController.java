package com.management.controller;

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
	public Role save(@RequestParam("name") String name) {
		Role role = Role.builder().name(name).build();
		if (!roleService.existsById(role.getId())) {
			try {
				roleService.save(role);
				return role;
			} catch (DataIntegrityViolationException e) {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"Role with name - " + role.getName() + " already exists");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Role with id - " + role.getId() + " already exists");
		}
	}

	@GetMapping("/findAll")
	public List<Role> findAll() {
		return roleService.findAll();
	}

	@GetMapping("/findById")
	public Role findById(@RequestParam("roleId") int id) {
		try {
			return roleService.findById(id);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No role found with id - " + id);
		}
	}

	@PutMapping("/updateById")
	public Role updateById(@RequestBody Role role) {
		if (roleService.existsById(role.getId())) {
			try {
				roleService.save(role);
				return role;
			} catch (DataIntegrityViolationException e) {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"Role with name - " + role.getName() + " already exists");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No role found with id - " + role.getId());
		}
	}

	@DeleteMapping("/deleteById")
	public String deleteById(@RequestParam("roleId") int id) {
		try {
			roleService.deleteById(id);
			return "Deleted role id - " + id;
		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No role found with id - " + id);
		}
	}

}
