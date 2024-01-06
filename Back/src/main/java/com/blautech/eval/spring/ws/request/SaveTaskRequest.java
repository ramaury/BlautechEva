package com.blautech.eval.spring.ws.request;


public class SaveTaskRequest {

	private long id;

	private long user;
	private String title;
	private String description;
	private int status;

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

	@Override
	public String toString() {
		return "SaveTaskRequest [id=" + id + ", user=" + user + ", title=" + title + ", description=" + description
				+ ", status=" + status + "]";
	}

}
