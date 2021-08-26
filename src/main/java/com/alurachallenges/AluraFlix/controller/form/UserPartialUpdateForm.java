package com.alurachallenges.AluraFlix.controller.form;

import javax.management.InvalidAttributeValueException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alurachallenges.AluraFlix.model.User;
import com.alurachallenges.AluraFlix.repository.UserRepository;

public class UserPartialUpdateForm {

	private String password;
	private String name;

	public User update(User user, UserRepository userRepository) throws InvalidAttributeValueException {
		if (password != null) {
			if (password.length() >= 8) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String encoded = encoder.encode(password);
				user.setPassword(encoded);
			} else
				throw new InvalidAttributeValueException("Password must have at least 8 characters.");
		}
		if (name != null) {
			if (name.length() >= 8)
				user.setName(name);
			else
				throw new InvalidAttributeValueException("Name must have at least 8 characters.");
		}
		return user;
	}

}
