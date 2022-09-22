package com.tsystems.mms.demoapp.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tsystems.mms.demoapp.organisational_unit.OrganisationalUnitRepository;
import com.tsystems.mms.demoapp.user.exceptions.EmailValidationException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private OrganisationalUnitRepository organisationalUnitRepository;

	private UserService userService;
	private List<User> testUsers;

	@BeforeEach
	public void setup() {
		userService = new UserService(userRepository, organisationalUnitRepository);
		testUsers = new ArrayList<>();
		testUsers.add(createUser(1L, "test1.user@foo.bar"));
		testUsers.add(createUser(2L, "test2.user@foo.bar"));
		testUsers.add(createUser(3L, "test3.user@foo.bar"));
	}

	@Test
	void testGetAll() {

		// Mock
		Mockito.when(userRepository.findAll()).thenReturn(testUsers);

		// Assert
		List<User> users = userService.getAll();
		Assertions.assertNotNull(users);
		Assertions.assertEquals(3, users.size());
		Assertions.assertEquals(testUsers.get(0), users.get(0));

		// Verify
		Mockito.verify(userRepository, Mockito.atMostOnce()).findAll();

	}

	private User createUser(Long id, String email) {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		return user;
	}

	@Test
	void testCreateUser_WhenEmailIsInvalid() {
		// Mock
		User user = testUsers.get(0);
		user.setEmail("invalidemail");
		Mockito.when(userRepository.save(user)).thenReturn(user);

		// Assert
		EmailValidationException exception = Assertions.assertThrows(EmailValidationException.class,
				() -> userService.createUser(user));
		Assertions.assertTrue(exception.getMessage().equals("Invalid email:" + user.getEmail()));
		// Verify
		Mockito.verify(userRepository, Mockito.never()).save(user);
	}

	@Test
	void testCreateUser_WhenEmailIsValid() {
		// Mock
		User user = testUsers.get(0);
		user.setEmail("hujber@test.com");
		Mockito.when(userRepository.save(user)).thenReturn(user);

		// Assert
		Long savedUserId = userService.createUser(user);
		Assertions.assertEquals(user.getId(), savedUserId);

		// Verify
		Mockito.verify(userRepository).save(user);
	}
}
