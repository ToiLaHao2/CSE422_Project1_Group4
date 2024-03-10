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

    private DataStorage dataStorage;
    private RequestToJoinPrivateGroupUseCase useCase;

    @BeforeEach
    public void setUp() {
        dataStorage = new InMemoryDataStorage();
        useCase = new RequestToJoinPrivateGroupUseCase(dataStorage);
    }

    @Test
    void testJoinGroupSuccessfully() {
        String userID = "userId1";
        String groupID = "groupId1";
        User admin = new User("Admin1", "123");
        User user = new User("User1", "123");
        user.setId(userID);

        PrivateGroup group = new PrivateGroup(admin, null);
        group.setId(groupID);
        group.addAdmin(admin);
        dataStorage.getUsers().add(admin);
        dataStorage.getUsers().add(user);
        dataStorage.getPrivateGroup().add(group);

        RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(
                userID, groupID);
        RequestToJoinPrivateGroupUseCase.OutputValues outputValues = useCase.execute(inputValues);

        assertEquals(true, outputValues.isRequestApproved());
    }

    @Test
    void testUserAlreadyInGroup() {
        User admin = new User("Admin1", "123");
        User user = new User("User1", "123");

        PrivateGroup group = new PrivateGroup("Group1");
        group.addAdmin(admin);
        group.addMember(user);

        RequestToJoinPrivateGroupUseCase request = new RequestToJoinPrivateGroupUseCase();

        RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(
                user, group);
        RequestToJoinPrivateGroupUseCase.OutputValues outputValues = request.execute(inputValues);

        assertEquals(false, outputValues.isRequestApproved());
    }

    @Test
    void testUserAlreadyRequested() {
        User admin = new User("Admin1", "123");
        User user = new User("User1", "123");

        PrivateGroup group = new PrivateGroup("Group1");
        group.addAdmin(admin);
        group.requestToJoin(user);

        RequestToJoinPrivateGroupUseCase request = new RequestToJoinPrivateGroupUseCase();

        RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(
                user, group);
        RequestToJoinPrivateGroupUseCase.OutputValues outputValues = request.execute(inputValues);

        assertEquals(false, outputValues.isRequestApproved());
    }

}
