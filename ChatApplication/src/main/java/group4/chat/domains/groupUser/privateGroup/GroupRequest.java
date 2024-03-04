package group4.chat.domains.groupUser.privateGroup;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;

public class GroupRequest {
    private User _requestingUser;
    private PrivateGroup _group;
    private User _admin;

    public GroupRequest(User requestingUser, PrivateGroup group) {
        this._requestingUser = requestingUser;
        this._group = group;
    }

    public User getRequestingUser() {
        return _requestingUser;
    }

    public PrivateGroup getGroup() {
        return _group;
    }

    public User getAdmin() {
        return _admin;
    }

    public void setAdmin(User admin) {
        this._admin = admin;
    }
}

