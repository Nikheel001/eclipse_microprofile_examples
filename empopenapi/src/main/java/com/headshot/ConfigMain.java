package com.headshot;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// tested with tomee.version 9.0.0-M8
/**
 * 
 * @author nikheel.patel
 *
 */
@ApplicationPath("")
@Path("")
public class ConfigMain extends Application {

	@Inject
	private Config config;

	@Inject
	@ConfigProperty(name = "welcome.message", defaultValue = "bla")
	private String someUrl;

	private final Person[] people = { new Person("a", 5), new Person("b", 15), new Person("nikheel", 25) };

	@Path("/")
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Operation(summary = "simple get request")
	@APIResponse(description = "a string displaying 1k", content = @Content(mediaType = MediaType.TEXT_HTML, schema = @Schema(implementation = String.class)))
//	can use @APIResponse multiple times on a method 
	public String readConfig() {
		System.out.println(someUrl);
		return "1k";
	}

	@Path("/msg")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String readConfig2() {
		System.out.println(config != null);
		System.out.println(someUrl);
		return someUrl;
	}

	@Path("/people")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "get list of people")
	@APIResponse(description = "fetches list of people	", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Person.class)))
	public Person[] People() {
		return people;
	}

	@Path("/person/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "get specific person from list of people")
	@APIResponse(description = "fetches person from list of people based on given name", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Person.class)))
	@APIResponse(responseCode = "204", description = "person not found")
	public Response findPerson(@Parameter(description = "name of the user for which details needs to be fetched.", required = true) @PathParam("name") String name) {
		Person ref = null;
		for (Person p : people) {
			if (p.name.equals(name)) {
				ref = p;
				break;
			}
		}
		if (ref == null) {
			return Response.noContent().build();
		}
		return Response.ok(ref).build();
	}
}
