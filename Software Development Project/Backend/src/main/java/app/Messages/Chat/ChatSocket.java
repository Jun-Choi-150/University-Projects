package app.Messages.Chat;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/chat/{movieId}/{username}")  // this is Websocket url
public class ChatSocket {

  // cannot autowire static directly (instead we do it by the below method
	private static ChatRepository msgRepo; 

	/*
   * Grabs the MessageRepository singleton from the Spring Application
   * Context.  This works because of the @Controller annotation on this
   * class and because the variable is declared as static.
   * There are other ways to set this. However, this approach is
   * easiest.
	 */
	@Autowired
	public void setMessageRepository(ChatRepository repo) {
		msgRepo = repo;  // we are setting the static variable
	}

	// Store all socket session and their corresponding username.
	private static Map<String, Map<Session, String>> movieSessionUsernameMap = new Hashtable<>();
	private static Map<String, Map<String, Session>> movieUsernameSessionMap = new Hashtable<>();


	private final Logger logger = LoggerFactory.getLogger(ChatSocket.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("movieId") String movieId, @PathParam("username") String username)
	        throws IOException {

	    logger.info("Entered into Open");

	    // Create a new Map for the movieId if it doesn't exist
	    if (!movieSessionUsernameMap.containsKey(movieId)) {
	        movieSessionUsernameMap.put(movieId, new Hashtable<>());
	        movieUsernameSessionMap.put(movieId, new Hashtable<>());
	    }

	    // Get the session maps associated with the movieId
	    Map<Session, String> sessionUsernameMap = movieSessionUsernameMap.get(movieId);
	    Map<String, Session> usernameSessionMap = movieUsernameSessionMap.get(movieId);

	    // Store connecting user information
	    sessionUsernameMap.put(session, username);
	    usernameSessionMap.put(username, session);

	    // Send chat history to the newly connected user
	    sendMessageToPArticularUser(movieId, username, getChatHistory(movieId));

	    // Broadcast that a new user joined
	    String message = username + " has joined the Chat";
	    broadcast(movieId, message);
	}


	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
	    // Handle new messages
	    logger.info("Entered into Message: Got Message:" + message);

	    // Find the movieId for the session
	    String movieId = findMovieIdBySession(session);
	    if (movieId == null) {
	        logger.error("MovieId not found for session " + session.getId());
	        return;
	    }

	    Map<Session, String> sessionUsernameMap = movieSessionUsernameMap.get(movieId);
	    String username = sessionUsernameMap.get(session);

	    // Check if the user entered "y"
	    if (message.equalsIgnoreCase("y")) {
	        sendMessageToPArticularUser(movieId, username, "You have entered a chat room with a spoiler!");
	        onOpen(session, "0", username);
	    } else {
	        // Direct message to a user using the format "@username <message>"
	        if (message.startsWith("@")) {
	            String destUsername = message.split(" ")[0].substring(1);

	            // Send the message to the sender and receiver
	            sendMessageToPArticularUser(movieId, destUsername, "[DM] " + username + ": " + message);
	            sendMessageToPArticularUser(movieId, username, "[DM] " + username + ": " + message);

	        } else { // Broadcast
	            broadcast(movieId, username + ": " + message);
	        }

	        // Saving chat history to repository
	        msgRepo.save(new Message(movieId, username, message));
	    }
	}


	@OnClose
	public void onClose(Session session) throws IOException {
	    logger.info("Entered into Close");

	    // Find the movieId for the session
	    String movieId = findMovieIdBySession(session);
	    if (movieId == null) {
	        logger.error("MovieId not found for session " + session.getId());
	        return;
	    }

	    Map<Session, String> sessionUsernameMap = movieSessionUsernameMap.get(movieId);
	    Map<String, Session> usernameSessionMap = movieUsernameSessionMap.get(movieId);

	    // Remove the user connection information
	    String username = sessionUsernameMap.get(session);
	    sessionUsernameMap.remove(session);
	    usernameSessionMap.remove(username);

	    // Broadcast that the user disconnected
	    String message = username + " disconnected";
	    broadcast(movieId, message);
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}


	private void sendMessageToPArticularUser(String movieId, String username, String message) {
	    Map<String, Session> usernameSessionMap = movieUsernameSessionMap.get(movieId);
	    try {
	        usernameSessionMap.get(username).getBasicRemote().sendText(message);
	    } catch (IOException e) {
	        logger.info("Exception: " + e.getMessage().toString());
	        e.printStackTrace();
	    }
	}



	private void broadcast(String movieId, String message) {
	    Map<Session, String> sessionUsernameMap = movieSessionUsernameMap.get(movieId);
	    sessionUsernameMap.forEach((session, username) -> {
	        try {
	            session.getBasicRemote().sendText(message);
	        } catch (IOException e) {
	            logger.info("Exception: " + e.getMessage().toString());
	            e.printStackTrace();
	        }
	    });
	}
	

  // Gets the Chat history from the repository
	private String getChatHistory(String movieId) {
	    List<Message> messages = msgRepo.findByMovieId(movieId);

	    // convert the list to a string
	    StringBuilder sb = new StringBuilder();
	    if (messages != null && messages.size() != 0) {
	        for (Message message : messages) {
	            sb.append(message.getUserName() + ": " + message.getContent() + "\n");
	        }
	    }
	    return sb.toString();
	}

	
	private String findMovieIdBySession(Session session) {
	    for (Map.Entry<String, Map<Session, String>> entry : movieSessionUsernameMap.entrySet()) {
	        String movieId = entry.getKey();
	        Map<Session, String> sessionUsernameMap = entry.getValue();

	        if (sessionUsernameMap.containsKey(session)) {
	            return movieId;
	        }
	    }
	    return null;
	}


} // end of Class
