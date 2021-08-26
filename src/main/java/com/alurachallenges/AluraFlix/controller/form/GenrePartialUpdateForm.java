package com.alurachallenges.AluraFlix.controller.form;

import javax.management.InvalidAttributeValueException;

import com.alurachallenges.AluraFlix.model.Genre;

public class GenrePartialUpdateForm {

	private String title;
	private String color;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Genre update(Genre genre) throws InvalidAttributeValueException {
		if (color != null) {
			if (!color.isBlank())
				genre.setColor(color);
			else
				throw new InvalidAttributeValueException("Color must not be blank.");
		}
		if (title != null) {
			if (!title.isBlank())
				genre.setTitle(title);
			else
				throw new InvalidAttributeValueException("Title must not be blank.");
		}
		return genre;
	}
}
