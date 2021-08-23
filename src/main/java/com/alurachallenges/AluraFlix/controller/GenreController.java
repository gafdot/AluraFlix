package com.alurachallenges.AluraFlix.controller;

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
import com.alurachallenges.AluraFlix.dto.GenreDto;
import com.alurachallenges.AluraFlix.dto.GenreDtoWithVideos;
import com.alurachallenges.AluraFlix.dto.form.GenreDtoForm;
import com.alurachallenges.AluraFlix.dto.form.GenreDtoPartialUpdateForm;
import com.alurachallenges.AluraFlix.model.Genre;
import com.alurachallenges.AluraFlix.repository.GenreRepository;
import com.alurachallenges.AluraFlix.repository.VideoRepository;

@RestController
@RequestMapping("genres")
@Transactional
public class GenreController {

	@Autowired
	private GenreRepository genreRepository;
	@Autowired
	private VideoRepository videoRepository;

	@GetMapping
	public ResponseEntity<List<GenreDto>> getGenres(@RequestParam(required = false) Integer page) {
		if (page == null) {
			page = Integer.valueOf(0);
		}
		PageRequest pageRequest = PageRequest.of(page.intValue(), 5);
		List<GenreDto> list = genreRepository.findAll(pageRequest).stream().map(GenreDto::new)
				.collect(Collectors.toList());
		return ResponseEntity.ok(list);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getOneGenre(@PathVariable Long id) {
		Optional<Genre> optional = genreRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The genre id was not found.");
		} else {
			return ResponseEntity.ok(new GenreDto(optional.get()));
		}
	}

	@PostMapping
	public ResponseEntity<GenreDto> addOneGenre(@RequestBody @Valid GenreDtoForm form, UriComponentsBuilder builder) {
		Genre genre = form.convert();
		genreRepository.save(genre);
		URI uri = builder.path("genres/{id}").buildAndExpand(genre.getId()).toUri();
		return ResponseEntity.created(uri).body(new GenreDto(genre));
	}

	@PutMapping("{id}")
	public ResponseEntity<?> updateGenre(@PathVariable Long id, @RequestBody @Valid GenreDtoForm form) {
		Optional<Genre> optional = genreRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The genre id was not found.");
		} else {
			Genre genre = form.update(optional.get());
			return ResponseEntity.ok().body(new GenreDto(genre));
		}
	}

	@PatchMapping("{id}")
	public ResponseEntity<?> partiallyUpdateGenre(@PathVariable Long id,
			@RequestBody @Valid GenreDtoPartialUpdateForm form) {
		Optional<Genre> optional = genreRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The genre id was not found.");
		} else {
			Genre genre;
			try {
				genre = form.update(optional.get());
				return ResponseEntity.ok().body(new GenreDto(genre));
			} catch (InvalidAttributeValueException e) {
				return ResponseEntity.badRequest().body(
						Arrays.asList(new InvalidFormDto(e.getMessage().toLowerCase().split(" ")[0], e.getMessage())));
			}
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteGenre(@PathVariable Long id) {
		Optional<Genre> optional = genreRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The genre id was not found.");
		} else {
			genreRepository.deleteById(id);
			return ResponseEntity.ok("The genre has been successfully deleted.");
		}
	}

	@GetMapping("{id}/videos")
	public ResponseEntity<?> getVideosByGenre(@PathVariable Long id) {
		Optional<Genre> optional = genreRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The genre id was not found.");
		} else {
			return ResponseEntity.ok(new GenreDtoWithVideos(optional.get(), videoRepository));
		}
	}
}
