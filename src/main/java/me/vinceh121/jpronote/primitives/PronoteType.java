package me.vinceh121.jpronote.primitives;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@JsonDeserialize(using = PronoteType.PronoteTypeDeserializer.class)
public enum PronoteType {
	/**
	 * String as format `dd/MM/YYYY hh:mm:ss` with time part being optional
	 */
	DATE(7),
	/**
	 * Defines an int value range with the format `[start..end]` e.g. `[0..5]`.
	 * It can be the empty value `[]`.
	 * TODO find out bound inclusiveness
	 */
	RANGE(8),
	/**
	 * Float value with a comma for decimal separator.
	 */
	GRADE(10),
	HTML_STRING(21),
	FILE_URL(23),
	/**
	 * V value is a JSON array.
	 */
	LIST(24),
	/**
	 * File embedded in the response under the "/donneesNonSec/fichiers" array as a
	 * B64 string. V value is the int index under the fichiers array.
	 */
	FILE(25),
	SET(26);

	private final int typeInt;

	private PronoteType(final int typeInt) {
		this.typeInt = typeInt;
	}

	public int getTypeInt() {
		return this.typeInt;
	}

	private static final Map<Integer, PronoteType> REVERSE_LUT = new HashMap<>();

	static {
		for (PronoteType t : values()) {
			REVERSE_LUT.put(t.typeInt, t);
		}
	}

	public static PronoteType fromTypeInt(int typeInt) {
		PronoteType t = REVERSE_LUT.get(typeInt);
		if (t == null) {
			throw new IllegalArgumentException("Invalid PronoteType typeInt " + typeInt);
		}
		return t;
	}

	public static class PronoteTypeDeserializer extends StdDeserializer<PronoteType> {
		private static final long serialVersionUID = 7849606467081275309L;

		public PronoteTypeDeserializer() {
			super(PronoteType.class);
		}

		@Override
		public PronoteType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
			if (p.currentToken() != JsonToken.VALUE_NUMBER_INT) {
				throw new JsonParseException(p, "Expected int as PronoteType");
			}

			return PronoteType.fromTypeInt(p.getIntValue());
		}
	}
}
