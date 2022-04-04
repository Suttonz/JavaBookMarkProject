package com.sutton.entities;

import com.sutton.constants.Gender;
import com.sutton.constants.UserType;

public class User {

	private long id;
	private String email;
	private String passeWord;
	private String firstName;
	private String lastName;
	private Gender gender;
	private UserType userType;

	public void setId(long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPasseWord(String passeWord) {
		this.passeWord = passeWord;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPasseWord() {
		return passeWord;
	}

	public Gender getGender() {
		return gender;
	}

	public UserType getUserType() {
		return userType;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", passeWord=" + passeWord + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", gender=" + gender + ", userType=" + userType + "]";
	}

}
