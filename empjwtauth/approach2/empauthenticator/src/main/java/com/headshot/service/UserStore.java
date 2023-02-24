package com.headshot.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.headshot.entity.User;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@ApplicationScoped
public class UserStore {

	@Inject
	private Config config;

	@Inject
	@ConfigProperty(name = "userstore.location", defaultValue = "users.json")
	private String store;

	private HashMap<Integer, User> users;

	@PostConstruct
	private void init() {
		users = new HashMap<>();
		Jsonb jsonb = JsonbBuilder.create();
		for (User u : (ArrayList<User>) jsonb.fromJson(this.getClass().getResourceAsStream(store),
				new ArrayList<User>() {
				}.getClass().getGenericSuperclass())) {
			users.put(u.hashCode(), u);
		}
	}

	public boolean contains(User o) {
		return users.containsKey(o.hashCode());
	}

	public User get(int key) {
		return users.get(key);
	}
}
