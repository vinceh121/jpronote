package me.vinceh121.jpronote.primitives;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractPronotePrimitive implements IPronotePrimitive {
	@JsonProperty("_T")
	private PronoteType type;
	@JsonProperty("_V")
	private Object value;

	@Override
	public PronoteType getType() {
		return type;
	}

	@Override
	public void setType(PronoteType type) {
		this.type = type;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}
}
