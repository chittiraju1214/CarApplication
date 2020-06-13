package com.example.car.service;

import java.util.ArrayList;

import com.example.car.model.Users;
import com.example.car.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailService implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Users user = usersRepository.findByUserId(Long.parseLong(username));

		if (user != null) {
			return new User(user.getUserName(), user.getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("No user present with username: " + username);
		}
	}
}
