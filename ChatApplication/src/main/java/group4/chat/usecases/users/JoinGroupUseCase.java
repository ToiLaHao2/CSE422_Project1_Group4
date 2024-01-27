package group4.chat.usecases.users;

import java.util.ArrayList;
import java.util.List;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;

public class JoinGroupUseCase {
    private String joinCode;
    private List<User> members=new ArrayList<>();
    private String groupName;
    private PublicGroup publicGroup;
    private User user;
    public JoinGroupUseCase(String joinCode) {
        this.joinCode = joinCode;
    }
    public String getJoinCode() {
        return joinCode;
    }
    public List<User> getMembers() {
        return members;
    }
    public String getGroupName() {
        return groupName;
    }
    public boolean joinGroupByJoinCode(String code){
        if (code.equals(joinCode)) {
            members.add(user);
            System.out.println("Join group successfully");
            return true;
        } else {
            System.out.println("Invalid join code. Failed to join group.");
            return false;
        }
    }
    
    
}
