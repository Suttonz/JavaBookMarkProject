package com.sutton.mangers;

import com.sutton.constants.Gender;
import com.sutton.constants.UserType;
import com.sutton.dao.UserDao;
import com.sutton.entities.User;

import java.util.List;

public class UserManager {

	private static UserManager instance = new UserManager();
	private static UserDao dao = new UserDao();

	public List<User> getUsers() {
		return dao.getUsers();
	}

	private UserManager() {
	}

	public static UserManager getInstance() {
		return instance;
	}

	public User createUser(long id, String email, String passeWord, String firstName, String lastName, Gender gender,
			UserType userType) {

		User user = new User();

		user.setId(id);
		user.setEmail(email);
		user.setPasseWord(passeWord);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setUserType(userType);

		return user;
	}

}
