package group4.chat.domains.groupUser.privateGroup;

import java.util.List;
import java.util.ArrayList;
import group4.chat.domains.User;
import group4.chat.domains.groupUser.GroupUsers;

public class PrivateGroup extends GroupUsers {

    private User _firstAdmin;
    protected List<User> _listAdmins;
    private List<GroupRequest> _groupRequests;

    public PrivateGroup(User admin) {
        super();
        this._firstAdmin = admin;
        this._listAdmins = new ArrayList<>();
        this._groupUsers.add(admin);
    }

    public PrivateGroup(String groupId) {
        // this._groupID = groupId;
        setId(groupId);
        this._groupUsers = new ArrayList<>();
        this._listAdmins = new ArrayList<>();
        this._groupRequests = new ArrayList<>();
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

    public List<User> getGroupAdmins() {
        return _listAdmins;
    }

    public boolean findAdmin(User admin) {
        if (_firstAdmin.getId() == admin.getId()) {
            return true;
        }
        for (User user : _listAdmins) {
            if (user.getId() == admin.getId()) {
                return true;
            }
        }
        return false;
    }

    public List<User> getGroupUsers() {
        return _groupUsers;
    }

    public boolean isAdmin(User user) {
        return _listAdmins.contains(user);
    }

    public int getSize() {
        return _groupUsers.size();
    }

    public User getUser(User user) {
        for (int i = 0; i < _groupUsers.size(); i++) {
            if (_groupUsers.get(i) == user) {
                return user;
            }
        }
        return null;
    }

    public void requestToJoin(User user) {
        GroupRequest request = new GroupRequest(user, this);
        _groupRequests.add(request);
    }

    public void approveRequest(GroupRequest request) {
        if (_groupRequests.contains(request) && _listAdmins.contains(request.getAdmin())) {
            _groupUsers.add(request.getRequestingUser());
            _groupRequests.remove(request);
        }
    }

    public void rejectRequest(GroupRequest request) {
        if (_groupRequests.contains(request) && _listAdmins.contains(request.getAdmin())) {
            _groupRequests.remove(request);
        }
    }

    public List<GroupRequest> getGroupRequests() {
        return _groupRequests;
    }
}