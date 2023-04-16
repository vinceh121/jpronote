package me.vinceh121.jpronote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.HttpGet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import me.vinceh121.jpronote.requester.LoginCAS;
import me.vinceh121.jpronote.requester.Requester;

public class JPronote {
	private static final ObjectMapper SESSION_INIT_MAPPER = new ObjectMapper();
	@SuppressWarnings("WeakerAccess")
	public static final String DEFAULT_USER_AGENT
			= "Mozilla/5.0 (X11; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/113.0";
	private final Requester requester;

	public JPronote(final String endpoint, final SessionType sessionType) {
		this(endpoint, sessionType, JPronote.DEFAULT_USER_AGENT);
	}

	@SuppressWarnings("WeakerAccess")
	public JPronote(final String endpoint, final SessionType sessionType, final String userAgent) {
		this.requester = new Requester(endpoint, sessionType, userAgent);
	}

	public Requester getRequester() {
		return this.requester;
	}

	public void login(final String username, final String password) throws AuthenticationException, IOException {
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
			this.requester.handshake(sessInit.get("h").asInt(), sessInit.get("MR").asText(),
					sessInit.get("ER").asText(), username, password, false);
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

	static {
		SESSION_INIT_MAPPER.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
				JsonParser.Feature.ALLOW_SINGLE_QUOTES);
	}
}
