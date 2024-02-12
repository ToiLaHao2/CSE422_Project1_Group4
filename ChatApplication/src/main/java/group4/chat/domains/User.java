package group4.chat.domains;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User extends BaseEntity {
	private String _firstName;
	private String _lastName;
	private String _fullName;
	private String _hashedPassword;
	private Gender _gender;
	private Date _dateOfBirth;
	private List<String> _listGroupOfUser;

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
		this._listGroupOfUser = new ArrayList<>();
	}

	public User(String _firstName, String _hashedPassword) {
		this._firstName = _firstName;
		this._hashedPassword = _hashedPassword;
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

	public void joinNewGroup(String groupID) {
		this._listGroupOfUser.add(groupID);
		System.out.println("You joined new group: " + groupID);
	}

	public void leaveGroup(String groupID) {
		this._listGroupOfUser.remove(groupID);
		System.out.println("You have left the group: " + groupID);
	}

	public void sendMessage(String receiverID, String content) {
		System.out.println("Message: '" + content + "''  has been sent to user with ID: " + receiverID);
	}

	public void sendAttachment(String receiverID, Byte[] attachment) {
		System.out.println("File: '" + attachment + "' has been sent to user with ID: " + receiverID);
	}
	public void receiveGroupInvite(String message) {
        System.out.println("Group invitation: " + message);
    }
}
