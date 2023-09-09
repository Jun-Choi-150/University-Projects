package app.Users;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.Permissions.Permission;
import app.Permissions.PermissionRepository;
import app.Posts.Post;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */ 


@Api(value = "User Controller", tags="1. User")
@RestController
@RequestMapping(value="/users")
public class UserController {

	
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PermissionRepository permissionRepository;


    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    
    @ApiOperation(value = "Create a new user", response = String.class)
    @PostMapping("/create")
    public String createUser(@RequestBody User user) {
        if (user == null)
            return failure;
        
        Permission permission = permissionRepository.findByType("General");
        user.setPermission(permission);          
        
        userRepository.save(user);
        return success;
    }
    

    @ApiOperation(value = "Update an existing user by ID", response = User.class)
    @PutMapping("update/{id}")
    User updateUser(@PathVariable Long id, @RequestBody User request){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            
            user.setName(request.getName());
            user.setEmailId(request.getEmailId());
            user.setPassword(request.getPassword());
            user.setUsername(request.getUsername());
            user.setProfilePic(request.getProfilePic());

            userRepository.save(user);
            
            return userRepository.findById(id).orElse(null);
            
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    } 
       
    

    @ApiOperation(value = "Delete a user by ID", response = String.class)
    @DeleteMapping(path = "delete/{id}")
    String deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        return success;
    }
    

    
    @ApiOperation(value = "Get all users' information", response = List.class)
    @GetMapping(path = "/all")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }


    
    @ApiOperation(value = "Get a user's information by ID", response = ResponseEntity.class)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
  

    @ApiOperation(value = "Get a user's profile picture by ID", response = URL.class)
    @GetMapping(path = "getPic/{id}")
    URL getUsersProfilePictureById(@PathVariable Long id){
        return userRepository.getOne(id).getProfilePic();
    }
    

    
    @ApiOperation(value = "Get a user's friends by ID", response = Set.class)
    @GetMapping(path = "/{id}/friends")
    Set<User> getUsersFriendsById(@PathVariable Long id){
        return userRepository.getOne(id).getFriends();
    }
    
    
    
    @ApiOperation(value = "Get a user's one single friend by friend's ID", response = User.class)
    @GetMapping(path = "/{userId}/get/{friendId}")
    public ResponseEntity<User> getUsersOneFriendById(@PathVariable Long userId, @PathVariable Long friendId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Set<User> friends = user.getFriends();

            for (User friend : friends) {
                if (friend.getId().equals(friendId)) {
                    return ResponseEntity.ok(friend);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }   
   

    @ApiOperation(value = "Add a friend to a user by ID", response = ResponseEntity.class)
    @PutMapping("/{userId}/addFriend/{friendId}")
    public ResponseEntity<String> addFriendToUser(@PathVariable Long userId, @PathVariable Long friendId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> friendOptional = userRepository.findById(friendId);

        if (userOptional.isPresent() && friendOptional.isPresent()) {
            User user = userOptional.get();
            User friend = friendOptional.get();

            user.getFriends().add(friend);
            friend.getFriends().add(user);

            userRepository.save(user);
            userRepository.save(friend);

            return ResponseEntity.ok(success);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failure);
        }
    }
    
    @ApiOperation(value = "Remove a friend from a user by ID", response = ResponseEntity.class)
    @DeleteMapping("/{userId}/removeFriend/{friendId}")
    public ResponseEntity<String> removeFriendFromUser(@PathVariable Long userId, @PathVariable Long friendId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> friendOptional = userRepository.findById(friendId);

        if (userOptional.isPresent() && friendOptional.isPresent()) {
            User user = userOptional.get();
            User friend = friendOptional.get();

            user.getFriends().remove(friend);
            friend.getFriends().remove(user);

            userRepository.save(user);
            userRepository.save(friend);

            return ResponseEntity.ok(success);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failure);
        }
    }


    @ApiOperation(value = "Get all posts for a user by ID", response = ResponseEntity.class)
    @GetMapping("/{id}/posts")
    public ResponseEntity<Set<Post>> getPostsByUserId(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isPresent()) {
            Set<Post> posts = userOptional.get().getPost();
            return ResponseEntity.ok(posts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
   
    @GetMapping("/search")
    @ApiOperation(value = "Search for a user by username", response = ResponseEntity.class)
    public ResponseEntity<User> getUserByQuery(
            @RequestParam(value = "username", required = false) String username) {

        Optional<User> userOptional = Optional.empty();

        if (username != null) {
            userOptional = userRepository.findByUsername(username);
        } 

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    @ApiOperation(value = "Changing and updating permission types for existing users", response = String.class)
    @PostMapping("/update/{userId}/permission/{permissionID}")
    public String updateUserPermission(@PathVariable Long userId, @PathVariable int permissionID) {
    	
        Optional<User> userOptional = userRepository.findById(userId);
        Permission permission = permissionRepository.findById(permissionID);
    	
        if (userOptional.isPresent()) {
        	
            User user = userOptional.get();
            user.setPermission(permission);          
            userRepository.save(user);
            
            return success;    
        } else {
            return failure;
        }
    }
}
