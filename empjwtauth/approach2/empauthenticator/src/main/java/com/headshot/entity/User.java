package com.headshot.entity;

import java.util.List;

public class User {
	private String name;
	private String password;
	private List<String> groups;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			User ref = (User) o;
			return ref.name.equals(name) && ref.password.equals(password);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int a = 0;
		a = (a * 13 + name.hashCode()) % 97;
		a = (a * 13 + password.hashCode()) % 97;
		return a;
	}
}
