package com.tsystems.mms.demoapp.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful API controller for managing users.
 */
@RestController
@RequestMapping("/api/v1.0")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Finds all users.
	 * 
	 * @return A list of users.
	 */
	@GetMapping("/user")
	public ResponseEntity<List<User>> getUsers() {

		LOGGER.info("Get all users from the database");
		return ResponseEntity.ok(userService.getAll());
	}

	/**
	 * Finds user by id.
	 * 
	 * @return The user by given id.
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Long userId) {

		LOGGER.info("Get user from the database by id" + userId);
		return ResponseEntity.ok(userService.getUserById(userId));
	}
}