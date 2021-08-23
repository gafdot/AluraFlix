package com.alurachallenges.AluraFlix.controller;

import java.net.URI;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@TestPropertySource(properties = {"DN_NAME=aluraflix-test", "spring.jpa.hibernate.ddl-auto=create-drop"})
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class VideoControllerTest {

	@Autowired
	private MockMvc mock;

	@Test
	@WithMockUser(username = "admin", password = "admin")
	void invalidFormTestWhenPosting() throws Exception {
		URI uri = new URI("/videos");
		String json = "{ \"url\": \"video1.com\", \"title\": \"video 1}\"";
		mock.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	void invalidFormTestWhenPatching() throws Exception {
		URI uri = new URI("/videos/1");
		String json = "{ \"url\": \"\", \"title\": \"video 1}\"";
		mock.perform(MockMvcRequestBuilders.patch(uri).content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		mock.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	void invalidIdTest() throws Exception {
		URI uri = new URI("/videos/100");
		String json = "{ \"url\": \"video1.com\", \"title\": \"video 1\"}";
		mock.perform(MockMvcRequestBuilders.patch(uri).content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	void invalidGet() throws Exception {
		URI uri = new URI("/videos/1");
		mock.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
