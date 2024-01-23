package group4.chat.domains.groupUser;

import java.util.ArrayList;
import java.util.List;

import group4.chat.domains.User;

public class GroupUsers {

    protected List<User> _groupUsers;

    public GroupUsers() {
        super();
        this._groupUsers = new ArrayList<User>();
    }

}

class PublicGroup extends GroupUsers {

    public PublicGroup() {
        super();
    }
}

class PrivateGroup extends GroupUsers {

    private User _admin;

    public PrivateGroup(String name, User admin) {
        super();
        this._admin = admin;
        this._groupUsers.add(admin);
    }
}
