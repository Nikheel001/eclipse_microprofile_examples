package com.headshot;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisConfigSourcev2 implements ConfigSource {

	String redisConnURL = "redis://192.168.0.103";
	RedisClient redisClient;
	HashMap<String, String> configsFromredis = new HashMap<>();

	public RedisConfigSourcev2() {
		redisClient = RedisClient.create(redisConnURL);
		var conn = redisClient.connect();
		RedisCommands<String, String> syncCommands = conn.sync();
		configsFromredis.putAll(syncCommands.hgetall("config.empconfigredis"));
	}

	@Override
	public int getOrdinal() {
		return 500;
	}

	@Override
	public Set<String> getPropertyNames() {
		return configsFromredis.keySet();
	}

	@Override
	public String getValue(String propertyName) {
		System.out.println("from config2 , key : "+propertyName+ ", value : "+configsFromredis.get(propertyName));
		return configsFromredis.get(propertyName);
	}

	@Override
	public String getName() {
		return RedisConfigSourcev2.class.getSimpleName();
	}

}
