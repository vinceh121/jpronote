package me.vinceh121.jpronote.entities;

import java.time.LocalDateTime;

import org.json.JSONPropertyName;

import me.vinceh121.jpronote.IPronoteEntity;

public class Event implements IPronoteEntity {
	private String name, comment, color;
	private boolean isClassCouncil;
	private LocalDateTime start, end;

	@Override
	public int getType() {
		return 0;
	}

	@Override
	@JSONPropertyName(value = "N")
	public String getId() {
		return null;
	}

	@JSONPropertyName(value = "L")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSONPropertyName(value = "Commentaire")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@JSONPropertyName(value = "CouleurCellule")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@JSONPropertyName(value = "estConseilClasse")
	public boolean isClassCouncil() {
		return isClassCouncil;
	}

	public void setClassCouncil(boolean isClassCouncil) {
		this.isClassCouncil = isClassCouncil;
	}

}
