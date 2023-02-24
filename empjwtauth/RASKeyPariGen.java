/**
* RSA and ECDSA Public Keys may be formatted in any of the following formats, specified in order of precedence:
* 1. Public Key Cryptography Standards #8 (PKCS#8) PEM
* 2. JSON Web Key (JWK)
* 3. JSON Web Key Set (JWKS)
* 4. JSON Web Key (JWK) Base64 URL encoded
* 5. JSON Web Key Set (JWKS) Base64 URL encoded
* Attempts to parse the Public Key text will proceed in the order specified above until a valid Public
* Key can be derived.
* Support for other Public Key formats such as PKCS#1, SSH2, or OpenSSH Public Key format is
* considered optional.
*/

package experiment_java_se;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class RASKeyPariGen {

	static String alg = "RSA";
	static int sizeInBits = 2048;

	public static void keygen(String privateKeyFile, String publicKeyFile) throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(alg);
		kpg.initialize(sizeInBits); // 2048
		KeyPair kp = kpg.generateKeyPair();

		try (FileOutputStream fo = new FileOutputStream(new File(privateKeyFile))) {
			fo.write("-----BEGIN PRIVATE KEY-----".getBytes());
			fo.write(System.lineSeparator().getBytes());
			fo.write(Base64.getMimeEncoder().encode(kp.getPrivate().getEncoded()));
			fo.write(System.lineSeparator().getBytes());
			fo.write("-----END PRIVATE KEY-----".getBytes());
			fo.write(System.lineSeparator().getBytes());
		} catch (FileNotFoundException e) {
			System.out.println("invalid private key file location");
		} catch (IOException e) {
			System.out.println("check private key file read write access");
		}

		try (FileOutputStream fo = new FileOutputStream(new File(publicKeyFile))) {
			fo.write("-----BEGIN PUBLIC KEY-----".getBytes());
			fo.write(System.lineSeparator().getBytes());
			fo.write(Base64.getMimeEncoder().encode(kp.getPublic().getEncoded()));
			fo.write(System.lineSeparator().getBytes());
			fo.write("-----END PUBLIC KEY-----".getBytes());
			fo.write(System.lineSeparator().getBytes());
			fo.write(System.lineSeparator().getBytes());
		} catch (FileNotFoundException e) {
			System.out.println("invalid public key file location");
		} catch (IOException e) {
			System.out.println("check public key file read write access");
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {

		//PKCS#8
		String privateKeyFile = "rsa_2048_pri.pem";
		String publicKeyFile = "rsa_2048_pub.pem";

		keygen(privateKeyFile, publicKeyFile);
	}

}