package group4.chat.domains.groupUser;

import java.util.ArrayList;

import group4.chat.domains.BaseEntity;
import group4.chat.domains.User;

public class GroupUsers extends BaseEntity {

    protected ArrayList<User> _groupUsers;
    protected String _groupName;

    public GroupUsers() {
        super();
        this._groupUsers = new ArrayList<User>();
    }

    // find a user
    public User findUser(String username) {
        for (int i = 0; i < this._groupUsers.size(); ++i) {
            if (this._groupUsers.get(i).get_fullName().equalsIgnoreCase(username)) {
                return this._groupUsers.get(i);
            }
        }
        return null;
    }

    public String getGroupName() {
        return this._groupName;
    }

}
