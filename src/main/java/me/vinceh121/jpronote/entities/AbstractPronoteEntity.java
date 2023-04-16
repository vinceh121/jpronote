package me.vinceh121.jpronote.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import me.vinceh121.jpronote.IPronoteEntity;

public abstract class AbstractPronoteEntity implements IPronoteEntity {
	private String id;
	@JsonProperty("_T")
	private PronoteType type;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(final String id) {
		this.id = id;
	}

	public PronoteType getType() {
		return type;
	}

	public void setType(PronoteType type) {
		this.type = type;
	}
}
