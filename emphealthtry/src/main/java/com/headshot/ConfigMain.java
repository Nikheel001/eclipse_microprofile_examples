package com.headshot;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
//import org.eclipse.microprofile.health.Readiness;
//import org.eclipse.microprofile.health.Startup;

import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;

// tested with tomee.version 9.0.0-M8
@ApplicationPath("")
@Path("")
@Liveness
//@Readiness
//@Startup
public class ConfigMain extends Application implements HealthCheck {

	@Inject
	private Config config;

	@Inject
	@ConfigProperty(name = "welcome.message", defaultValue = "bla")
	private String someUrl;

	@Path("/")
	@GET
	@Produces("plain/text")
	public String readConfig() {
		System.out.println(someUrl);
		return "1k";
	}

	@Path("/msg")
	@GET
	@Produces("plain/text")
	public String readConfig2() {
		System.out.println(config != null);
		System.out.println(someUrl);
		return someUrl;
	}

	@Override
	public HealthCheckResponse call() {
//		test something if needed and update status
		if (config == null) {
			return HealthCheckResponse.down("msg");
		} else {
			return HealthCheckResponse.up("ConfigMain endpoins are up");
		}
	}

}
