package me.vinceh121.jpronote.requester;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;

public class LoginCAS {
	private final String url;
	private final String username;
	private final String password;
	private final String selection;

	public LoginCAS(final String url, final String username, final String password) {
		this(url, username, password, null);
	}

	public LoginCAS(final String url, final String username, final String password, final String selection) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.selection = selection;
	}

	public HttpResponse execute(final Requester requester) throws Exception {
		final HttpPost request = new HttpPost(this.url);
		final ArrayList<NameValuePair> list = this.fetchFields(requester);
		list.add(new BasicNameValuePair("username", this.username));
		list.add(new BasicNameValuePair("password", this.password));
		request.setEntity(new UrlEncodedFormEntity(list));
		return requester.getHttpClient().execute(request);
	}

	private ArrayList<NameValuePair> fetchFields(final Requester requester) throws IOException {
		String endpoint = this.url
				+ "?service="
				+ URLEncoder.encode(requester.getEndpoint() + requester.getSessionType().getLoginPath(), "UTF-8");
		if (this.selection != null) {
			endpoint += "&selection=" + this.selection;
		}
		final HttpGet request = new HttpGet(endpoint);
		final HttpResponse response = requester.getHttpClient().execute(request);

		final ByteArrayOutputStream execStream = new ByteArrayOutputStream();
		response.getEntity().writeTo(execStream);

		final ArrayList<NameValuePair> fields = new ArrayList<>();
		Jsoup.parse(execStream.toString()).getElementsByTag("input").forEach(input -> {
			final String name = input.attr("name");
			if (!"username".equals(name) && !"password".equals(name)) {
				fields.add(new BasicNameValuePair(name, input.attr("value")));
			}
		});
		return fields;
	}
}
