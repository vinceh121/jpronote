package me.vinceh121.jpronote;

public enum Page {
	HOMEPAGE(7, "PageAccueil"), INFO_POLLS(8, "PageActualites"), AGENDA(9, "PageAgenda"), MENU(10, "PageMenus"),
	CALENDAR(11, ""), // PageCalendrier?
	TIMETABLE(16, "PageEmploiDuTemps"), PLURIANNUAL(44, "PageSuiviPluriannuel"), LIVRET(83, "LivretScolaire"),
	GRADES(196, "DernieresNotes"), GENERATE_PDF(227, "GenerationPDF"), TEACHER_ROSTER(142, "PageTrombinoscope"),
	STAFF_ROSTER(143, "PageTrombinoscope"), STUDENT_ROSTER(77, "PageTrombinoscope"),
	PEDAGOGIC_ROSTER(179, "PageTrombinoscope"), LIST_TEACHERS(123, "ListeRessources");

	int id;
	String pageName;

	Page(int id, String pageName) {
		this.id = id;
		this.pageName = pageName;
	}

	public int getId() {
		return id;
	}

	public String getPageName() {
		return pageName;
	}
}
