package com.alurachallenges.AluraFlix.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.alurachallenges.AluraFlix.model.Genre;
import com.alurachallenges.AluraFlix.model.Video;
import com.alurachallenges.AluraFlix.repository.VideoRepository;

public class GenreDtoWithVideos {

	private String title;
	private String color;
	private List<VideoDto> videos;

	public GenreDtoWithVideos() {
	}

	public GenreDtoWithVideos(Genre genre, VideoRepository videoRepository) {
		this.title = genre.getTitle();
		this.color = genre.getColor();
		List<Video> videos = videoRepository.findByGenreId(genre.getId());
		this.videos = videos.stream().map(VideoDto::new).collect(Collectors.toList());

//		List<Video> videos2 = genre.getVideos();
//		this.videos = videos2.stream().map(VideoDto::new).collect(Collectors.toList());
	}

	public List<VideoDto> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoDto> videos) {
		this.videos = videos;
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
