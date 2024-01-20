package group4.chat.domains;

import java.util.Date;

public class User {
	private String _firstName;
	private String _lastName;
	private String _fullName;
	private String _hashedPassword;
	private Gender _gender;
	private Date _dateOfBirth;

	public enum Gender {
		MALE, FEMALE, OTHER
	}
	
	
}
