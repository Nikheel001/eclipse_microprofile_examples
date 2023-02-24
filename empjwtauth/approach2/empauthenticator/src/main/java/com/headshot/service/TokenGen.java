package com.headshot.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jose4j.base64url.internal.apache.commons.codec.binary.Base64;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

import com.headshot.entity.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TokenGen {

	@Inject
	@ConfigProperty(name = "privatekey.location", defaultValue = "rsaPrivateKey.pem")
	String privateKey;

	public String genToken(User u) {
		try {
			byte[] key;
			try (InputStream is = this.getClass().getResourceAsStream(privateKey)) {
				key = is.readAllBytes();
			}
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key));
			PrivateKey pk = keyFactory.generatePrivate(keySpec);

			JwtClaims claims = new JwtClaims();
			claims.setIssuer("https://example.com");
			claims.setAudience("empjwtrest");
			claims.setClaim("name", u.getName());
			claims.setClaim("preferred_username", u.getName());
			claims.setClaim("upn", u.getName() + "@example.com");
			claims.setExpirationTimeMinutesInTheFuture(1);
			claims.setGeneratedJwtId(); // a unique identifier for the token
			claims.setIssuedAtToNow();
			claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
			claims.setSubject("subject");
			claims.setStringListClaim("groups", u.getGroups());
			JsonWebSignature jws = new JsonWebSignature();
//			System.out.println(claims.toJson());
//			System.out.println(claims.toString());
			jws.setPayload(claims.toJson());
			jws.setKey(pk);
			jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
			return jws.getCompactSerialization();
		} catch (JoseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
