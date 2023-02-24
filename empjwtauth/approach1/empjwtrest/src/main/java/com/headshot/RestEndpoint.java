package com.headshot;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("rest")
public class RestEndpoint {
	
	@Inject
	private Config config;

	@Inject
	@ConfigProperty(name = "welcome.message", defaultValue = "bla")
	private String msg;

	// if all resource not need token it will be null
	//@Inject
	//private JsonWebToken jwtPrincipal;

	@Inject
	@ConfigProperty(name = "securemsg", defaultValue = "hahahaha")
	private String secureMsg;

	@Path("/")
	@GET
	public String getMsg() {
		return msg;
	}

	@Path("/secureMsg")
	@GET
	@RolesAllowed({ "admin" })
	public String getSecureMsg() {
		return secureMsg;
	}
}
