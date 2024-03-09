package group4.chat.domains.groupUser.publicGroup;

import java.util.List;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.GroupUsers;

public class PublicGroup extends GroupUsers {

    private String _joinCode;
    protected List<User> _listUsers;
    private String _name;

    public PublicGroup(String joincode) {
        super();
        this._joinCode = joincode;
        
    }
    public PublicGroup(String joincode, String name) {
        super();
        this._joinCode = joincode;
        this._name = name;
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

    public List<User> getGroupUsers() {
        return _groupUsers;
    }

    public boolean searchUser(String userID) {
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
    public String getName() {
        return _name;
    }

}