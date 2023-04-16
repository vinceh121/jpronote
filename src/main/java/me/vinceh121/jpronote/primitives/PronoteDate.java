package me.vinceh121.jpronote.primitives;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

public class PronoteDate extends AbstractPronotePrimitive {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/mm/yyyy");
	private static final DateTimeFormatter DATETIME_FORMATTER
			= new DateTimeFormatterBuilder().appendValue(DAY_OF_MONTH, 2)
					.appendLiteral('/')
					.appendValue(MONTH_OF_YEAR, 2)
					.appendLiteral('/')
					.appendValue(YEAR)
					.optionalStart()
					.appendLiteral(' ')
					.appendValue(HOUR_OF_DAY)
					.appendLiteral(':')
					.appendValue(MINUTE_OF_HOUR)
					.appendLiteral(':')
					.appendValue(SECOND_OF_MINUTE)
					.toFormatter();

	public Date toDate() throws ParseException {
		return PronoteDate.DATE_FORMAT.parse((String) this.getValue());
	}

	public LocalDateTime toLocalDate() {
		return LocalDateTime.parse((CharSequence) this.getValue(), PronoteDate.DATETIME_FORMATTER);
	}
}
