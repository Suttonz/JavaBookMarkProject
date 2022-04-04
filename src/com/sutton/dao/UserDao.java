package com.sutton.dao;

import com.sutton.DataStore;
import com.sutton.entities.User;

import java.util.List;

public class UserDao {
	
	public List<User> getUsers() {
		return DataStore.getUsers();
	}

}
