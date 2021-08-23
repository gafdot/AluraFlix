package com.alurachallenges.AluraFlix.dto.form;

import java.util.List;

import javax.management.InvalidAttributeValueException;

import com.alurachallenges.AluraFlix.model.Genre;
import com.alurachallenges.AluraFlix.model.Video;
import com.alurachallenges.AluraFlix.repository.GenreRepository;

public class VideoDtoPartialUpdateForm {

	private String description;
	private String url;
	private String title;
	private Long genreId;
	private Boolean free;

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

	public Video update(Video video, GenreRepository genreRepository) throws InvalidAttributeValueException {
		if (description != null) {
			if (!description.isBlank())
				video.setDescription(description);
			else
				throw new InvalidAttributeValueException("Description must not be blank.");
		}
		if (title != null) {
			if (!title.isBlank())
				video.setTitle(title);
			else
				throw new InvalidAttributeValueException("Title must not be blank.");
		}
		if (url != null) {
			if (!url.isBlank())
				video.setUrl(url);
			else
				throw new InvalidAttributeValueException("Url must not be blank.");
		}
		if (free != null) {
			video.setFree(free);
		}
		if (genreId != null) {
			if (video.getGenre() != null) {
				List<Video> listOfVideosFromPreviosGenre = genreRepository.findById(video.getGenre().getId()).get()
						.getVideos();
				listOfVideosFromPreviosGenre.remove(video);
			}
			Genre genre = genreRepository.findById(genreId).get();
			video.setGenre(genre);
			List<Video> listOfVideosFromNewGenre = genre.getVideos();
			listOfVideosFromNewGenre.add(video);
		}
		return video;
	}
}
