package com.alurachallenges.AluraFlix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alurachallenges.AluraFlix.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
