package com.headshot;

import java.util.HashMap;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@RegisterRestClient(baseUri = "http://localhost:9900")
public interface TryRestCall {
	
	@GET
	@Path("/")
	String greet();

	@GET
	@Path("/")
	HashMap<String, Object> greet2();
}
