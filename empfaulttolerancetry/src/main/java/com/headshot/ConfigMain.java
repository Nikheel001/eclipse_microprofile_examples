package com.headshot;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

// tested with tomee.version 9.0.0-RC1
@ApplicationPath("")
@Path("")
public class ConfigMain extends Application {

	@Inject
	private Config config;

	@Inject
	@Named("producedMsgA")
	MessageA msgB;

	@Inject
	@ConfigProperty(name = "welcome.message", defaultValue = "bla")
	private String someUrl;

	@Path("/")
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Counted(name = "base")
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
		System.out.println(msgB != null);
		System.out.println(msgB.getMsg());
		System.out.println(msgB.getSender());
		return someUrl;
	}

	@Named("producedMsgA")
	@Retry(maxRetries = 4, delay = 5000, retryOn = ConnectException.class)
	@jakarta.enterprise.inject.Produces
	@RequestScoped
	public MessageA getBeanViaHttpConn() throws ConnectException {
		MessageA obj = new MessageA();
		obj.setSender("headshot");
		obj.setMsg("works");
		System.out.println("===============================================================");
		try {
			String uri = "http://localhost:9900";
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
			HttpResponse<String> response;
			response = client.send(request, BodyHandlers.ofString());
//			System.out.println("===============================================================");
			System.out.println("Response in file:" + response.body());
//			System.out.println("===============================================================");
		} catch (ConnectException e) {
			System.out.println("it should retry");
			throw e;
		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
			System.out.println(
					"---------------------------------------------------------Failed---------------------------------------------------------");
			System.out.println(e.getMessage() + "=========" + e.getClass().getCanonicalName());

			return null;
		}
		System.out.println("===============================================================");
		return obj;
	}
}
