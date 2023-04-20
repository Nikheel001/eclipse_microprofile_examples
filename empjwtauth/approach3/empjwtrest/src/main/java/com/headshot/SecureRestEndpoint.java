package com.headshot;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("securerest")
public class SecureRestEndpoint {

	@Inject
	@ConfigProperty(name = "securemsg", defaultValue = "hahahaha")
	private String secureMsg;

//	 if a resource does not need token it will be null
	@Inject
	private JsonWebToken jwtPrincipal;

	@Path("/")
	@GET
	@RolesAllowed({ "admin" })
	public String getSecureMsg() {
		return secureMsg;
	}

	@Path("/roles")
	@GET
	@RolesAllowed({ "guest", "admin" })
	public Response getRole() {
		if (jwtPrincipal != null) {
			return Response.ok(jwtPrincipal.getGroups()).build();
		}
		return Response.noContent().build();
	}

	@Path("/username")
	@GET
	@RolesAllowed({ "guest", "admin" })
	public Response getUsername() {
		if (jwtPrincipal != null) {
			return Response.ok(jwtPrincipal.getClaim("name")).build();
		}
		return Response.noContent().build();
	}
}
