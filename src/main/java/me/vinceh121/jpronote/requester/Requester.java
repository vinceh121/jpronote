package me.vinceh121.jpronote.requester;

import me.vinceh121.jpronote.Page;
import me.vinceh121.jpronote.SessionType;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Random;

public class Requester {
    private final String endpoint;
    private final SessionType sessionType;
    private final HttpClient httpClient;
    private boolean isAuthenticated = false;

    private int session;

    private byte[] key = "".getBytes();
    private byte[] iv = new byte[16];
    private int number = 1;
    private Page page = Page.HOMEPAGE;

    public Requester(String endpoint, SessionType sessionType, String userAgent) {
        this.endpoint = endpoint;
        this.sessionType = sessionType;
        this.httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .setUserAgent(userAgent)
                .build();
    }

    public void handshake(int session, String MR, String ER, String username, String password, boolean throughCAS) throws Exception {
        if (isAuthenticated) throw new IllegalStateException("Handshake already performed!");
        this.session = session;

        // --- STEP 1: Prepare encryption
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(MR, 16), new BigInteger(ER, 16));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey pub = factory.generatePublic(keySpec);

        new Random().nextBytes(iv);
        String UUID = Base64.getEncoder().encodeToString(encrypt(iv, pub));

        // --- STEP 2: FonctionParametres (Initial request)
        performRequest("FonctionParametres", new JSONObject().put("Uuid", UUID));

        // --- STEP 3: Authenticate
        JSONObject challengeJson = performRequest("Identification", new JSONObject()
                .put("demandeConnexionAppliMobile", false)
                .put("demandeConnexionAppliMobileJeton", false)
                .put("demandeConnexionAuto", false)
                .put("enConnexionAuto", false)
                .put("genreConnexion", 0)
                .put("genreEspace", sessionType.getType())
                .put("identifiant", username)
                .put("loginTokenSAV", "")
                .put("pourENT", throughCAS)
        );

        // --- STEP 4: Challenge
        String challenge = challengeJson.getString("challenge");
        String rnd = challengeJson.has("alea") ? challengeJson.getString("alea") : "";
        String hashed = hashPassword(password, rnd);
        byte[] key = (throughCAS ? hashed : (username + hashed)).getBytes(StandardCharsets.UTF_8);
        byte[] decryptedChallenge = decryptAES(Hex.decodeHex(challenge), key, iv);
        byte[] cleanedChallenge = removeBS(decryptedChallenge);
        String encryptedChallenge = Hex.encodeHexString(encryptAES(cleanedChallenge, key, iv));

        JSONObject identificationJson = performRequest("Authentification", new JSONObject()
                .put("connexion", 0)
                .put("espace", sessionType.getType())
                .put("challenge", encryptedChallenge)
        );

        String rawKey = identificationJson.getString("cle");
        this.key = makeLessStupid(decryptAES(Hex.decodeHex(rawKey), key, iv));
        isAuthenticated = true;

        // --- STEP 5: Navigate - YES BECAUSE IT'S NOT EVEN FINISHED YET FFS
        performRequest("Navigation", new JSONObject().put("ongletPrec", 7).put("onglet", 7));
    }

    public JSONObject navigate(Page toPage, JSONObject data) throws Exception {
        performRequest("Navigation", new JSONObject().put("ongletPrec", page.getId()).put("onglet", toPage.getId()));
        return performRequest(toPage.getPageName(), data);
    }

    private JSONObject performRequest(String function, JSONObject data) throws Exception {
        String numberStr = getNumber();
        String url = endpoint + "/appelfonction/" + sessionType.getType() + "/" + numberStr;
        JSONObject bodyData = new JSONObject().put("donnees", data);
        if (isAuthenticated) bodyData.put("_Signature_", new JSONObject().put("onglet", page.getId()));
        JSONObject body = new JSONObject()
                .put("nom", function)
                .put("session", session)
                .put("numeroOrdre", numberStr)
                .put("donneesSec", bodyData);

        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity(body.toString(), ContentType.APPLICATION_JSON));
        HttpResponse response = httpClient.execute(request);

        ByteArrayOutputStream execStream = new ByteArrayOutputStream();
        response.getEntity().writeTo(execStream);
        number += 2;
        return new JSONObject(execStream.toString()).getJSONObject("donneesSec").getJSONObject("donnees");
    }

    //-----------------------//
    //---- Encryption bs ----//
    //-----------------------//
    private String getNumber() throws Exception {
        byte[] plaintext = String.valueOf(number).getBytes();
        byte[] rndiv = MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(this.iv);
        IvParameterSpec iv = new IvParameterSpec(number > 1 ? rndiv : new byte[16]);
        SecretKeySpec keySpec = new SecretKeySpec(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(key), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        byte[] encrypted = cipher.doFinal(plaintext);
        return Hex.encodeHexString(encrypted, true);
    }

    private static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    private static byte[] encryptAES(byte[] plaintext, byte[] key, byte[] ivv) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(ivv));
        SecretKeySpec keySpec = new SecretKeySpec(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(key), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        return cipher.doFinal(plaintext);
    }

    private static byte[] decryptAES(byte[] plaintext, byte[] key, byte[] ivv) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(ivv));
        SecretKeySpec keySpec = new SecretKeySpec(MessageDigest.getInstance(MessageDigestAlgorithms.MD5).digest(key), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        return cipher.doFinal(plaintext);
    }

    private static byte[] removeBS(byte[] bytes) {
        String st = new String(bytes, StandardCharsets.UTF_8);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < st.length(); i++) {
            if (i % 2 == 0) s.append(st.charAt(i));
        }
        return s.toString().getBytes();
    }

    private static byte[] makeLessStupid(byte[] b) {
        String s = new String(b);
        String[] arr = s.split(",");
        byte[] out = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            out[i] = (byte) Integer.parseInt(arr[i]);
        }
        return out;
    }

    private static String hashPassword(String password, String rnd) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
        digest.update(rnd.getBytes());
        digest.update(password.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(digest.digest(), false);
    }

    //-----------------//
    //---- Getters ----//
    //-----------------//
    public String getEndpoint() {
        return endpoint;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }
}
