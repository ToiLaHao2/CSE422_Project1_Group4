package group4.chat.usecases.users;

import java.util.ArrayList;
import java.util.List;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class JoinGroupUseCase
 extends UseCase<JoinGroupUseCase.InputValues, JoinGroupUseCase.OutputValues>  {
    private String joinCode;
    private List<User> members=new ArrayList<>();
    private String groupName;
    private PublicGroup publicGroup;
    private User user;
    private DataStorage _dataStorage;

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
    public static class InputValues {
        private String _groupID;
        private String _userID;

        public InputValues(String groupID, String userID) {
            _groupID = groupID;
            _userID  = userID;
        }
    
    }
    public static class OutputValues {
        private final int _resultCode;
        private final String _message;

        public OutputValues(int resultCode, String message) {
            _message = message;
            _resultCode = resultCode;
        }

        public int getResultCode(){
            return _resultCode;
        }

        public String getMessage(){
            return _message;
        }
    }

    public static class ResultCodes {
        public static final int SUCCESS = 1;
        public static final int FAILED = 0;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        _dataStorage.getUsers().getById(_userID);
        if (input.equals(joinCode)) {
            members.add();
            System.out.println("Joined group '" + groupName + "' successfully!");
            return true;
    }
}
    
    
    

