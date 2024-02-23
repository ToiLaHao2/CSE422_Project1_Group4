package group4.chat.usecases.users;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class LeaveGroupUseCase extends UseCase<LeaveGroupUseCase.InputValues, LeaveGroupUseCase.OutputValues> {
     private DataStorage dataStorage;

    public LeaveGroupUseCase(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        User user = dataStorage.getUsers().getById(input.userID);
        String groupID = input.getGroupID();
    
        PublicGroup publicGroup = dataStorage.getPublicGroup().getById(groupID);
        if (publicGroup != null) {
            publicGroup.removeMember(user);
            return new OutputValues(ResultCodes.SUCCESS, "User has left the group");
        }
    
        PrivateGroup privateGroup = dataStorage.getPrivateGroup().getById(groupID);
        if (privateGroup != null) {
            privateGroup.removeMember(user);
            return new OutputValues(ResultCodes.SUCCESS, "User has left the group");
        }
    
        return new OutputValues(ResultCodes.FAILED, "Group not found");
    
    }

    public static class InputValues {
        private String userID;
        private String groupID;

        public InputValues(String userID, String groupID) {
            this.userID = userID;
            this.groupID = groupID;
        }

        public String getUserID() {
            return userID;
        }

        public String getGroupID() {
            return groupID;
        }
    }

    public static class OutputValues {
        private final int _resultCode;
        private final String _message;

        public OutputValues(int resultCode, String message) {
            _message = message;
            _resultCode = resultCode;
        }

        public int getResultCode() {
            return _resultCode;
        }

        public String getMessage() {
            return _message;
        }

    }

    public static class ResultCodes {
        public static final int SUCCESS = 1;
        public static final int FAILED = 0;
    }

}