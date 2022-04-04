package com.sutton.constants;

public enum UserType {
	

	
	 USER("user"),
	 EDITOR ("editor"),
	 CHIEF_EDITOR("chiefeditor");

	 private String type;

	 private UserType(String type){
		 this.type = type;
	 }

	public String getType() {
		return type;
	}
}
