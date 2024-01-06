package com.blautech.eval.spring.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "user")
	private long user;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private int status;

	@Column(name = "creationdate")
	private LocalDateTime creationdate;

	public Task() {

	}

	public Task(long user, String title, String description, int status) {
		this.user = user;
		this.title = title;
		this.description = description;
		this.status = status;
		this.creationdate = LocalDateTime.now(); 
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(LocalDateTime creationdate) {
		this.creationdate = creationdate;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", user=" + user + ", title=" + title + ", description=" + description + ", status="
				+ status + ", creationdate=" + creationdate + "]";
	}

}
