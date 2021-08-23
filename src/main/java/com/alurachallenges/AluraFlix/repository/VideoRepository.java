package com.alurachallenges.AluraFlix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alurachallenges.AluraFlix.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long>, JpaSpecificationExecutor<Video> {

	public List<Video> findByGenreId(Long genreId);

}
