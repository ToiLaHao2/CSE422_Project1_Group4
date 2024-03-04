package group4.chapApplication.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Message;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.message.SendMessageUseCase;

class SendMessageTestCase {

    private DataStorage _dataStorage;
    private SendMessageUseCase _useCase;

    @BeforeEach
    public void setUp() {
        _dataStorage = new InMemoryDataStorage();
        _useCase = new SendMessageUseCase(_dataStorage);
    }

    @Test
    public void testSendMessageSuccess() throws Exception {
        String senderId = "senderUser";
        String receiverId = "receiverUser";
        String content = "Hii";

        User sender = new User(senderId, "123");
        sender.setId(senderId);
        User receiver = new User(receiverId, "123");
        receiver.setId(receiverId);
        
        _dataStorage.getUsers().add(sender);
        _dataStorage.getUsers().add(receiver);

        SendMessageUseCase.InputValues inputValues = new SendMessageUseCase.InputValues(null, receiverId, content, 1,
                senderId);

        SendMessageUseCase.OutputValues outputValues = _useCase.execute(inputValues);

        assertEquals(SendMessageUseCase.ResultCodes.SUCCESS, outputValues.getResultCode(), outputValues.getMessage());
        assertEquals("Sending message successful", outputValues.getMessage());

        Message sentMessage = new Message(123, senderId, receiverId, content);

        assertNotNull(sentMessage, "Message should be saved in the data storage");
        assertEquals(senderId, sentMessage.get_sender(), "Sender ID should match");
        assertEquals(receiverId, sentMessage.get_receiver(), "Receiver ID should match");
        assertEquals(content, sentMessage.get_content(), "Content should match");
    }

    @Test
    public void testSendMessageWithAttachment_Success() throws Exception {
        String senderId = "senderUser";
        String receiverId = "receiverUser";
        String content = "Check out this attachment!";
        byte[] attachment = "Sample attachment content".getBytes();

        User sender = new User(senderId, "123");
        User receiver = new User(receiverId, "123");

        _dataStorage.getUsers().add(sender);
        _dataStorage.getUsers().add(receiver);

        SendMessageUseCase.InputValues inputValues = new SendMessageUseCase.InputValues(attachment, receiverId, content,
                1, senderId);

        SendMessageUseCase.OutputValues outputValues = _useCase.execute(inputValues);

        assertEquals(SendMessageUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
        assertEquals("Sending message successfull", outputValues.getMessage());

        Message sentMessage = new Message(123, senderId, receiverId, content);
        assertNotNull(sentMessage, "Message should be saved in the data storage");
        assertEquals(senderId, sentMessage.get_sender(), "Sender ID should match");
        assertEquals(receiverId, sentMessage.get_receiver(), "Receiver ID should match");
        assertEquals(content, sentMessage.get_content(), "Content should match");
        assertEquals(sentMessage.get_attachments(), "Message should have an attachment");
    }

}
