package me.vinceh121.jpronote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.HttpGet;

import me.vinceh121.jpronote.requester.LoginCAS;
import me.vinceh121.jpronote.requester.Requester;

public class JPronote {
	@SuppressWarnings("WeakerAccess")
	public static final String DEFAULT_USER_AGENT = "Mozilla/5.5 (Windows NT 11.0; Win64; x64; rv:71.0) Gecko/20150101 Firefox/72.2";
	private final Requester requester;

	public JPronote(String endpoint, SessionType sessionType) {
		this(endpoint, sessionType, DEFAULT_USER_AGENT);
	}

	@SuppressWarnings("WeakerAccess")
	public JPronote(String endpoint, SessionType sessionType, String userAgent) {
		requester = new Requester(endpoint, sessionType, userAgent);
	}

	public Requester getRequester() {
		return requester;
	}

	public void login(String username, String password) throws AuthenticationException, IOException {
		HttpGet req = new HttpGet(requester.getEndpoint() + requester.getSessionType().getLoginPath());
		HttpResponse res = requester.getHttpClient().execute(req);

		ByteArrayOutputStream execStream = new ByteArrayOutputStream();
		res.getEntity().writeTo(execStream);
		Pattern pattern = Pattern.compile("onload=.*h:'(\\d+).*,MR:'(\\w+).*ER:'(\\d+)");
		Matcher matcher = pattern.matcher(execStream.toString());
		// noinspection ResultOfMethodCallIgnored
		matcher.find();
		try {
			requester.handshake(Integer.parseInt(matcher.group(1)), matcher.group(2), matcher.group(3), username,
					password, false);
		} catch (Exception e) {
			AuthenticationException exception = new AuthenticationException("Failed to authenticate with Pronote");
			exception.initCause(e);
			throw exception;
		}
	}

	public void halfLogin(String username, String password) throws AuthenticationException, IOException {
		HttpGet req = new HttpGet(requester.getEndpoint() + requester.getSessionType().getLoginPath());
		HttpResponse res = requester.getHttpClient().execute(req);

		ByteArrayOutputStream execStream = new ByteArrayOutputStream();
		res.getEntity().writeTo(execStream);
		Pattern pattern = Pattern.compile("onload=.*h:'(\\d+).*,MR:'(\\w+).*ER:'(\\d+)");
		Matcher matcher = pattern.matcher(execStream.toString());
		// noinspection ResultOfMethodCallIgnored
		matcher.find();
		try {
			requester.halfHandshake(Integer.parseInt(matcher.group(1)), matcher.group(2), matcher.group(3));
		} catch (Exception e) {
			AuthenticationException exception = new AuthenticationException("Failed to authenticate with Pronote");
			exception.initCause(e);
			throw exception;
		}
	}

	public void loginCas(String casUrl, String username, String password) throws AuthenticationException, IOException {
		loginCas(casUrl, username, password, null);
	}

	public void loginCas(String casUrl, String username, String password, String selection)
			throws AuthenticationException, IOException {
		final LoginCAS request = new LoginCAS(casUrl, username, password, selection);
		HttpResponse res;
		try {
			res = request.execute(requester);
		} catch (Exception e) {
			AuthenticationException exception = new AuthenticationException("Failed to login using CAS");
			exception.addSuppressed(e);
			throw exception;
		}

		if (res.getStatusLine().getStatusCode() == 401) {
			throw new AuthenticationException("Failed to login using CAS");
		}

		ByteArrayOutputStream execStream = new ByteArrayOutputStream();
		res.getEntity().writeTo(execStream);
		System.out.println();
		Pattern pattern = Pattern.compile("onload=.*h:'(\\d+).*,e:'(\\w+).*,f:'(\\w+).*,MR:'(\\w+).*ER:'(\\d+)");
		Matcher matcher = pattern.matcher(execStream.toString());
		// noinspection ResultOfMethodCallIgnored
		matcher.find();
		try {
			requester.handshake(Integer.parseInt(matcher.group(1)), matcher.group(4), matcher.group(5),
					matcher.group(2), matcher.group(3), true);
		} catch (Exception e) {
			AuthenticationException exception = new AuthenticationException("Failed to authenticate with Pronote");
			exception.addSuppressed(e);
			throw exception;
		}
	}

}
