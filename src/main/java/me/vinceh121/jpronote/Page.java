package me.vinceh121.jpronote;

public enum Page {
    HOMEPAGE(7, "PageAccueil"),
    TIMETABLE(16, "PageEmploiDuTemps");

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
