package com.management.entity;

import java.util.Set;

import javax.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;

	@NonNull
	@Column(name = "user_username")
	private String username;

	@NonNull
	@Column(name = "user_password")
	private String password;

	@NonNull
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> userRoles;

	private static PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public void setPassword(String password) {
		this.password = bCryptPasswordEncoder.encode(password);
	}

	public static class UserBuilder {
		private String password;

		public UserBuilder password(String password) {
			this.password = bCryptPasswordEncoder.encode(password);
			return this;
		}
	}

}
