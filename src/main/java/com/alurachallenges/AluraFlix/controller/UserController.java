package com.alurachallenges.AluraFlix.controller;

import java.util.Arrays;
import java.util.Optional;

import javax.management.InvalidAttributeValueException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alurachallenges.AluraFlix.config.validation.InvalidFormDto;
import com.alurachallenges.AluraFlix.dto.form.UserDtoForm;
import com.alurachallenges.AluraFlix.dto.form.UserDtoPartialUpdateForm;
import com.alurachallenges.AluraFlix.model.User;
import com.alurachallenges.AluraFlix.repository.UserRepository;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping
	public ResponseEntity<?> registerNewUser(@RequestBody @Valid UserDtoForm form) {
		User user = form.convert();
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping
	public ResponseEntity<?> updateUser(@RequestBody @Valid UserDtoPartialUpdateForm form,
			@AuthenticationPrincipal UserDetails userDetails) {
		Optional<User> optional = userRepository.findByUsername(userDetails.getUsername());
		try {
			form.update(optional.get(), userRepository);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (InvalidAttributeValueException e) {
			return ResponseEntity.badRequest().body(
					Arrays.asList(new InvalidFormDto(e.getMessage().toLowerCase().split(" ")[0], e.getMessage())));
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
		Optional<User> optional = userRepository.findByUsername(userDetails.getUsername());
		User user = optional.get();
		userRepository.deleteById(user.getId());
		return ResponseEntity.ok("This user has been successfully deleted.");
	}

	@DeleteMapping(path = { "{id}" })
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
		Optional<User> optional = userRepository.findById(id);
		User user = optional.get();
		userRepository.deleteById(user.getId());
		return ResponseEntity.ok("This user has been successfully deleted.");
	}

	@DeleteMapping(path = { "{username}" })
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUserByUsername(@PathVariable String username) {
		Optional<User> optional = userRepository.findByUsername(username);
		User user = optional.get();
		userRepository.deleteById(user.getId());
		return ResponseEntity.ok("This user has been successfully deleted.");
	}
}
