package group4.chat.domains.groupUser.privateGroup;

import java.util.List;
import java.util.ArrayList;
import group4.chat.domains.User;
import group4.chat.domains.groupUser.GroupUsers;

public class PrivateGroup extends GroupUsers {

    private String _groupID;
    private User _admin;
    protected List<User> _listAdmins;

    public PrivateGroup(User admin, String groupID) {
        super();
        this._admin = admin;
        this._listAdmins = new ArrayList<>();
        this._groupUsers.add(admin);
        this._groupID = groupID;
    }

    public void addAdmin(User user) {
        if (!_listAdmins.contains(user)) {
            _listAdmins.add(user);
            System.out.println("Successful added admin");
            addMember(user);
        } else {
            System.out.println("This admin is already an administrator of the group.");
        }
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
            System.out.println("The user is not in the group");
        }
        if (_listAdmins.contains(user)) {
            _listAdmins.remove(user);
        }
    }

    public String getGroupID() {
        return _groupID;
    }
    public List<User> getGroupAdmins() {
        return _listAdmins;
    }
    public List<User> getGroupUsers() {
        return _groupUsers;
    }
}