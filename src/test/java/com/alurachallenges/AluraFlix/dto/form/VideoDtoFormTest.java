package com.alurachallenges.AluraFlix.dto.form;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.alurachallenges.AluraFlix.model.Genre;
import com.alurachallenges.AluraFlix.model.Video;
import com.alurachallenges.AluraFlix.repository.GenreRepository;

@ExtendWith(SpringExtension.class)
class VideoDtoFormTest {

	@MockBean
	private GenreRepository genreRepo;
	private Genre genre;
	private Genre genre2;
	private Video video;
	private Video video2;
	private Optional<Genre> optional;
	private VideoDtoForm form;

	@BeforeEach
	public void initialize() {
		genre = new Genre("genre 1", "#FFFFFF");
		genre.setId(1l);
		genre2 = new Genre("genre 2", "#EEEEEE");
		genre2.setId(2l);
		optional = Optional.of(genre);

		video = new Video("video 1", "url 1", "title 1", genre, true);
		genre.getVideos().add(video);
		video2 = new Video("video 2", "url 2", "title 2", genre, true);
		genre.getVideos().add(video2);

		form = new VideoDtoForm();
		form.setDescription("form description 1");
		form.setTitle("form title 1");
		form.setUrl("form url.1");
		form.setFree(true);
	}

	@Test
	void convertTest() {
		Mockito.when(genreRepo.findById(1l)).thenReturn(optional);

		Assertions.assertEquals(new Video("form description 1", "form url.1", "form title 1", genre, true),
				form.convert(genreRepo));
	}

	@Test
	void testUpdateMethodAndIfVideoChangesGenre() {
		Assertions.assertEquals(2, genre.getVideos().size());
		Assertions.assertEquals(0, genre2.getVideos().size());
		form.setGenreId(2l);

		Mockito.when(genreRepo.findById(1l)).thenReturn(optional);
		Optional<Genre> optional2 = Optional.of(genre2);
		Mockito.when(genreRepo.findById(2l)).thenReturn(optional2);

		form.update(video, genreRepo);

		Assertions.assertEquals(1, genre.getVideos().size());
		Assertions.assertEquals(1, genre2.getVideos().size());
	}
}
