package me.vinceh121.jpronote.requester;

import me.vinceh121.jpronote.SessionType;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class LoginCAS implements IRequest {
    private final String url;
    private final String username;
    private final String password;
    private final String selection;

    public LoginCAS(String url, String username, String password) {
        this(url, username, password, null);
    }

    public LoginCAS(String url, String username, String password, String selection) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.selection = selection;
    }

    public HttpResponse execute(Requester requester) throws Exception {
        final HttpPost request = new HttpPost(url);
        final String execution = fetchExecutionToken(requester);
        ArrayList<NameValuePair> list = new ArrayList<>();
        list.add(ApacheHttpClientSucks.valuePair("service", requester.getEndpoint() + requester.getSessionType().getLoginPath()));
        list.add(ApacheHttpClientSucks.valuePair("geolocation", ""));
        list.add(ApacheHttpClientSucks.valuePair("_eventId", "submit"));
        list.add(ApacheHttpClientSucks.valuePair("submit", "Valider"));
        list.add(ApacheHttpClientSucks.valuePair("username", username));
        list.add(ApacheHttpClientSucks.valuePair("password", password));
        list.add(ApacheHttpClientSucks.valuePair("execution", execution));
        if (selection != null) list.add(ApacheHttpClientSucks.valuePair("selection", selection));

        request.setEntity(new UrlEncodedFormEntity(list));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        return requester.getHttpClient().execute(request);
    }

    private String fetchExecutionToken(Requester requester) throws IOException {
        String endpoint = url + "?service=" + URLEncoder.encode(requester.getEndpoint() + requester.getSessionType().getLoginPath(), "UTF-8");
        if (selection != null) endpoint += "&selection=" + selection;
        final HttpGet request = new HttpGet(endpoint);
        HttpResponse response = requester.getHttpClient().execute(request);

        ByteArrayOutputStream execStream = new ByteArrayOutputStream();
        response.getEntity().writeTo(execStream);
        System.out.println(execStream.toString());
        return Jsoup.parse(execStream.toString()).getElementsByAttributeValue("name", "execution").first().val();
    }
}
