package group4.chat.domains.groupUser.privateGroup;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.GroupUsers;

public class PrivateGroup extends GroupUsers {

    private User _admin;

    public PrivateGroup(String name, User admin) {
        super();
        this._admin = admin;
        this._groupUsers.add(admin);
    }
}