package me.vinceh121.jpronote;

import java.util.Hashtable;

public final class PronoteErrorHandler {
	private static Hashtable<Integer, String> errorCodes = new Hashtable<>();

	static {
		errorCodes.put(22, "The document expired");
		errorCodes.put(4, "Access denied");
		errorCodes.put(7, "IP address suspended");
	}

	public static String getErrorMessage(int code) {
		final String err = errorCodes.get(code);
		if (err != null)
			return err + " (" + code + ")";
		else
			return "Error code: " + code;
	}
}
