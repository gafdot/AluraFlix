package com.alurachallenges.AluraFlix.controller.form;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alurachallenges.AluraFlix.model.User;

public class UserForm {

	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String name;

	public User convert() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encoded = encoder.encode(password);
		return new User(username, encoded, name, false, true);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
