package com.headshot;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

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

	@Inject
	@RestClient
	TryRestCall trc;

	@Inject
	@RestClient
	TryRestCallAsync trc2;

	@Path("/")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String readConfig() {
		System.out.println(someUrl);
		if (trc != null) {
			System.out.println(trc.greet());
			System.out.println("===================");
			System.out.println(trc.greet2());
			System.out.println("===================");
		}

		if (trc2 != null) {
			trc2.greet().thenAccept(i -> System.out.println(i));
			System.out.println("===================");
			trc2.greet2().thenAccept(j -> System.out.println(j));
			System.out.println("===================");
		}
		return "1k";
	}
}
