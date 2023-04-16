package me.vinceh121.jpronote.primitives;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = PronoteList.class)
public interface IPronoteList<T> extends IPronotePrimitive, List<T> {
}
