package com.tsystems.mms.demoapp.user;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	 * Create a new user.
	 * 
	 * @param User to save.
	 * @return The location of the created user.
	 */
	@PostMapping("/user")
	public ResponseEntity<Void> createUser(@RequestBody User user) {

		LOGGER.info("Create new user in database");
		Long userId = userService.createUser(user);
		return ResponseEntity.created(URI.create("/api/v1.0/user/" + userId)).build();
	}

	/**
	 * Finds user by id.
	 * 
	 * @param The user by given id.
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {

		LOGGER.info("Get user from the database by id" + userId);
		return ResponseEntity.ok(userService.getUserById(userId));
	}

	/**
	 * Modify user by given id.
	 * 
	 * @param User id.
	 * @param New user data values.
	 * @return The user by given id.
	 */
	@PutMapping("/user/{userId}")
	public ResponseEntity<Void> updateUserById(@PathVariable Long userId, @RequestBody User user) {

		LOGGER.info("Update user with id:" + userId);
		userService.updateUser(userId, user);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Delete user by given id.
	 * 
	 * @param User id. 
	 */
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {

		LOGGER.info("Delete user with id:" + userId);
		userService.deleteUser(userId);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Assign the organisational unit to the user with given id.
	 * 
	 * @param User id.
	 * @param Organisational unit id.
	 */
	@PutMapping("/user/{userId}/{organisationalUnitId}")
	public ResponseEntity<Void> updateUserById(@PathVariable Long userId, @PathVariable Long organisationalUnitId) {

		LOGGER.info("Assign user id:" + userId + " with organizational unit:" + organisationalUnitId);
		userService.assignOrganisationalUnit(userId, organisationalUnitId);
		return ResponseEntity.ok().build();
	}
}