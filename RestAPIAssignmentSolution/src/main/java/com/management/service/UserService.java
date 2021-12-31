package com.management.service;

import java.util.List;

import com.management.entity.User;

public interface UserService {

	public boolean existsById(int id);

	public List<User> findAll();

	public User findById(int id);

	public void save(User role);

	public void deleteById(int id);

	public User getByUsername(String username);

}
