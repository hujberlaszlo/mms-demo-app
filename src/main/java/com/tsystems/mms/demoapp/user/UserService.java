package com.tsystems.mms.demoapp.user;

import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsystems.mms.demoapp.organisational_unit.OrganisationalUnit;
import com.tsystems.mms.demoapp.organisational_unit.OrganisationalUnitRepository;
import com.tsystems.mms.demoapp.user.exceptions.EmailValidationException;
import com.tsystems.mms.demoapp.user.exceptions.UserNotFoundException;

/**
 * This service manages all user.
 */
@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final OrganisationalUnitRepository organisationalUnitRepository;

	public UserService(UserRepository userRepository, OrganisationalUnitRepository organisationalUnitRepository) {
		this.userRepository = userRepository;
		this.organisationalUnitRepository = organisationalUnitRepository;
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
	 * @return UserDto by given id.
	 */
	public UserDto getUserById(Long userId) {
		User user = findUserById(userId);
		return mapToUserDto(user);
	}

	/**
	 * Create user.
	 * 
	 * @param User to save in database.
	 * @throws EmailValidationException when the user is not found with given id.
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
		User userToUpdate = findUserById(userId);
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setSurName(user.getSurName());
		userToUpdate.setGender(user.getGender());
	}

	/**
	 * Create user.
	 * 
	 * @param User id.
	 * @throws EmailValidationException when the email is invalid.
	 */
	public void deleteUser(Long userId) {
		User user = findUserById(userId);
		userRepository.delete(user);

	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id:" + userId));
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

	/**
	 * Maps user entity into userDto.
	 * 
	 * @param User entity.
	 */
	private UserDto mapToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setEmail(user.getEmail());
		userDto.setFirstName(user.getFirstName());
		userDto.setSurName(user.getSurName());
		userDto.setGender(user.getGender().toString());
		userDto.setOrganisationalUnit(user.getOrganisationalUnit().getName());
		userDto.setId(user.getId());

		return userDto;
	}

	/**
	 * Assigns the user with the given id to the Org unit with the given id.
	 * 
	 * @param User id.
	 * @param Org  Unit id.
	 * @throws UserNotFoundException when the user is not found with given id.
	 */
	public void assignOrganisationalUnit(Long userId, Long organisationalUnitId) {
		User user = findUserById(userId);
		OrganisationalUnit organisationalUnit = organisationalUnitRepository.findById(organisationalUnitId)
				.orElseThrow(() -> new EntityNotFoundException("Org unit not found with id:" + organisationalUnitId));

		user.setOrganisationalUnit(organisationalUnit);

	}
}
