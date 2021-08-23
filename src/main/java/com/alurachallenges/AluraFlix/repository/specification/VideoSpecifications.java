package com.alurachallenges.AluraFlix.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.alurachallenges.AluraFlix.model.Video;

public class VideoSpecifications {

	public static Specification<Video> titleContains(String title) {
		if (title == null)
			return null;
		return (root, query, cb) -> cb.like(root.get("title"), "%" + title + "%");
	}

	public static Specification<Video> descriptionContains(String description) {
		if (description == null)
			return null;
		return (root, query, cb) -> cb.like(root.get("description"), "%" + description + "%");
	}

	public static Specification<Video> hasUrl(String url) {
		if (url == null)
			return null;
		return (root, query, cb) -> cb.like(root.get("url"), "%" + url + "%");
	}

	public static Specification<Video> isFree(Boolean free) {
		if (free == null)
			return null;
		return (root, query, cb) -> cb.equal(root.get("free"), free);
	}

}
