package me.vinceh121.jpronote.requester;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.protocol.HttpContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import me.vinceh121.jpronote.Page;
import me.vinceh121.jpronote.PronoteErrorHandler;
import me.vinceh121.jpronote.PronoteException;
import me.vinceh121.jpronote.SessionType;

public class Requester {
	private final ObjectMapper mapper = new ObjectMapper();
	private final String endpoint;
	private final SessionType sessionType;
	private final HttpClient httpClient;
	private boolean isAuthenticated = false;
	private JsonNode parameters, identificationJson;

	private int session;

	private byte[] key = "".getBytes();
	private byte[] iv = new byte[16];
	private int number = 1;
	private Page page = Page.HOMEPAGE;

	public Requester(final String endpoint, final SessionType sessionType, final String userAgent) {
		this.endpoint = endpoint;
		this.sessionType = sessionType;
		this.httpClient = HttpClientBuilder.create()
				.setRedirectStrategy(new LaxRedirectStrategy())
				.setUserAgent(userAgent)
				.build();
	}

	public void halfHandshake(final int session, final String MR, final String ER) throws Exception {
		if (this.isAuthenticated) {
			throw new IllegalStateException("Handshake already performed!");
		}
		this.session = session;

		// --- STEP 1: Prepare encryption
		final RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(MR, 16), new BigInteger(ER, 16));
		final KeyFactory factory = KeyFactory.getInstance("RSA");
		final PublicKey pub = factory.generatePublic(keySpec);

		new SecureRandom().nextBytes(this.iv);
		final String UUID = Base64.getEncoder().encodeToString(Requester.encrypt(this.iv, pub));

