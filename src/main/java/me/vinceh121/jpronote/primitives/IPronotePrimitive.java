package me.vinceh121.jpronote.primitives;

public interface IPronotePrimitive {

	void setValue(Object value);

	Object getValue();

	void setType(PronoteType type);

	PronoteType getType();
}
