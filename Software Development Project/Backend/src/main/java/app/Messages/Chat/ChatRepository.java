package app.Messages.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ChatRepository {

    private List<Message> messages;

    public ChatRepository() {
        messages = new ArrayList<>();
    }

    public void save(Message message) {
        messages.add(message);
    }

    public List<Message> findAll() {
        return messages;
    }

    public List<Message> findByMovieId(String movieId) {
        return messages.stream()
                .filter(message -> message.getMovieId().equals(movieId))
                .collect(Collectors.toList());
    }
}
