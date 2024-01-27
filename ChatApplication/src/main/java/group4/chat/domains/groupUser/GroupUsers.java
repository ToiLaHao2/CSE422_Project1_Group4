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

    // find a user
    public User findUser(String username) {
        for (int i = 0; i < this._groupUsers.size(); ++i) {
            if (this._groupUsers.get(i).get_fullName().equalsIgnoreCase(username)) {
                return this._groupUsers.get(i);
            }
        }
        return null;
    }

}
