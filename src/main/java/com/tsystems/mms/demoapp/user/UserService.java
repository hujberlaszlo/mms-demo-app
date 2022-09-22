package com.tsystems.mms.demoapp.user;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsystems.mms.demoapp.user.exception.EmailValidationException;
import com.tsystems.mms.demoapp.user.exception.UserNotFoundException;

/**
 * This service manages all user.
 */
@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Find all users from the database.
	 * 
	 * @return List of users.
	 */
	public List<User> getAll() {
		return userRepository.findAll();
	}

	/**
	 * Find user from the database by id.
	 * 
	 * @param The user id.
	 * @throws UserNotFoundException when the user is not found with given id.
	 * @return The user by given id.
	 */
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id:" + userId));
	}

	/**
	 * Create user.
	 * 
	 * @param User to save in database.
	 * @throws UserNotFoundException when the user is not found with given id.
	 * @return The created user's id.
	 */
	public Long createUser(User user) {
		validateEmail(user.getEmail());
		return userRepository.save(user).getId();
	}

	/**
	 * Update user.
	 * 
	 * @param User id and user updated data.
	 */
	public void updateUser(Long userId, User user) {
		user.setId(userId);
		userRepository.save(user);
	}

	/**
	 * Create user.
	 * 
	 * @param User id.
	 * @throws UserNotFoundException when the user is not found with given id.
	 */
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id:" + userId));
		userRepository.delete(user);

	}

	private static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$";

	/**
	 * Validates the email based on a regex.
	 * 
	 * @param User email.
	 * @throws EmailValidationException when the email is invalid.
	 */
	private void validateEmail(String emailAddress) {
		boolean isValid = Pattern.compile(EMAIL_REGEX_PATTERN).matcher(emailAddress).matches();

		if (!isValid) {
			throw new EmailValidationException("Invalid email:" + emailAddress);
		}
	}
}
