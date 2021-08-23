package com.alurachallenges.AluraFlix.dto.form;

import javax.validation.constraints.NotBlank;

import com.alurachallenges.AluraFlix.model.Genre;

public class GenreDtoForm {

	@NotBlank
	private String title;
	@NotBlank
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

	public Genre convert() {
		return new Genre(title, color);
	}

	public Genre update(Genre genre) {
		genre.setColor(color);
		genre.setTitle(title);
		return genre;
	}
}
