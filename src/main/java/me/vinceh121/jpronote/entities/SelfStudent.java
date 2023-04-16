package me.vinceh121.jpronote.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import me.vinceh121.jpronote.primitives.IPronoteList;
import me.vinceh121.jpronote.primitives.PronoteList;

public class SelfStudent extends AbstractBasicPronoteEntity {
	@JsonProperty("Etablissement")
	private final IPronoteList<Establishment> establishments = new PronoteList<>();
	@JsonProperty("avecPhoto")
	private boolean hasPhoto;
	@JsonProperty("classeDEleve")
	private Clazz clazz;

	public boolean isHasPhoto() {
		return hasPhoto;
	}

	public void setHasPhoto(boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	public IPronoteList<Establishment> getEstablishments() {
		return establishments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(clazz, establishments, hasPhoto);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SelfStudent other = (SelfStudent) obj;
		return Objects.equals(clazz, other.clazz) && Objects.equals(establishments, other.establishments)
				&& hasPhoto == other.hasPhoto;
	}

	@Override
	public String toString() {
		return "SelfStudent [establishments=" + establishments + ", hasPhoto=" + hasPhoto + ", clazz=" + clazz + "]";
	}
}
