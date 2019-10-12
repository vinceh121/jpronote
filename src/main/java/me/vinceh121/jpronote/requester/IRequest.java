package me.vinceh121.jpronote.requester;

import org.apache.http.HttpResponse;

import javax.annotation.Nullable;

public interface IRequest {
    @Nullable
    HttpResponse execute(Requester requester) throws Exception;
}
