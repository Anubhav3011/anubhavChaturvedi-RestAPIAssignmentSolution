package com.management.service;

import java.util.List;
import java.util.Set;

import com.management.entity.Role;

public interface RoleService {

	public boolean existsById(int id);

	public List<Role> findAll();

	public Role findById(int id);

	public void save(Role role);

	public void deleteById(int id);

	public Role getByName(String name);

	public Set<Role> getByNames(String... name);

}
