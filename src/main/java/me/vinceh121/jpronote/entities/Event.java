package me.vinceh121.jpronote.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import me.vinceh121.jpronote.AbstractPronoteEntity;
import me.vinceh121.jpronote.IPronoteEntity;

public class Event extends AbstractPronoteEntity implements IPronoteEntity {
	private String name, comment, color;
	private boolean isClassCouncil;
	private Date start, end;

	@JsonProperty("L")
	public String getName() {
		return name;
	}

	@JsonProperty("L")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("Commentaire")
	public String getComment() {
		return comment;
	}

	@JsonProperty("Commentaire")
	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonProperty("CouleurCellule")
	public String getColor() {
		return color;
	}

	@JsonProperty("CouleurCellule")
	public void setColor(String color) {
		this.color = color;
	}

	@JsonProperty("estConseilClasse")
	public boolean isClassCouncil() {
		return isClassCouncil;
	}

	@JsonProperty("estConseilClasse")
	public void setClassCouncil(boolean isClassCouncil) {
		this.isClassCouncil = isClassCouncil;
	}

}
