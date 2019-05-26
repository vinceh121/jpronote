package me.vinceh121.jpronote;

public enum SessionType {
	STUDENT(3, "/eleve.html");

	int type;
	String loginPath;

	SessionType(int type, String loginPath) {
		this.type = type;
		this.loginPath = loginPath;
	}

	public String getLoginPath() {
		return loginPath;
	}

	public int getType() {
		return type;
	}
}
