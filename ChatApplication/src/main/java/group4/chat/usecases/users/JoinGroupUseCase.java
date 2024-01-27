package group4.chat.usecases.users;

import java.util.ArrayList;
import java.util.List;

import group4.chat.domains.User;

public class JoinGroupUseCase {
    private String joinCode;
    private List<User> members=new ArrayList<>();
    private String groupName;
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
    
    
}
