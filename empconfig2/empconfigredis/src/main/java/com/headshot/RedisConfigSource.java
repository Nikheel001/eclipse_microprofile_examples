package com.headshot;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisConfigSource implements ConfigSource {

	String redisConnURL = "redis://192.168.0.103";
	RedisClient redisClient;

	public RedisConfigSource() {
		redisClient = RedisClient.create(redisConnURL);
	}

	private StatefulRedisConnection<String, String> getRedisConfigCon() {
		return redisClient.connect();
	}

	@Override
	public int getOrdinal() {
		return 502;
	}

	@Override
	public Set<String> getPropertyNames() {
		var newKeys = new HashSet<String>();
		var connection = getRedisConfigCon();
		RedisCommands<String, String> syncCommands = connection.sync();
		newKeys.addAll(syncCommands.hkeys("config.empconfigredis"));
		connection.close();
		return newKeys;
	}

	@Override
	public String getValue(String propertyName) {
		String res;
		var connection = getRedisConfigCon();
		RedisCommands<String, String> syncCommands = connection.sync();
		res = syncCommands.hget("config.empconfigredis", propertyName);
		System.out.println("from config1 , key : " + propertyName + ", value : " + res);
		connection.close();
		return res;
	}

	@Override
	public String getName() {
		return RedisConfigSource.class.getSimpleName();
	}

}
