package me.vinceh121.jpronote;

import java.util.Hashtable;

public final class PronoteErrorHandler {
	private static Hashtable<Integer, String> errorCodes = new Hashtable<>();

	static {
		PronoteErrorHandler.errorCodes.put(25, "Reached maximum number of authentifications");
		PronoteErrorHandler.errorCodes.put(22, "The document expired");
		PronoteErrorHandler.errorCodes.put(4, "Access denied");
		PronoteErrorHandler.errorCodes.put(7, "IP address suspended");
	}

	public static String getErrorMessage(final int code) {
		final String err = PronoteErrorHandler.errorCodes.get(code);
		if (err != null) {
			return err + " (" + code + ")";
		} else {
			return "Error code: " + code;
		}
	}
}
