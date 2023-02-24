package com.headshot;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.List;

import org.jose4j.base64url.internal.apache.commons.codec.binary.Base64;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

/**
 * Hello world!
 *
 */
public class App {
	static PrivateKey loadKey(String PrivateKeyFile)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] key = Files.readAllBytes(Paths.get(PrivateKeyFile));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key));
		PrivateKey finalKey = keyFactory.generatePrivate(keySpec);
		return finalKey;
	}

	static String generateJwt(boolean isAdmin)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, JoseException {
//		String PrivateKeyFile = "D:\\private\\local\\eclipse_microprofile\\eclipse_microprofile\\empjwtauth\\approach1\\jwtclient\\src\\main\\resources\\rsa_2048_pri.pem";
		String PrivateKeyFile = "D:\\private\\local\\eclipse_microprofile\\eclipse_microprofile\\empjwtauth\\approach2\\empauthenticator\\src\\main\\resources\\rsaPrivateKey.pem";

		JwtClaims claims = new JwtClaims();
		claims.setIssuer("https://example.com");
		claims.setAudience("empjwtrest");
		claims.setClaim("name", "Headshot");
		claims.setClaim("preferred_username", "headshot");
		claims.setClaim("upn", "headshot@example.com");
		claims.setExpirationTimeMinutesInTheFuture(10);
		claims.setGeneratedJwtId(); // a unique identifier for the token
		claims.setIssuedAtToNow();
		claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
		claims.setSubject("subject");
		List<String> groups = Arrays.asList("guest");

		if (isAdmin) {
			groups.set(0, "admin");
		}
		claims.setStringListClaim("groups", groups);
		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(loadKey(PrivateKeyFile));
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		return jws.getCompactSerialization();

	}

	static void requestPublicResource(String token) throws URISyntaxException, IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET().uri(new URI("http://localhost:8080/empjwtrest/rest"))
				.build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		System.out.printf("Status {%d} => {%s}", response.statusCode(), response.body());
	}

	static void requestPrivateResource(String token) throws URISyntaxException, IOException, InterruptedException {
		System.out.println(token);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + token)
//		HttpRequest request = HttpRequest.newBuilder().GET().header("Authorization", token)
//				.uri(new URI("http://localhost:8080/empjwtrest/rest/secureMsg")).build();
				.uri(new URI("http://localhost:8080/empjwtrest/securerest/")).build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		if (response.statusCode() == 404) {
			System.out.println("not found");
		} else {

			System.out.printf("Status {%d} => {%s}", response.statusCode(), response.body());
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException,
			JoseException, URISyntaxException, InterruptedException {
		System.out.println("Welcome headshot");

//		String guestJWT = generateJwt(false);
//		String adminJWT = generateJwt(true);
//		String tempToken="eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tIiwiYXVkIjoiZW1wand0cmVzdCIsIm5hbWUiOiJoZWFkc2hvdCIsInByZWZlcnJlZF91c2VybmFtZSI6ImhlYWRzaG90IiwidXBuIjoiaGVhZHNob3RAZXhhbXBsZS5jb20iLCJleHAiOjE2NzcxNTQ3NzEsImp0aSI6IjMycTZieDJSV1ZyNWpmUm1keVpldFEiLCJpYXQiOjE2NzcxNTQxNzEsIm5iZiI6MTY3NzE1NDA1MSwic3ViIjoic3ViamVjdCIsImdyb3VwcyI6bnVsbH0.omLK3Uw4FUePX9KBTUCwGvFpMx3qPI24SumSOl8wWRBueo9ZpYfof9TrhiyYnld8JiBPrjTfon7x6c6OWn56fzCTV02PC_Gh394Nw6cbaBV00Hj9hCPkfgjO6Fo6Hb0GpXArm4QVx74KYuK7KgvR7rcHXwwdF6j9Z0bSi0F-9GQu0pLHv-aIi_AXgwwlx4XaHF4G_TnrIm9AuNyDn2buQLhIg3Y7CVF3YcwUB4R6ww7rCp7hjrktcSMVNucDN5x4JEmGN-_oG9HqcpFenx2cklkRMwV7DX4B4FvS6oVxoMebSTyRnAYGjZfWcMCyeMsMSAkFDvR4LNNV8hPdYpWvZQ";
		String tempToken="eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tIiwiYXVkIjoiZW1wand0cmVzdCIsIm5hbWUiOiJoZWFkc2hvdCIsInByZWZlcnJlZF91c2VybmFtZSI6ImhlYWRzaG90IiwidXBuIjoiaGVhZHNob3RAZXhhbXBsZS5jb20iLCJleHAiOjE2NzcxNTQ5NDgsImp0aSI6Im8xOGVXb0EzMm0wU1c3YW9DRG42aXciLCJpYXQiOjE2NzcxNTQzNDgsIm5iZiI6MTY3NzE1NDIyOCwic3ViIjoic3ViamVjdCIsImdyb3VwcyI6bnVsbH0.kEbtyENon9YUr7hsAf1qRW9R51TVLLDXevRbnPJBntNGFcxxeJz8kq0JerCI_1DI0u3Bh_Ke4icNCyRmYZqQHjI91yiekJvY3a3xl7PjA8h_R8tVk5fCYHhubKZELyDQQrn2i9-TI2znixwk-4 vf7dnTz0zEH1Svzs4xEruUjqyXKL4jqlWhEAEz0tXN0MWxKclXRXe_XpzszVuzA_p2vDC-o4y8oSdLPAlJT5xA0AZZ2bKRe2lIznUg8T6DrO6HGKJHtOe8wn31glBKm4go7koKY-Oup-c5anB8vnigct5gVIYq9igKSznoPUgqYn9_ESXz6bd9zlpDJzhXf5GBhw";

		requestPrivateResource(tempToken);
//		requestPrivateResource(adminJWT);
//		requestPrivateResource(guestJWT);
//		requestPublicResource(guestJWT);
//		requestPublicResource(adminJWT);

	}
}
