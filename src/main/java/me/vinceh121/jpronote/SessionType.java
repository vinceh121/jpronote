package me.vinceh121.jpronote;

public enum SessionType {
	ADMINISTRATION(16, "/direction.html"), TEACHER(1, "/professeur.html"), STUD_ADMIN(13, "/viescolaire.html"),
	PARENT(2, "/parent.html"), ENTERPRISE(4, "/entreprise.html"), ACADEMIC(5, "/academie.html"),
	STUDENT(3, "/eleve.html"), NULL(0, "/");

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
