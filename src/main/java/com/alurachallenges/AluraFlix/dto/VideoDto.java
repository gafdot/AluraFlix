package com.alurachallenges.AluraFlix.dto;

import com.alurachallenges.AluraFlix.model.Genre;
import com.alurachallenges.AluraFlix.model.Video;
import com.alurachallenges.AluraFlix.repository.GenreRepository;

public class VideoDto {

	private Long id;
	private String description;
	private String url;
	private String title;
	private Long genreId;

	public VideoDto(Video video) {
		this.id = video.getId();
		this.description = video.getDescription();
		this.url = video.getUrl();
		this.title = video.getTitle();
		this.genreId = video.getGenre().getId();
	}

	public VideoDto(Video video, GenreRepository genreRepository) {
		this.id = video.getId();
		this.description = video.getDescription();
		this.url = video.getUrl();
		this.title = video.getTitle();
		if (video.getGenre() == null) {
			Genre genre = genreRepository.findById(1l).get();
			video.setGenre(genre);
			genre.getVideos().add(video);
		}
		this.genreId = video.getGenre().getId();
	}

	public VideoDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGenreId() {
		return genreId;
	}

	public void setGenreId(Long genreId) {
		this.genreId = genreId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
