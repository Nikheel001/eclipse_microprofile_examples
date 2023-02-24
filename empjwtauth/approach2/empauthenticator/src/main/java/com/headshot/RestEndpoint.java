package com.headshot;

import com.headshot.entity.User;
import com.headshot.service.TokenGen;
import com.headshot.service.UserStore;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("auth")
public class RestEndpoint {

	@Inject
	UserStore store;

	@Inject
	TokenGen tokenGen;

	@Path("/token")
	@POST
	public Response autenticateAndGenToken(JsonObject creds) {
		if (creds.containsKey("name") && creds.containsKey("password") && !creds.isNull("password")
				&& !creds.isNull("name")) {
			User u = new User();
			u.setName(creds.getString("name"));
			u.setPassword(creds.getString("password"));
			if (store.contains(u)) {
				return Response.accepted(tokenGen.genToken(store.get(u.hashCode()))).build();
			} else {
				System.out.println("not found in store");
			}
		}
		return Response.notModified().build();
	}

	// how to handle token expire?
//	@DELETE
//	@Path("/logout")
//	public Response logout() {
//		return msg;
//	}
}
