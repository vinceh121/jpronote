package me.vinceh121.jpronote.entities;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Grade extends AbstractPronoteEntity {
	private String grade;

	@JsonProperty("V")
	public String getGrade() {
		return this.grade;
	}

	@JsonProperty("V")
	public void setGrade(final String grade) {
		this.grade = grade;
	}

	public double asDouble() throws ParseException {
		return NumberFormat.getInstance(Locale.FRENCH).parse(this.grade).doubleValue();
	}

	@Override
	public String toString() {
		return "Grade [grade=" + this.grade + "]";
	}

}
