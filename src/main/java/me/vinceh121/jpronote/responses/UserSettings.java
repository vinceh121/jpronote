package me.vinceh121.jpronote.responses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import me.vinceh121.jpronote.entities.SelfStudent;

public class UserSettings {
	@JsonProperty("listeOngletsInvisibles")
	private final List<Integer> invisibleTabs = new ArrayList<>();
	@JsonProperty("listeOngletsNotification")
	private final List<Integer> notificationTabs = new ArrayList<>();
	@JsonProperty("ressource")
	private SelfStudent student;

	public SelfStudent getStudent() {
		return student;
	}

	public void setStudent(SelfStudent student) {
		this.student = student;
	}

	public List<Integer> getInvisibleTabs() {
		return invisibleTabs;
	}

	public List<Integer> getNotificationTabs() {
		return notificationTabs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(invisibleTabs, notificationTabs, student);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserSettings other = (UserSettings) obj;
		return Objects.equals(invisibleTabs, other.invisibleTabs)
				&& Objects.equals(notificationTabs, other.notificationTabs) && Objects.equals(student, other.student);
	}

	@Override
	public String toString() {
		return "UserSettings [invisibleTabs="
				+ invisibleTabs
				+ ", notificationTabs="
				+ notificationTabs
				+ ", student="
				+ student
				+ "]";
	}
}
