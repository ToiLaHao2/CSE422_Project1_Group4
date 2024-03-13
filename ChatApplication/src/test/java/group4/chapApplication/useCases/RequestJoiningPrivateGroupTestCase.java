package group4.chapApplication.useCases;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.RemoveUserFromAGroupUseCase;
import group4.chat.usecases.users.RequestToJoinPrivateGroupUseCase;

class RequestJoiningPrivateGroupTestCase {
    private RequestToJoinPrivateGroupUseCase _requestJoining;
    private InMemoryDataStorage _dataStorage;

    @BeforeEach
    public void setUp() {
        _dataStorage = new InMemoryDataStorage();
        _requestJoining = new RequestToJoinPrivateGroupUseCase(_dataStorage);
    }

    private DataStorage dataStorage;
    private RequestToJoinPrivateGroupUseCase useCase;

    @Test
    void testJoinGroupSuccessfully() {
        String userID = "userId1";
        String groupID = "groupId1";
        User admin = new User("Admin1", "123");
        User user = new User("User1", "123");
        user.setId(userID);

        PrivateGroup group = new PrivateGroup(admin, null);
        group.setId(groupID);

        dataStorage.getUsers().add(admin);
        dataStorage.getUsers().add(user);
        dataStorage.getPrivateGroup().add(group);

        RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(
                userID, groupID);
        RequestToJoinPrivateGroupUseCase.OutputValues outputValues = useCase.execute(inputValues);

    }

    @Test
    void testUserAlreadyInGroup() {
        String adminId = "admin1";
        String userId = "user1";
        User admin = new User("Admin1", "123");
        User user = new User("User1", "123");
        admin.setId(adminId);
        user.setId(userId);

        String groupId = "group1";
        PrivateGroup group = new PrivateGroup(admin, "Group1");
        group.setId(groupId);
        group.addAdmin(admin);
        group.addMember(user);

        dataStorage.getUsers().add(admin);
        dataStorage.getUsers().add(user);
        dataStorage.getPrivateGroup().add(group);

        RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(
                userId, groupId);
        RequestToJoinPrivateGroupUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertEquals(false, outputValues.isRequestApproved());
    }

    @Test
    void testUserAlreadyRequested() {
        String adminId = "admin1";
        String userId = "user1";
        User admin = new User("Admin1", "123");
        User user = new User("User1", "123");
        admin.setId(adminId);
        user.setId(userId);

        String groupId = "group1";
        PrivateGroup group = new PrivateGroup(admin, "Group1");
        group.setId(groupId);
        group.addAdmin(admin);
        group.requestToJoin(user);

        dataStorage.getUsers().add(admin);
        dataStorage.getUsers().add(user);
        dataStorage.getPrivateGroup().add(group);

        RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(
                userId, groupId);
        RequestToJoinPrivateGroupUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertEquals(false, outputValues.isRequestApproved());
    }

}
