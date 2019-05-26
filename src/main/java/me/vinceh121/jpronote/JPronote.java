package me.vinceh121.jpronote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class JPronote {
	public static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.128 Electron/4.1.5 Safari/537.36";
	private String token, endPoint, sessionId, userAgent;
	private boolean isConnected;
	private SessionType sessionType;
	private HttpClient httpClient;

	public JPronote(SessionType sessionType, String endPoint) {
		this(sessionType, endPoint, DEFAULT_USER_AGENT);
	}

	public JPronote(SessionType sessionType, String endPoint, String userAgent) {
		this.endPoint = endPoint;
		this.userAgent = userAgent;
		this.sessionType = sessionType;
		this.httpClient = HttpClientBuilder.create().setUserAgent(userAgent).build();
	}

	public void login(String username, String password) {

	}

	public void loginCas(String casUrl, final String username, final String password)
			throws IOException, AuthenticationException {
		System.out.println(endPoint + sessionType.getLoginPath());

		// Kdecole wants this number back in the form
		HttpGet execGet = new HttpGet(casUrl);
		execGet.setHeader("User-Agent", userAgent);
		HttpResponse execRes = httpClient.execute(execGet);
		String cookies = execRes.getFirstHeader("Set-Cookie").getValue();
		ByteArrayOutputStream execStream = new ByteArrayOutputStream();
		execRes.getEntity().writeTo(execStream);
		final String execution = Jsoup.parse(execStream.toString()).getElementsByAttributeValue("name", "execution")
				.first().val();
		System.out.println("Execution: " + execution);
		System.out.println("Cookies: " + cookies);

		HttpPost post = new HttpPost(casUrl);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		post.setHeader("Cookies", cookies);
		post.setHeader("User-Agent", userAgent);
		post.setHeader("Referer", execGet.getURI().toString());
		ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new NameValuePair() {

			public String getValue() {
				return endPoint + sessionType.getLoginPath();
			}

			public String getName() {
				return "service";
			}
		});
		list.add(new NameValuePair() {

			public String getValue() {
				return "";
			}

			public String getName() {
				return "geolocation";
			}
		});
		list.add(new NameValuePair() {

			public String getValue() {
				return "submit";
			}

			public String getName() {
				return "_eventId";
			}
		});
		list.add(new NameValuePair() {

			public String getValue() {
				return "Valider";
			}

			public String getName() {
				return "submit";
			}
		});
		list.add(new NameValuePair() {

			public String getValue() {
				return username;
			}

			public String getName() {
				return "username";
			}
		});
		list.add(new NameValuePair() {

			public String getValue() {
				return password;
			}

			public String getName() {
				return "password";
			}
		});
		list.add(new NameValuePair() {

			public String getValue() {
				return execution;
			}

			public String getName() {
				return "execution";
			}
		});

		post.setEntity(new UrlEncodedFormEntity(list));

		HttpResponse res = httpClient.execute(post);

		if (res.getStatusLine().getStatusCode() == 401) {
			throw new AuthenticationException("Failed to login using CAS");
		}

		System.out.println(res.getStatusLine().getStatusCode());
		System.out.println("Location: " + res.getFirstHeader("Location"));
		res.getEntity().writeTo(System.out);
		this.isConnected = true;
	}

	public JSONObject makeRequest(HttpUriRequest req) {
		try {
			return httpClient.execute(req, new ResponseHandler<JSONObject>() {

				public JSONObject handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status != 200) {
						System.err.println("Status code: " + status);
					}

					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					response.getEntity().writeTo(stream);
					try {
						return new JSONObject(stream.toString());
					} catch (JSONException e) {
						return null;
					}
				}
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public JSONObject makeGetRequest(String request) {
		if (!request.endsWith("/"))
			request = request + "/";
		HttpGet get = new HttpGet(endPoint + request);
		// XXX: Headers
		return makeRequest(get);
	}

	public JSONObject callFunction() {
		return null;
	}
}
