package coms309.people;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

/**
*
* @author Wonjun Choi
*/

@RestController
public class UserController {

    // Note that there is only ONE instance of PeopleController in Springboot system.
    HashMap<String, User> userList = new  HashMap<>();

    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    
    // This controller takes no input. 
    // Springboot automatically converts the list to JSON format 
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/user")
    public @ResponseBody HashMap<String,User> getAllPersons() {
        return userList;
    }

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a person object and 
    // the method below enters it into the list.
    
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    
    /*
     *  -----------JSON format--------------
     * 
     *  "firstName" : "user's firstName",
     *  "lastName" : "user's lastName"
     *  "userID" : "user's userID"
     *  "password" : "user's password"
     * 
     */
    
    // Note: To CREATE we use POST method
    @PostMapping("/user")
    public @ResponseBody String createPerson(@RequestBody User user) {
        System.out.println(user);
        userList.put(user.getFirstName(), user);
        return "New person "+ user.getFirstName() + " Saved";
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/user/{firstName}")
    public @ResponseBody User getUser(@PathVariable String firstName) {
        User u = userList.get(firstName);
        return u;
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/user/{firstName}")
    public @ResponseBody User updateUser(@PathVariable String firstName, @RequestBody User u) {
        userList.replace(firstName, u);
        return userList.get(firstName);
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/user/{firstName}")
    public @ResponseBody HashMap<String, User> deleteUser(@PathVariable String firstName) {
        userList.remove(firstName);
        return userList;
    }
}

