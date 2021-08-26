package com.alurachallenges.AluraFlix.controller.form;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import com.alurachallenges.AluraFlix.model.Genre;
import com.alurachallenges.AluraFlix.model.Video;
import com.alurachallenges.AluraFlix.repository.GenreRepository;

public class VideoForm {

	@NotBlank
	private String description;
	@NotBlank
	private String url;
	@NotBlank
	private String title;
	@NotBlank
	private Boolean free;
	private Long genreId;

	public Boolean getFree() {
		return free;
	}

	public void setFree(Boolean free) {
		this.free = free;
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

	public Video convert(GenreRepository genreRepository) {
		if (genreId == null) {
			this.genreId = 1l;
		}
		Optional<Genre> optional = genreRepository.findById(genreId);
		return new Video(description, url, title, optional.get(), free);
	}

	public Video update(Video video, GenreRepository genreRepository) {
		video.setDescription(description);
		video.setTitle(title);
		video.setUrl(url);
		video.setFree(free);
		if (genreId == null) {
			this.genreId = 1l;
		}
		if (video.getGenre() != null) {
			List<Video> listOfVideosFromPreviosGenre = genreRepository.findById(video.getGenre().getId()).get()
					.getVideos();
			listOfVideosFromPreviosGenre.remove(video);
		}
		Genre genre = genreRepository.findById(genreId).get();
		video.setGenre(genre);
		List<Video> listOfVideosFromNewGenre = genre.getVideos();
		listOfVideosFromNewGenre.add(video);
		return video;
	}
}
