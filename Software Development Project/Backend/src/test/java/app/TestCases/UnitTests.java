package app.TestCases;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.Messages.Message;
import app.Messages.MessageRepository;
import app.Messages.MessageService;
import app.Users.User;
import app.Users.UserRepository;
import app.Users.UserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UnitTests {
	
	@InjectMocks
	UserService userService;
	
	@InjectMocks
	MessageService msgService;
	
	@Mock 
	UserRepository userRepo;
	
	@Mock 
	MessageRepository msgRepo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void getAllUsersTest() {
		List<User> list = new ArrayList<User>();
		User user1 = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
		User user2 = new User("Joey Tribbiani", "User_Joey", "Joey123", "joeyt@iastate.edu", null);
		User user3 = new User("Ross Geller", "User_Ross", "Ross123", "rossg@iastate.edu", null);
		User user4 = new User("Monica Geller", "User_Monica", "Monica123", "monicag@iastate.edu", null);

		list.add(user1);
		list.add(user2);
		list.add(user3);
		list.add(user4);

		when(userRepo.findAll()).thenReturn(list);

		List<User> userList = userService.getAllUsers();

		assertEquals(4, userList.size());
		verify(userRepo, times(1)).findAll();
	}
	
	@Test
	public void getAllUsersUsernames() {
		User user1 = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
		User user2 = new User("Joey Tribbiani", "User_Joey", "Joey123", "joeyt@iastate.edu", null);
		User user3 = new User("Ross Geller", "User_Ross", "Ross123", "rossg@iastate.edu", null);
		User user4 = new User("Monica Geller", "User_Monica", "Monica123", "monicag@iastate.edu", null);

		assertEquals("User_Rachel", user1.getUsername());
		assertEquals("User_Joey", user2.getUsername());
		assertEquals("User_Ross", user3.getUsername());
		assertEquals("User_Monica", user4.getUsername());
	}
	
	@Test
	public void getAllUsersPasswords() {
		User user1 = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
		User user2 = new User("Joey Tribbiani", "User_Joey", "Joey123", "joeyt@iastate.edu", null);
		User user3 = new User("Ross Geller", "User_Ross", "Ross123", "rossg@iastate.edu", null);
		User user4 = new User("Monica Geller", "User_Monica", "Monica123", "monicag@iastate.edu", null);

		assertEquals("Rachel123", user1.getPassword());
		assertEquals("Joey123", user2.getPassword());
		assertEquals("Ross123", user3.getPassword());
		assertEquals("Monica123", user4.getPassword());
	}
	
	@Test
	public void getAllUsersEmails() {
		User user1 = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
		User user2 = new User("Joey Tribbiani", "User_Joey", "Joey123", "joeyt@iastate.edu", null);
		User user3 = new User("Ross Geller", "User_Ross", "Ross123", "rossg@iastate.edu", null);
		User user4 = new User("Monica Geller", "User_Monica", "Monica123", "monicag@iastate.edu", null);

		assertEquals("rachelg@iastate.edu", user1.getEmailId());
		assertEquals("joeyt@iastate.edu", user2.getEmailId());
		assertEquals("rossg@iastate.edu", user3.getEmailId());
		assertEquals("monicag@iastate.edu", user4.getEmailId());
	}
	
	@Test
	public void getAllUsersFriends() {
		Set<User> user1_friends = new HashSet<User>();
		Set<User> user2_friends = new HashSet<User>();
		Set<User> user3_friends = new HashSet<User>();
		
		User user1 = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
		User user2 = new User("Joey Tribbiani", "User_Joey", "Joey123", "joeyt@iastate.edu", null);
		User user3 = new User("Ross Geller", "User_Ross", "Ross123", "rossg@iastate.edu", null);

		user1_friends.add(user2);
		user1_friends.add(user3);
		user1.setFriends(user1_friends);
		
		user2_friends.add(user1);
		user2.setFriends(user2_friends);
		
		user3_friends.add(user1);
		user3.setFriends(user3_friends);

		
		assertEquals(user1_friends, user1.getFriends());
		assertEquals(user2_friends, user2.getFriends());
		assertEquals(user3_friends, user3.getFriends());
	}
	
	@Test
	public void getAllMessagesTest() {
		List<Message> list = new ArrayList<Message>();
		Message message1 = new Message((long) 1, "Hello!");
		Message message2 = new Message((long) 2, "Hey! How are you?");
		Message message3 = new Message((long) 3, "I'm good. Have you seen this new movie?");

		list.add(message1);
		list.add(message2);
		list.add(message3);

		when(msgRepo.findAll()).thenReturn(list);

		List<Message> msgList = msgService.getAllMessages();

		assertEquals(3, msgList.size());
		verify(msgRepo, times(1)).findAll();
	}
	
	@Test
	public void getAllMessagesText() {
		Message message1 = new Message((long) 1, "Hello!");
		Message message2 = new Message((long) 2, "Hey! How are you?");
		Message message3 = new Message((long) 3, "I'm good. Have you seen this new movie?");

		assertEquals("Hello!", message1.getText());
		assertEquals("Hey! How are you?", message2.getText());
		assertEquals("I'm good. Have you seen this new movie?", message3.getText());
	}
	
	@Test
	public void getAllMessagesSenderId() {
		Message message1 = new Message((long) 1, "Hello!");
		Message message2 = new Message((long) 2, "Hey! How are you?");
		Message message3 = new Message((long) 3, "I'm good. Have you seen this new movie?");
		User user1 = new User("Stacy Smith", "User_Stacy", "Stacy123", "stacy@iastate.edu", null);
		User user2 = new User("Callie Jones", "User_Callie", "Callie123", "cjones@iastate.edu", null);

		message1.setSender(user1);
		message2.setSender(user2);
		message3.setSender(user1);

		assertEquals(user1, message1.getSender());
		assertEquals(user2, message2.getSender());
		assertEquals(user1, message3.getSender());
	}
	
	@Test
	public void getAllMessagesRecepientId() {
		Message message1 = new Message((long) 1, "Hello!");
		Message message2 = new Message((long) 2, "Hey! How are you?");
		Message message3 = new Message((long) 3, "I'm good. Have you seen this new movie?");
		User user1 = new User("Stacy Smith", "User_Stacy", "Stacy123", "stacy@iastate.edu", null);
		User user2 = new User("Callie Jones", "User_Callie", "Callie123", "cjones@iastate.edu", null);

		message1.setRecipient(user2);
		message2.setRecipient(user1);
		message3.setRecipient(user2);

		assertEquals(user2, message1.getRecipient());
		assertEquals(user1, message2.getRecipient());
		assertEquals(user2, message3.getRecipient());
	}
}