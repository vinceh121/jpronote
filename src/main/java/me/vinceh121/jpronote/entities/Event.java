package me.vinceh121.jpronote.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import me.vinceh121.jpronote.AbstractPronoteEntity;

public class Event extends AbstractPronoteEntity {
	private String name, comment, color;
	private boolean isClassCouncil;
	private Date start, end;

	@JsonProperty("L")
	public String getName() {
		return this.name;
	}

	@JsonProperty("L")
	public void setName(final String name) {
		this.name = name;
	}

	@JsonProperty("Commentaire")
	public String getComment() {
		return this.comment;
	}

	@JsonProperty("Commentaire")
	public void setComment(final String comment) {
		this.comment = comment;
	}

	@JsonProperty("CouleurCellule")
	public String getColor() {
		return this.color;
	}

	@JsonProperty("CouleurCellule")
	public void setColor(final String color) {
		this.color = color;
	}

	@JsonProperty("estConseilClasse")
	public boolean isClassCouncil() {
		return this.isClassCouncil;
	}

	@JsonProperty("estConseilClasse")
	public void setClassCouncil(final boolean isClassCouncil) {
		this.isClassCouncil = isClassCouncil;
	}

}
