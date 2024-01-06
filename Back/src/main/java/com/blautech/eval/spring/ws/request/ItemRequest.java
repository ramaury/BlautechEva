package com.blautech.eval.spring.ws.request;

public class ItemRequest {

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ItemRequest [id=" + id + "]";
	}

}