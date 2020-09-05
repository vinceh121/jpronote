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
	public static final String DEFAULT_USER_AGENT
			= "Mozilla/5.5 (Windows NT 11.0; Win64; x64; rv:71.0) Gecko/20150101 Firefox/72.2";
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
		final Pattern pattern = Pattern.compile("onload=.*h:'(\\d+).*,MR:'(\\w+).*ER:'(\\d+)");
		final Matcher matcher = pattern.matcher(execStream.toString());
		// noinspection ResultOfMethodCallIgnored
		matcher.find();
		try {
			final int session = Integer.parseInt(matcher.group(1));
			final String mr = matcher.group(2);
			final String er = matcher.group(3);
			this.requester.handshake(session, mr, er, username, password, false);
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
		final Pattern pattern = Pattern.compile("onload=.*h:'(\\d+).*,MR:'(\\w+).*ER:'(\\d+)");
		final Matcher matcher = pattern.matcher(execStream.toString());
		// noinspection ResultOfMethodCallIgnored
		matcher.find();
		try {
			this.requester.halfHandshake(Integer.parseInt(matcher.group(1)), matcher.group(2), matcher.group(3));
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
		System.out.println();
		final Pattern pattern = Pattern.compile("onload=.*h:'(\\d+).*,e:'(\\w+).*,f:'(\\w+).*,MR:'(\\w+).*ER:'(\\d+)");
		final Matcher matcher = pattern.matcher(execStream.toString());
		// noinspection ResultOfMethodCallIgnored
		matcher.find();
		try {
			this.requester.handshake(Integer.parseInt(matcher.group(1)), matcher.group(4), matcher.group(5),
					matcher.group(2), matcher.group(3), true);
		} catch (final Exception e) {
			final AuthenticationException exception
					= new AuthenticationException("Failed to authenticate with Pronote");
			exception.addSuppressed(e);
			throw exception;
		}
	}

}
