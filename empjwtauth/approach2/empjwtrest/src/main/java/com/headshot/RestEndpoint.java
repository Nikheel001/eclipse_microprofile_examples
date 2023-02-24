package com.headshot;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("rest")
public class RestEndpoint {

	@Inject
	@ConfigProperty(name = "welcome.message", defaultValue = "bla")
	private String msg;

	@Path("/")
	@GET
	public String getMsg() {
		return msg;
	}
}
