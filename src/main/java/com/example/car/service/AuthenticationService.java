package com.example.car.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.car.model.TokenPair;
import com.example.car.model.UserInformation;
import com.example.car.model.Users;
import com.example.car.repository.UsersRepository;
import com.example.car.security.model.RegistrationRequest;
import com.example.car.security.model.SignIn;
import com.example.car.security.model.SignInRequest;
import com.example.car.security.model.SignUp;
import com.example.car.util.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	JwtUtil jwtUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

	public SignUp generateRegistrationRequest(List<RegistrationRequest> requestDtoList) throws Exception {
		LOGGER.info("inside doRegistration for user Registration");
		try {
			List<String> existingMails = usersRepository.findAllUserEmail();
			List<RegistrationRequest> existingUsers = requestDtoList.stream()
					.filter(data -> existingMails.contains(data.getUserEmail())).collect(Collectors.toList());
			requestDtoList.removeAll(existingUsers);
			List<Users> newUsers = requestDtoList.stream().map(data -> {
				final Users user = new Users();
				user.setUserEmail(data.getUserEmail());
				user.setUserName(data.getUserName());
				user.setPassword(passwordEncoder.encode(data.getPassword()));
				user.setCreatedBy(1L);
				return user;
			}).collect(Collectors.toList());
			usersRepository.saveAll(newUsers);
			List<UserInformation> registeredMails = requestDtoList.stream()
					.map(data -> new UserInformation(data.getUserName(), data.getUserEmail()))
					.collect(Collectors.toList());
			List<UserInformation> existingUserEmails = existingUsers.stream()
					.map(data -> new UserInformation(data.getUserName(), data.getUserEmail()))
					.collect(Collectors.toList());
			SignUp signUp = new SignUp(registeredMails, existingUserEmails);
			return signUp;
		} catch (Exception ex) {
			LOGGER.error("Error while registraing users with error message", ex.getMessage());
			throw ex;
		}
	}

	public SignIn generateLoginRequest(SignInRequest signInRequest) throws Exception {
		LOGGER.info("inside do login method of Authentication service");
		try {
			Users users = usersRepository.findByUserEmail(signInRequest.getUserEmail());
			Optional<Users> optionalUser = Optional.ofNullable(users);
			if (!optionalUser.isPresent()) {
				throw new Exception("No user found with this email");
			} else if (!optionalUser.get().getUserEmail().equalsIgnoreCase(signInRequest.getUserEmail())
					|| !passwordEncoder.matches(signInRequest.getPassword(), optionalUser.get().getPassword())) {
				throw new Exception("Incorrect Credentials");
			} else {
				final TokenPair tokenPair = jwtUtil.createRefreshAndAccessToken(users);

				SignIn signIn = new SignIn(users.getUserId(), users.getUserName(), users.getUserEmail(),
						tokenPair);
				return signIn;
			}
		} catch (Exception ex) {
			LOGGER.error("Error while login with error message {}", ex.getMessage());
			throw ex;
		}
	}
}
