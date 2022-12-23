package com.headshot;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;


// tested with tomee.version 9.0.0-M8
// tested with openliberty.maven.version 3.5.1
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
	@Produces("plain/text")
	public String readConfig() {
		System.out.println(someUrl);
		return "0k";
	}

	@Path("/msg")
	@GET
	@Produces("plain/text")
	public String readConfig2() {
		System.out.println(config != null);
		System.out.println(someUrl);
		return someUrl;
	}
	
}
