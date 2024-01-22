package group4.chat.domains;

import java.util.Date;

public class User extends BaseEntity {
	private String _firstName;
	private String _lastName;
	private String _fullName;
	private String _hashedPassword;
	private Gender _gender;
	private Date _dateOfBirth;

	public enum Gender {
		MALE, FEMALE, OTHER
	}

	public User(String _firstName, String _lastName, String _hashedPassword, Gender _gender, Date _dateOfBirth) {
		super();
		this._firstName = _firstName;
		this._lastName = _lastName;
		this._hashedPassword = _hashedPassword;
		this._gender = _gender;
		this._dateOfBirth = _dateOfBirth;
	}

	public String get_firstName() {
		return _firstName;
	}

	public String get_lastName() {
		return _lastName;
	}

	public String get_fullName() {
		return this._firstName + " " + _lastName;
	}

	public String get_hashedPassword() {
		return _hashedPassword;
	}

	public Gender get_gender() {
		return _gender;
	}

	public Date get_dateOfBirth() {
		return _dateOfBirth;
	}

	public static class UserBuilder {
		private String _firstName;
		private String _lastName;
		private String _fullName;
		private String _hashedPassword;
		private Gender _gender;
		private Date _dateOfBirth;

		public UserBuilder(String _lastName, String _fullName, String _hashedPassword) {
			super();
			this._lastName = _lastName;
			this._fullName = _fullName;
			this._hashedPassword = _hashedPassword;
		}
		public UserBuilder(String _firstName, String _hashedPassword) {
			super();
			this._firstName = _firstName;
			this._hashedPassword = _hashedPassword;
		}
		
		public UserBuilder firstName(String firstName) {
			this._firstName = firstName;
			return this;
		}
		
		public UserBuilder lastName(String lastName) {
			this._lastName = lastName;
			return this;
		}
		
		public UserBuilder fullName(String firstName, String lastName) {
			this._fullName = firstName + " " + lastName;
			return this;
		}

		public UserBuilder gender(Gender gender) {
			this._gender = gender;
			return this;
		}
		
		public User build() {
			User user = new User(_firstName, _lastName, _hashedPassword, _gender, _dateOfBirth);
			return user;
		}
	}
}
