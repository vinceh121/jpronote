package me.vinceh121.jpronote;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface INamedEntity extends IPronoteEntity {
	@JsonProperty("L")
	void setName(String name);

	@JsonProperty("L")
	void getName();
}
