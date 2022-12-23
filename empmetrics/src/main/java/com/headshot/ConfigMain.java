package com.headshot;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

// tested with tomee.version 9.0.0-M8
@ApplicationPath("")
@Path("")
public class ConfigMain extends Application {

	@Inject
	private Config config;

	@Inject
	@ConfigProperty(name = "welcome.message", defaultValue = "bla")
	private String someUrl;

	@Path("/")
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Counted(name="base")
	public String readConfig() {
		System.out.println(someUrl);
		return "1k";
	}

	@Path("/msg")
	@GET
	@Produces(MediaType.TEXT_HTML)
	@SimplyTimed(name = "msg")
	public String readConfig2() {
		System.out.println(config != null);
		System.out.println(someUrl);
		return someUrl;
	}
}
