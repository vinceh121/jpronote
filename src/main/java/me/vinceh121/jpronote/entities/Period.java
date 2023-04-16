package me.vinceh121.jpronote.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a period of the scholar year. Typically trimester 1, 2, 3, or semester 1, 2
 *
 * @author vincent
 */
public class Period extends AbstractBasicPronoteEntity {
	@JsonProperty("GenreNotation")
	private int gradingType;

	public int getGradingType() {
		return gradingType;
	}

	public void setGradingType(int gradingType) {
		this.gradingType = gradingType;
	}
}
