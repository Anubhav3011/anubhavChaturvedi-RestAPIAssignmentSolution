package com.management.entity;

import java.util.Set;

import javax.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
	@Setter(AccessLevel.NONE)
	@Column(name = "user_username", unique = true)
	private String username;

	@NonNull
	@Setter(AccessLevel.NONE)
	@Column(name = "user_password")
	private String password;

	@NonNull
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> userRoles;

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	private static PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public void setPassword(String password) {
		this.password = bCryptPasswordEncoder.encode(password);
	}

	public static class UserBuilder {
		private String username;
		private String password;

		public UserBuilder username(String username) {
			this.username = username.toLowerCase();
			return this;
		}

		public UserBuilder password(String password) {
			this.password = bCryptPasswordEncoder.encode(password);
			return this;
		}
	}

}
