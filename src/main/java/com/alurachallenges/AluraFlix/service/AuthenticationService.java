package com.alurachallenges.AluraFlix.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.alurachallenges.AluraFlix.model.User;
import com.alurachallenges.AluraFlix.repository.UserRepository;

@Component
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = userRepository.findByUsername(username);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new UsernameNotFoundException("User or password not found.");
	}

}
