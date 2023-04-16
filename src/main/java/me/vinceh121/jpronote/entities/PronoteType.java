package me.vinceh121.jpronote.entities;

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
	GRADE(10);

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
		return REVERSE_LUT.get(typeInt);
	}

	private static class PronoteTypeDeserializer extends StdDeserializer<PronoteType> {
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
