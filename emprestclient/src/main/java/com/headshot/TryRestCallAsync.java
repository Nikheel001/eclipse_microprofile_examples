package com.headshot;

import java.util.HashMap;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@RegisterRestClient(baseUri = "http://localhost:9900")
public interface TryRestCallAsync {
	@GET
	@Path("/")
	CompletionStage<String> greet();

	@GET
	@Path("/")
	CompletionStage<HashMap<String, Object>> greet2();
}
