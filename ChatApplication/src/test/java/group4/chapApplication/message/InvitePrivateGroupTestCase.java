package group4.chapApplication.message;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.UserInviteForPrivateGroupUseCase;

class InvitePrivateGroupTestCase {

	private DataStorage _dataStorage;
    private UserInviteForPrivateGroupUseCase _useCase;

    @BeforeEach
    public void setUp() {
        _dataStorage = new InMemoryDataStorage();
        _useCase = new UserInviteForPrivateGroupUseCase(_dataStorage);
    }

    @Test
    public void testUserInviteForPrivateGroupSuccess() throws Exception {
        String adminId = "adminUser";
        String userId = "invitedUser";
        String groupId = "privateGroup";

        User admin = new User(adminId, "123");
        User user = new User(userId, "123");
        PrivateGroup privateGroup = new PrivateGroup(admin, groupId);

        _dataStorage.getUsers().add(admin);
        _dataStorage.getUsers().add(user);
        _dataStorage.getPrivateGroup().add(privateGroup);

        UserInviteForPrivateGroupUseCase.InputValues inputValues =
                new UserInviteForPrivateGroupUseCase.InputValues(adminId, userId, groupId);

        UserInviteForPrivateGroupUseCase.OutputValues outputValues = _useCase.execute(inputValues);

        assertEquals(UserInviteForPrivateGroupUseCase.ResultCodes.SUCCESS, outputValues.getResultCode(),outputValues.getMessage());
        // assertEquals(1, "User has been invited to join the group", outputValues.getMessage());

        // assertNotNull(privateGroup.getGroupUsers(), "User should have received an invitation");
        // assertEquals(1, privateGroup.getSize(), "User should have received one invitation");

        // User userInvited = privateGroup.getUser(user);
        // assertEquals(1, "You have been invited to join the private group: " + groupId, userInvited.toString() +
        //         " Invitation message should match");
    }

}
