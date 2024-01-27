package group4.chat.domains.groupUser.privateGroup;

import java.util.List;
import java.util.ArrayList;
import group4.chat.domains.User;
import group4.chat.domains.groupUser.GroupUsers;

public class PrivateGroup extends GroupUsers {

    private String _groupID;
    private User _admin;
    protected List<User> _listAdmin;

    public PrivateGroup(User admin) {
        super();
        this._admin = admin;
        this._listAdmin = new ArrayList<>();
        
        this._groupUsers.add(admin);
    }

}