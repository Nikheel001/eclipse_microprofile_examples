package com.headshot;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("rest/")
public class RestEndpoint {
	
	@Inject
	private Config config;

	@Inject
	@ConfigProperty(name = "config.welcome.message", defaultValue="bla")
//	jakarta.inject.Provider<String> someUrl;
	String someUrl;
	
	
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
//		return someUrl.get();
		return someUrl;
	}

}
