package com.tsystems.mms.demoapp.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		return userRepository.save(user).getId();
	}
}
