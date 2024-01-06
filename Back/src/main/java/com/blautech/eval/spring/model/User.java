package com.blautech.eval.spring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "fullname")
	private String fullname;

	public User() {

	}

	public User(String name, String fullname) {
		this.name = name;
		this.fullname = fullname;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", fullname=" + fullname + "]";
	}

}
