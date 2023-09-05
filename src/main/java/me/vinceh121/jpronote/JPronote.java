package me.vinceh121.jpronote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import me.vinceh121.jpronote.requester.LoginCAS;
import me.vinceh121.jpronote.requester.Requester;
import me.vinceh121.jpronote.responses.UserSettings;

public class JPronote {
	private static final ObjectMapper SESSION_INIT_MAPPER = new ObjectMapper();
	private static final Pattern PAT_MR
			= Pattern.compile(Pattern.quote("const c_rsaPub_modulo_1024='") + "([a-fA-F0-9]+)" + Pattern.quote("'"));
	private static final Pattern PAT_ER
	= Pattern.compile(Pattern.quote("const c_rsaPub_exposant_1024='") + "([a-fA-F0-9]+)" + Pattern.quote("'"));
	public static final String DEFAULT_USER_AGENT
			= "Mozilla/5.0 (X11; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0";
	private final Requester requester;

	public JPronote(final String endpoint, final SessionType sessionType) {
		this(endpoint, sessionType, JPronote.DEFAULT_USER_AGENT);
	}

	public JPronote(final String endpoint, final SessionType sessionType, final String userAgent) {
		this.requester = new Requester(endpoint, sessionType, userAgent);
	}

	public Requester getRequester() {
		return this.requester;
	}

	public void login(final String username, final String password) throws AuthenticationException, IOException {
		final HttpGet req = new HttpGet(this.requester.getEndpoint() + this.requester.getSessionType().getLoginPath());
		final HttpResponse res = this.requester.getHttpClient().execute(req);

		// fetch session number in index.html
		final String index = EntityUtils.toString(res.getEntity());
		final Pattern pattern = Pattern.compile("onload=\"try \\{ Start \\((.*)\\) \\} catch");
		final Matcher matcher = pattern.matcher(index);
		// noinspection ResultOfMethodCallIgnored
		matcher.find();
		ObjectNode sessInit = (ObjectNode) SESSION_INIT_MAPPER.readTree(matcher.group(1));

		// fetch RSA keys in eleve.js
		String eleveJsPath = null;
		for (Element script : Jsoup.parse(index).getElementsByTag("script")) {
			if (script.attr("src").endsWith("/eleve.js")) {
				eleveJsPath = script.attr("src");
			}
		}

		if (eleveJsPath == null) {
			throw new IllegalStateException("Couldn't find path for eleve.js");
		}

		final HttpGet eleveReq = new HttpGet(this.requester.getEndpoint() + eleveJsPath);
		final HttpResponse eleveRes = this.requester.getHttpClient().execute(eleveReq);

		final String eleveJs = EntityUtils.toString(eleveRes.getEntity());

		final Matcher mrMatcher = PAT_MR.matcher(eleveJs);

		if (!mrMatcher.find()) {
			throw new IllegalStateException("Cannot find MR in eleve.js");
		}

		final String MR = mrMatcher.group(1);

		final Matcher erMatcher = PAT_ER.matcher(eleveJs);

		if (!erMatcher.find()) {
			throw new IllegalStateException("Cannot find MR in eleve.js");
		}

		final String ER = erMatcher.group(1);

		try {
			this.requester.handshake(sessInit.get("h").asInt(), MR, ER, username, password, false);
		} catch (final Exception e) {
			final AuthenticationException exception
					= new AuthenticationException("Failed to authenticate with Pronote");
			exception.initCause(e);
			throw exception;
		}
	}

	public void halfLogin() throws AuthenticationException, IOException {
		final HttpGet req = new HttpGet(this.requester.getEndpoint() + this.requester.getSessionType().getLoginPath());
		final HttpResponse res = this.requester.getHttpClient().execute(req);

		final ByteArrayOutputStream execStream = new ByteArrayOutputStream();
		res.getEntity().writeTo(execStream);
		final Pattern pattern = Pattern.compile("onload=\"try \\{ Start \\((.*)\\) \\} catch");
		final Matcher matcher = pattern.matcher(execStream.toString());
		// noinspection ResultOfMethodCallIgnored
		matcher.find();
		ObjectNode sessInit = (ObjectNode) SESSION_INIT_MAPPER.readTree(matcher.group(1));
		try {
			this.requester.halfHandshake(sessInit.get("h").asInt(), sessInit.get("MR").asText(),
					sessInit.get("ER").asText());
		} catch (final Exception e) {
			throw new AuthenticationException("Failed to authenticate with Pronote", e);
		}
	}

	public void loginCas(final String casUrl, final String username, final String password)
			throws AuthenticationException, IOException {
		this.loginCas(casUrl, username, password, null);
	}

	public void loginCas(final String casUrl, final String username, final String password, final String selection)
			throws AuthenticationException, IOException {
		final LoginCAS request = new LoginCAS(casUrl, username, password, selection);
		HttpResponse res;
		try {
			res = request.execute(this.requester);
		} catch (final Exception e) {
			final AuthenticationException exception = new AuthenticationException("Failed to login using CAS");
			exception.addSuppressed(e);
			throw exception;
		}

		if (res.getStatusLine().getStatusCode() == 401) {
			throw new AuthenticationException("Failed to login using CAS");
		}

		final ByteArrayOutputStream execStream = new ByteArrayOutputStream();
		res.getEntity().writeTo(execStream);
		final Pattern pattern = Pattern.compile("onload=\"try \\{ Start \\((.*)\\) \\} catch");
		final Matcher matcher = pattern.matcher(execStream.toString());
		// noinspection ResultOfMethodCallIgnored
		matcher.find();
		ObjectNode sessInit = (ObjectNode) SESSION_INIT_MAPPER.readTree(matcher.group(1));
		try {
			this.requester.handshake(sessInit.get("h").asInt(), sessInit.get("MR").asText(),
					sessInit.get("ER").asText(), sessInit.get("e").asText(), sessInit.get("f").asText(), true);
		} catch (final Exception e) {
			final AuthenticationException exception
					= new AuthenticationException("Failed to authenticate with Pronote");
			exception.addSuppressed(e);
			throw exception;
		}
	}

	public UserSettings fetchUserSettings() throws IOException, PronoteException {
		return this.requester.performParsedRequest("ParametresUtilisateur",
				this.requester.getMapper().createObjectNode(), UserSettings.class);
	}

	static {
		SESSION_INIT_MAPPER.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
				JsonParser.Feature.ALLOW_SINGLE_QUOTES);
	}
}
