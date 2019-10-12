package me.vinceh121.jpronote.requester;

import me.vinceh121.jpronote.SessionType;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Requester {
    private final String endpoint;
    private final SessionType sessionType;
    private final HttpClient httpClient;

    public Requester(String endpoint, SessionType sessionType, String userAgent) {
        this.endpoint = endpoint;
        this.sessionType = sessionType;
        this.httpClient = HttpClientBuilder.create().setUserAgent(userAgent).build();
    }

    public HttpResponse performRequest(IRequest request) throws Exception {
        return request.execute(this);
    }

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
