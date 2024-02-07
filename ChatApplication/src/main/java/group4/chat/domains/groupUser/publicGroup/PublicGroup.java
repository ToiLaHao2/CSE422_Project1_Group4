package group4.chat.domains.groupUser.publicGroup;

import java.util.List;
import java.util.Random;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.GroupUsers;

public class PublicGroup extends GroupUsers {
    private String _joinCode;
    private User _user;
    protected List<User> _listUsers;
    private User user;

    public PublicGroup(String _joincode, User user) {
        super();
        this._joinCode = _joincode;
        this._groupUsers.add(_user);

    }

    private void generateJoinCode() {
        Random rd = new Random(5);
        this._joinCode = rd.toString();
    }

    public String outputJoinCode() {
        return this._joinCode;
    }

    public void addMember(User user) {
        if (!_groupUsers.contains(user)) {
            _groupUsers.add(user);
            System.out.println("Successful added user");
        } else {
            System.out.println("The user is already a member of the group");
        }
    }

    public void removeMember(User user) {
        if (_groupUsers.contains(user)) {
            _groupUsers.remove(user);
            System.out.println("Remove user successfully");
        } else {
            System.out.println("The user is not in this group. Please choose another group");
        }

    }

    public String getJoinCode() {
        return _joinCode;
    }

    public boolean findUser(String userID) {
        boolean check;
        for (User user : _listUsers) {
            if (user.getId().equals(userID)) {
                check = true;
                break;
            }
        }
        check = false;
        return check;
    }

}