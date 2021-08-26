package com.alurachallenges.AluraFlix.controller;

import static com.alurachallenges.AluraFlix.repository.specification.VideoSpecifications.descriptionContains;
import static com.alurachallenges.AluraFlix.repository.specification.VideoSpecifications.hasUrl;
import static com.alurachallenges.AluraFlix.repository.specification.VideoSpecifications.isFree;
import static com.alurachallenges.AluraFlix.repository.specification.VideoSpecifications.titleContains;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.InvalidAttributeValueException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alurachallenges.AluraFlix.config.validation.InvalidFormDto;
import com.alurachallenges.AluraFlix.controller.dto.VideoDto;
import com.alurachallenges.AluraFlix.controller.form.VideoForm;
import com.alurachallenges.AluraFlix.controller.form.VideoPartialUpdateForm;
import com.alurachallenges.AluraFlix.model.Video;
import com.alurachallenges.AluraFlix.repository.GenreRepository;
import com.alurachallenges.AluraFlix.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
@Transactional
public class VideoController {

	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private GenreRepository genreRepository;

	@GetMapping
	public ResponseEntity<List<VideoDto>> getVideos(@RequestParam(required = false) String title,
			@RequestParam(required = false) String description, @RequestParam(required = false) String url,
			@RequestParam(required = false) Integer page) {

		if (page == null) {
			page = Integer.valueOf(0);
		}
		PageRequest pageRequest = PageRequest.of(page.intValue(), 5);
		Specification<Video> specs = Specification.where(titleContains(title)).and(descriptionContains(description))
				.and(hasUrl(url));
		List<VideoDto> list = videoRepository.findAll(specs, pageRequest).stream()
				.map(v -> new VideoDto(v, genreRepository)).collect(Collectors.toList());
		return ResponseEntity.ok(list);
	}

	@GetMapping("/free")
	public ResponseEntity<List<VideoDto>> getFreeVideos(@RequestParam(required = false) String title,
			@RequestParam(required = false) String description, @RequestParam(required = false) String url,
			@RequestParam(required = false) Integer page) {
		if (page == null) {
			page = Integer.valueOf(0);
		}
		PageRequest pageRequest = PageRequest.of(page.intValue(), 5);
		Specification<Video> specs = Specification.where(titleContains(title)).and(descriptionContains(description))
				.and(hasUrl(url)).and(isFree(true));
		List<VideoDto> list = videoRepository.findAll(specs, pageRequest).stream()
				.map(v -> new VideoDto(v, genreRepository)).collect(Collectors.toList());
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOneVideo(@PathVariable Long id) {
		Optional<Video> optional = videoRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The video id has not been found.");
		} else {
			return ResponseEntity.ok(new VideoDto(optional.get(), genreRepository));
		}
	}

	@PostMapping
	public ResponseEntity<VideoDto> addOneVideo(@RequestBody @Valid VideoForm form, UriComponentsBuilder builder) {
		Video video = form.convert(genreRepository);
		videoRepository.save(video);
		List<Video> videos = genreRepository.findById(video.getGenre().getId()).get().getVideos();
		videos.add(video);
		URI uri = builder.path("videos/{id}").buildAndExpand(video.getId()).toUri();
		return ResponseEntity.created(uri).body(new VideoDto(video));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateVideo(@RequestBody @Valid VideoForm form, @PathVariable Long id) {
		Optional<Video> optional = videoRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The video id has not been found.");
		} else {
			Video updatedVideo = form.update(optional.get(), genreRepository);
			return ResponseEntity.ok(new VideoDto(updatedVideo));
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> partiallyUpdateVideo(@RequestBody @Valid VideoPartialUpdateForm form,
			@PathVariable Long id) {
		Optional<Video> optional = videoRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The video id has not been found.");
		} else {
			Video updatedVideo;
			try {
				updatedVideo = form.update(optional.get(), genreRepository);
				return ResponseEntity.ok(new VideoDto(updatedVideo));
			} catch (InvalidAttributeValueException e) {
				return ResponseEntity.badRequest().body(
						Arrays.asList(new InvalidFormDto(e.getMessage().toLowerCase().split(" ")[0], e.getMessage())));
			}
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteVideo(@PathVariable Long id) {
		Optional<Video> optional = videoRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The video id has not been found.");
		} else {
			Video video = videoRepository.findById(id).get();
			genreRepository.findById(video.getGenre().getId()).get().getVideos().remove(video);
			videoRepository.deleteById(id);
			return ResponseEntity.ok("The video has been successfully deleted.");
		}
	}
}
