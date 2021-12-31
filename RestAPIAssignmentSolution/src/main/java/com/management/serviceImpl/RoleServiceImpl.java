package com.management.serviceImpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.entity.Role;
import com.management.repository.RoleRepository;
import com.management.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public boolean existsById(int id) {
		return roleRepository.existsById(id);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(int id) {
		return roleRepository.findById(id).get();
	}

	@Override
	public void save(Role student) {
		roleRepository.save(student);
	}

	@Override
	public void deleteById(int id) {
		roleRepository.deleteById(id);
	}

	@Override
	public Role getByName(String name) {
		return roleRepository.getByName(name);
	}

	@Override
	public Set<Role> getByNames(String... name) {
		return roleRepository.getByNames(name);
	}

}
