package com.management.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.management.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query(value = "SELECT * FROM Roles r WHERE r.role_name = :name", nativeQuery = true)
	public Role getByName(String name);

	@Query(value = "SELECT * FROM Roles r WHERE r.role_name in :names", nativeQuery = true)
	public Set<Role> getByNames(String... names);

}
