package com.management.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private int id;

	@NonNull
	@Setter(AccessLevel.NONE)
	@Column(name = "role_name", unique = true)
	private String name;

	public void setName(String name) {
		this.name = name.toUpperCase();
	}

	public static class RoleBuilder {
		private String name;

		public RoleBuilder name(String name) {
			this.name = name.toUpperCase();
			return this;
		}
	}

}
