package com.alurachallenges.AluraFlix.controller.dto;

import com.alurachallenges.AluraFlix.model.Genre;

public class GenreDto {

	private Long id;
	private String title;
	private String color;

	public GenreDto() {
	}

	public GenreDto(Genre genre) {
		this.title = genre.getTitle();
		this.color = genre.getColor();
		this.id = genre.getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
}
