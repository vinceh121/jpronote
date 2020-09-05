package me.vinceh121.jpronote;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface IPronoteEntity {

	@JsonProperty("N")
	String getId();

	@JsonProperty("N")
	void setId(String id);
}
