package com.blautech.eval.spring.ws.request;

public class SaveUserRequest {

	private long id;

	private String name;
	private String fullname;

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
		return "SaveUserRequest [id=" + id + ", name=" + name + ", fullname=" + fullname + "]";
	}	
}
