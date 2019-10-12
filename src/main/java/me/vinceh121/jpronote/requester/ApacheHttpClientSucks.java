package me.vinceh121.jpronote.requester;

import org.apache.http.NameValuePair;

class ApacheHttpClientSucks {
    static NameValuePair valuePair(final String name, final String value) {
        return new NameValuePair() {
            public String getName() {
                return name;
            }

            public String getValue() {
                return value;
            }
        };
    }
}