		// --- STEP 2: FonctionParametres (Initial request)
		this.parameters = this.performRequest("FonctionParametres", this.mapper.createObjectNode().put("Uuid", UUID));

	}

	public void handshake(final int session, final String MR, final String ER, final String username,
			final String password, final boolean throughCAS) throws Exception {
		if (this.isAuthenticated) {
			throw new IllegalStateException("Handshake already performed!");
		}
		this.session = session;

		// --- STEP 1: Prepare encryption
		final RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(MR, 16), new BigInteger(ER, 16));
		final KeyFactory factory = KeyFactory.getInstance("RSA");
		final PublicKey pub = factory.generatePublic(keySpec);

		new SecureRandom().nextBytes(this.iv);
		final String UUID = Base64.getEncoder().encodeToString(Requester.encrypt(this.iv, pub));

		// --- STEP 2: FonctionParametres (Initial request)
		this.parameters = this.performRequest("FonctionParametres", this.mapper.createObjectNode().put("Uuid", UUID));

		// --- STEP 3: Authenticate
		final JsonNode challengeJson = this.performRequest("Identification",
				this.mapper.createObjectNode()
						.put("demandeConnexionAppliMobile", false)
						.put("demandeConnexionAppliMobileJeton", false)
						.put("demandeConnexionAuto", false)
						.put("enConnexionAuto", false)
						.put("genreConnexion", 0)
						.put("genreEspace", this.sessionType.getType())
						.put("identifiant", username)
						.put("loginTokenSAV", "")
						.put("pourENT", throughCAS));

		// --- STEP 4: Challenge
		final String challenge = challengeJson.get("challenge").asText();
		final String rnd = challengeJson.has("alea") ? challengeJson.get("alea").asText() : "";
		final String hashed = Requester.hashPassword(password, rnd);
		final byte[] key = (throughCAS ? hashed : username + hashed).getBytes(StandardCharsets.UTF_8);
		// byte[] key = Hex.decodeHex(throughCAS ? hashed : (username + hashed));
		// byte[] key = Hex.decodeHex(hashed);
		final byte[] decryptedChallenge = Requester.decryptAES(Hex.decodeHex(challenge), key, this.iv);
		final byte[] cleanedChallenge = Requester.removeBS(decryptedChallenge);
		final String encryptedChallenge = Hex.encodeHexString(Requester.encryptAES(cleanedChallenge, key, this.iv));

		this.identificationJson = this.performRequest("Authentification",
				this.mapper.createObjectNode()
						.put("connexion", 0)
						.put("espace", this.sessionType.getType())
						.put("challenge", encryptedChallenge));

		final String rawKey = this.identificationJson.get("cle").asText();
		this.key = Requester.makeLessStupid(Requester.decryptAES(Hex.decodeHex(rawKey), key, this.iv));
		this.isAuthenticated = true;

		// --- STEP 5: Navigate - YES BECAUSE IT'S NOT EVEN FINISHED YET FFS
		this.performRequest("Navigation", this.mapper.createObjectNode().put("ongletPrec", 7).put("onglet", 7));
	}

	public JsonNode navigate(final Page toPage, final JsonNode data) throws Exception {
		this.page = toPage;
		this.performRequest("Navigation",
				this.mapper.createObjectNode().put("ongletPrec", this.page.getId()).put("onglet", toPage.getId()));
		return this.performRequest(toPage.getPageName(), data);
	}

	public JsonNode performRequest(final String function, final JsonNode data) throws Exception {
		final String numberStr = this.getNumber();
		final String url = this.endpoint + "/appelfonction/" + this.sessionType.getType() + "/" + numberStr;
		final ObjectNode bodyData = this.mapper.createObjectNode();
		bodyData.set("donnees", data);
		if (this.isAuthenticated) {
			bodyData.set("_Signature_", this.mapper.createObjectNode().put("onglet", this.page.getId()));
		}
		final JsonNode body = this.mapper.createObjectNode()
				.put("nom", function)
				.put("session", this.session)
				.put("numeroOrdre", numberStr)
				.set("donneesSec", bodyData);

		final HttpPost request = new HttpPost(url);
		request.setEntity(new StringEntity(body.toString(), ContentType.APPLICATION_JSON));
		final HttpResponse response = this.httpClient.execute(request);

		final ByteArrayOutputStream execStream = new ByteArrayOutputStream();
		response.getEntity().writeTo(execStream);
		this.number += 2;
		final JsonNode answer = this.mapper.readTree(execStream.toString());
		if (answer.has("Erreur")) {
			throw new PronoteException(PronoteErrorHandler.getErrorMessage(answer.get("Erreur").asInt()));
		}
		return answer.get("donneesSec").get("donnees");
	}

	public JsonNode getParameters() {
		return this.parameters;
	}

	public JsonNode getAuthJson() {
		return this.identificationJson;
	}

	// -----------------------//
	// ---- Encryption bs ----//
	// -----------------------//
	private String getNumber() throws Exception {
		final byte[] plaintext = String.valueOf(this.number).getBytes();
		final byte[] rndiv = MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(this.iv);
		final IvParameterSpec iv = new IvParameterSpec(this.number > 1 ? rndiv : new byte[16]);
		final SecretKeySpec keySpec
				= new SecretKeySpec(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(this.key), "AES");
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
		final byte[] encrypted = cipher.doFinal(plaintext);
		return Hex.encodeHexString(encrypted, true);
	}

	private static byte[] encrypt(final byte[] data, final PublicKey publicKey) throws Exception {
		final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	private static byte[] encryptAES(final byte[] plaintext, final byte[] key, final byte[] ivv) throws Exception {
		final IvParameterSpec iv
				= new IvParameterSpec(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(ivv));
		final SecretKeySpec keySpec
				= new SecretKeySpec(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(key), "AES");
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
		return cipher.doFinal(plaintext);
	}

	private static byte[] decryptAES(final byte[] plaintext, final byte[] key, final byte[] ivv) throws Exception {
		final IvParameterSpec iv
				= new IvParameterSpec(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(ivv));
		final SecretKeySpec keySpec
				= new SecretKeySpec(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(key), "AES");
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
		return cipher.doFinal(plaintext);
	}

	private static byte[] removeBS(final byte[] bytes) {
		final String st = new String(bytes, StandardCharsets.UTF_8);
		final StringBuilder s = new StringBuilder();
		for (int i = 0; i < st.length(); i++) {
			if (i % 2 == 0) {
				s.append(st.charAt(i));
			}
		}
		return s.toString().getBytes();
	}

	private static byte[] makeLessStupid(final byte[] b) {
		final String s = new String(b);
		final String[] arr = s.split(",");
		final byte[] out = new byte[arr.length];
		for (int i = 0; i < arr.length; i++) {
			out[i] = (byte) Integer.parseInt(arr[i]);
		}
		return out;
	}

	private static String hashPassword(final String password, final String rnd) throws NoSuchAlgorithmException {
		final MessageDigest digest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
		digest.update(rnd.getBytes());
		digest.update(password.getBytes(StandardCharsets.UTF_8));
		return Hex.encodeHexString(digest.digest(), false);
	}

	// -----------------//
	// ---- Getters ----//
	// -----------------//
	public String getEndpoint() {
		return this.endpoint;
	}

	public SessionType getSessionType() {
		return this.sessionType;
	}

	public HttpClient getHttpClient() {
		return this.httpClient;
	}
}
