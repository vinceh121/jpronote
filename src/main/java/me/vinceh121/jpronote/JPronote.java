package me.vinceh121.jpronote;

import java.io.IOException;

import me.vinceh121.jpronote.requester.LoginCAS;
import me.vinceh121.jpronote.requester.Requester;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;

public class JPronote {
    @SuppressWarnings("WeakerAccess")
    public static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.128 Electron/4.1.5 Safari/537.36";
    private final Requester requester;
    private String token, sessionId, userAgent;
    private boolean isConnected;

    public JPronote(String endpoint, SessionType sessionType) {
        this(endpoint, sessionType, DEFAULT_USER_AGENT);
    }

    @SuppressWarnings("WeakerAccess")
    public JPronote(String endpoint, SessionType sessionType, String userAgent) {
        requester = new Requester(endpoint, sessionType, userAgent);
    }

    public void login(String username, String password) {

    }

    public void loginCas(String casUrl, String username, String password) throws AuthenticationException, IOException {
        loginCas(casUrl, username, password, null);
    }

    public void loginCas(String casUrl, String username, String password, String selection) throws AuthenticationException, IOException {
        final LoginCAS request = new LoginCAS(casUrl, username, password, selection);
        HttpResponse res;
        try {
            res = requester.performRequest(request);
        } catch (Exception e) {
            AuthenticationException exception = new AuthenticationException("Failed to login using CAS");
            exception.addSuppressed(e);
            throw exception;
        }

        if (res.getStatusLine().getStatusCode() == 401) {
            throw new AuthenticationException("Failed to login using CAS");
        }

        // @todo: Follow 301 & rest of the flow
        System.out.println(res.getStatusLine().getStatusCode());
        res.getEntity().writeTo(System.out);
        this.isConnected = true;
    }
}
