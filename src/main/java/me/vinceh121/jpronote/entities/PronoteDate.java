package me.vinceh121.jpronote.entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PronoteDate extends AbstractPronoteEntity {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/mm/yyyy");
	private String value;

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public Date toDate() throws ParseException {
		return PronoteDate.DATE_FORMAT.parse(this.value);
	}

}
