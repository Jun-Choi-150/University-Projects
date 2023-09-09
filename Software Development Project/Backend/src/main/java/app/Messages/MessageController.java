package app.Messages;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.Posts.Post;
import app.Users.User;
import app.Users.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */ 

@Api(value = "Message Controller", tags="4. Message")
@RestController
public class MessageController {
	
    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";


    @ApiOperation(value = "Get data from all messages", response = Message.class, responseContainer = "List")
    @GetMapping("/messages")
    List<Message> getAllMessages(){
        return messageRepository.findAll();
    }


    @ApiOperation(value = "Create a user's message", response = String.class)
    @PostMapping("users/{userId}/create/message/{friendId}")
    String createMessage(@PathVariable("userId") long userId, @PathVariable("friendId") long friendId, @RequestBody Message message){
        Optional<User> senderOptional = userRepository.findById(userId);
        Optional<User> recipientOptional = userRepository.findById(friendId);
        
        if (!senderOptional.isPresent() || !recipientOptional.isPresent()) {
            return failure;
        }

        User sender = senderOptional.get();
        User recipient = recipientOptional.get();

        Message newMessage = new Message();
        newMessage.setSender(sender);
        newMessage.setRecipient(recipient);
        newMessage.setText(message.getText());
        sender.getSentMessages().add(newMessage);
        recipient.getReceivedMessages().add(newMessage);
        
        messageRepository.save(newMessage);

        return "Message created";
    }  
        

    @ApiOperation(value = "Delete user's message", response = String.class)   
    @DeleteMapping("users/{userId}/delete/message/{friendId}")
    String deleteMessage(@PathVariable("userId") long userId, @PathVariable("friendId") long friendId) {
        List<Message> messages = messageRepository.findBySenderIdAndRecipientId(userId, friendId);
        for (Message message : messages) {
            messageRepository.delete(message);
        }
        return success;
    }
    
    
}