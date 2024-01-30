package group4.chat.domains.groupUser.publicGroup;

import java.util.Random;

import group4.chat.domains.groupUser.GroupUsers;

public class PublicGroup extends GroupUsers {
private String _joinCode;
    public PublicGroup() {
        super();
    }
    private void generateJoinCode() {
        Random rd=new Random(5);
        this._joinCode=rd.toString();
    }
    public String outputJoinCode(){
        return this._joinCode;
    }
    

    

}