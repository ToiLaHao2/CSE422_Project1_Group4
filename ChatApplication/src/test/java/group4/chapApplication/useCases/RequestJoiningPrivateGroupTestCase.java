package group4.chapApplication.useCases;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.usecases.users.RequestToJoinPrivateGroupUseCase;

class RequestJoiningPrivateGroupTestCase {

	@Test
    void testJoinGroupSuccessfully() {
        User admin = new User("Admin1", "123");
        User user = new User("User1", "123");

        PrivateGroup group = new PrivateGroup(admin);
        group.addAdmin(admin);

        RequestToJoinPrivateGroupUseCase request = new RequestToJoinPrivateGroupUseCase();

        RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(user, group);
        RequestToJoinPrivateGroupUseCase.OutputValues outputValues = request.execute(inputValues);

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

        RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(user, group);
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

        RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(user, group);
        RequestToJoinPrivateGroupUseCase.OutputValues outputValues = request.execute(inputValues);

        assertEquals(false, outputValues.isRequestApproved());
    }

}
