package app.SystemTests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import app.Messages.Message;
import app.Permissions.Permission;
import app.Posts.Post;
import app.Users.User;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SystemTests {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    /*
     * User Tests
     */
    
    @Test
    public void createUserTest() {
        // Prepare test user data
        User testUser = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);

        // Send request and receive response
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(testUser)
            .when()
            .post("/users/create");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check if the user was created
        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.getString("message"));
        	
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getUserTest() {
        // Prepare test user data
        User testUser = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);

        // Send request and receive response
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(testUser)
            .when()
            .get("/users/all");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check if the user was created
        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.getString("message"));
        	
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
       
    @Test
    public void getUsersFriendTest() {
        // Prepare test user data
        User testUser1 = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
        User testUser2 = new User("Monica Geller", "User_Monica", "Monica123", "monicag@iastate.edu", null);
        
        Set <User> testUser1sFriends = new HashSet<User>();
        testUser1sFriends.add(testUser2);
        
        testUser1.setFriends(testUser1sFriends);

        // Send request and receive response
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(testUser1sFriends)
            .when()
            .get("users/1/get/2");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check if the user was created
        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.getString("message"));
        	
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void updateUsersFriendTest() {
        // Prepare test user data
        User testUser1 = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
        User testUser2 = new User("Monica Geller", "User_Monica", "Monica123", "monicag@iastate.edu", null);
        
        Set <User> testUser1sFriends = new HashSet<User>();
        testUser1sFriends.add(testUser2);
        
        testUser1.setFriends(testUser1sFriends);

        // Send request and receive response
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(testUser1sFriends)
            .when()
            .put("users/1/addFriend/2");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check if the user was created
        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.getString("message"));
        	
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void postUserPermissionTest() {
        // Prepare test user data -- user defaults to permission type 2 (General)
        User testUser = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
        Permission testPermission3 = new Permission(3, "Developer");
        
        // Send request and receive response
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(testUser)
            .when()
            .post("/users/update/1/permission/3");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check if the user was updated
        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.getString("message"));
        	
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getUsersPostTest() {
        // Prepare test user data
        User testUser = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
        
        Post post1 = new Post();
        post1.setContents("I thought this movie was awesome!");
        post1.setDate(new Date());
        post1.setId(1);
        post1.setMovieId(67);
        post1.setRating(4);
        post1.setTitle("Avatar");
        
        testUser.addPost(post1);
        
        Set <Post> testUsersPosts = new HashSet<Post>();
        testUsersPosts.add(post1);
        
        // Send request and receive response
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(testUsersPosts)
            .when()
            .get("/users/1/posts");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.getString("message"));
        	
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Message Tests
     */
    
    @Test
    public void createMessageTest() {
        // Prepare test message/user data
        Message testMessage = new Message((long) 1, "Hello!");
        User testUser1 = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
        User testUser2 = new User("Monica Geller", "User_Monica", "Monica123", "monicag@iastate.edu", null);
        
        Set <Message> testUser1sMessages = new HashSet<Message>();
        Set <User> testUser1sFriends = new HashSet<User>();
        testUser1sFriends.add(testUser2);
        Set <User> testUser2sFriends = new HashSet<User>();
        testUser2sFriends.add(testUser1);
        
        testUser1.setSentMessages(testUser1sMessages);
        testUser2.setReceivedMessages(testUser1sMessages);
        testUser1.setFriends(testUser1sFriends);
        testUser2.setFriends(testUser2sFriends);

        // Send request and receive response
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(testMessage)
            .when()
            .post("/users/1/create/message/2");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check if the message was created
        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.getString("message"));
        	
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
 
    @Test
    public void getAllMessageTest() {
        // Prepare test message/user data
        Message testMessage1 = new Message((long) 1, "Hello!");
        Message testMessage2 = new Message((long) 2, "Hey! How are you?");
        Message testMessage3 = new Message((long) 3, "I am good, wbu?");
        
        Set <Message> allMessages = new HashSet<Message>();
        allMessages.add(testMessage1);
        allMessages.add(testMessage2);
        allMessages.add(testMessage3);

        // Send request and receive response
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(allMessages)
            .when()
            .get("/messages");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check if the message/user was created
        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.getString("message"));
        	
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
	/*
	 * Movie Tests
	 */

	@Test
	public void getMovieInfoTest() {
		// Prepare test movie ID
		String movieId = "76600";

		// Send request and receive response
		Response response = RestAssured.given().contentType("application/json").when().get("/movies/" + movieId);

		// Check status code
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		// Check if the movie information was retrieved
		String returnString = response.getBody().asString();
		try {
			JSONObject returnObj = new JSONObject(returnString);
			assertEquals(movieId, returnObj.getString("id"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getMovieCastsTest() {
		// Prepare test movie ID
		String movieId = "76600";

		// Send request and receive response
		Response response = RestAssured.given().contentType("application/json").when()
				.get("/movies/" + movieId + "/casts");

		// Check status code
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		// Check if the movie casts were retrieved
		JsonPath jsonPath = response.getBody().jsonPath();
		JSONArray castsArray = new JSONArray(jsonPath.getList("$"));
		assertTrue(castsArray.length() > 0);
	}

	@Test
	public void getNowPlayingMoviesTest() {
		// Send request and receive response
		Response response = RestAssured.given().contentType("application/json").when().get("/movies/now_playing");

		// Check status code
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);

		// Check if the now playing movies were retrieved
		JsonPath jsonPath = response.getBody().jsonPath();
		JSONArray nowPlayingArray = new JSONArray(jsonPath.getList("$"));
		assertTrue(nowPlayingArray.length() > 0);
	}
	
	/*
	 * Permission Test
	 */
	
	@Test
    public void getPermissionTest() {
        // Prepare test permission data
        Permission testPermission1 = new Permission(1, "Guest");
        Permission testPermission2 = new Permission(2, "General");
        Permission testPermission3 = new Permission(3, "Developer");
        Permission testPermission4 = new Permission(4, "Editor");
        
        List<Permission> allPermissions = new ArrayList<Permission>();
        
        allPermissions.add(testPermission1);
        allPermissions.add(testPermission2);
        allPermissions.add(testPermission3);
        allPermissions.add(testPermission4);

        // Send request and receive response
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(allPermissions)
            .when()
            .get("/permission");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check if the permission was created
        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.getString("message"));
        	
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
	
	/*
	 * Post Test
	 */
	
	@Test
    public void getAllPostsTest() {
        // Prepare test user data
        User testUser1 = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
        User testUser2 = new User("Monica Geller", "User_Monica", "Monica123", "monicag@iastate.edu", null);
        
        Post post1 = new Post();
        post1.setContents("I thought this movie was awesome!");
        post1.setDate(new Date());
        post1.setId(1);
        post1.setMovieId(67);
        post1.setRating(4);
        post1.setTitle("Avatar");
        
        testUser1.addPost(post1);
        
        Post post2 = new Post();
        post2.setContents("I thought this movie was alright.");
        post2.setDate(new Date());
        post2.setId(1);
        post2.setMovieId(2);
        post2.setRating(3);
        post2.setTitle("Ariel");
        
        testUser2.addPost(post2);
        
        List <Post> allPosts = new ArrayList<Post>();
        allPosts.add(post1);
        allPosts.add(post2);
        
        // Send request and receive response
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(allPosts)
            .when()
            .get("/get/posts/getAll");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.getString("message"));
        	
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
	
	@Test
    public void getPostByUserIdTest() {
        // Prepare test user data
        User testUser1 = new User("Rachel Green", "User_Rachel", "Rachel123", "rachelg@iastate.edu", null);
        
        Post post1 = new Post();
        post1.setContents("I thought this movie was awesome!");
        post1.setDate(new Date());
        post1.setId(1);
        post1.setMovieId(67);
        post1.setRating(4);
        post1.setTitle("Avatar");
        
        testUser1.addPost(post1);
        
        List <Post> allPosts = new ArrayList<Post>();
        allPosts.add(post1);
        
        // Send request and receive response
        Response response = RestAssured.given()
            .contentType("application/json")
            .body(allPosts)
            .when()
            .get("/get/posts/user/1");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.getString("message"));
        	
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
       
}
