package app.Messages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	
	@Autowired
    MessageRepository messageRepository;

	public List<Message> getAllMessages()
    {
        return this.messageRepository.findAll();
    }
 
    public MessageService(MessageRepository messageRepository)
    {
        this. messageRepository =  messageRepository;
    }
}