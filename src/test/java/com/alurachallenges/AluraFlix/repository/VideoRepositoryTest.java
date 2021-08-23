package com.alurachallenges.AluraFlix.repository;

import static com.alurachallenges.AluraFlix.repository.specification.VideoSpecifications.titleContains;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.alurachallenges.AluraFlix.model.Genre;
import com.alurachallenges.AluraFlix.model.Video;

@DataJpaTest
public class VideoRepositoryTest {

	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private TestEntityManager em;
	private Genre genre;
	private Video video;
	private Video video2;

	@BeforeEach
	public void initialize() {
		genre = new Genre("uncategorized", "grey");
		em.persist(genre);
		video = new Video("video 1", "www.video1.com", "video 1", genre, true);
		em.persist(video);
		video2 = new Video("video 2", "www.video2.com", "video 2", genre, true);
		em.persist(video2);
		em.flush();
	}

	@Test
	public void testFindByGenreId() {
		Long genreId = 2l;
		List<Video> videos = videoRepository.findByGenreId(genreId);
		Assertions.assertNotNull(videos);
		Assertions.assertEquals(2, videos.size());
	}

	@Test
	public void testFindPieceOfTitle() {
		List<Video> videos = videoRepository.findAll(titleContains("vi"));
		Assertions.assertNotNull(videos);
		Assertions.assertEquals(2, videos.size());
	}

	@Test
	public void testNotFindPieceOfTitle() {
		List<Video> videos = videoRepository.findAll(titleContains("54"));
		Assertions.assertEquals(0, videos.size());
	}

}
