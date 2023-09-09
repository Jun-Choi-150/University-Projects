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

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

@RestController
public class MovieController {

    HashMap<String, Movies> moviesList = new  HashMap<>();

    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // Gets all the users in the list and returns it in JSON format.
    // Note: To LIST, we use the GET method
    @GetMapping("/movies")
    public @ResponseBody HashMap<String,Movies> getAllMovies() {
        return moviesList;
    }

    // THIS IS THE CREATE OPERATION
    // Creates a new user with a movie title, movie genre, movie year, and movie rating.
    // In the format:
    // {
    // 		"userName" : "userName",
    // 		"movieTitle"  : "movieTitle",
    //		"movieGenre"   : "movieGenre",
    //		"movieYear" : "movieYear",
    //		"movieRating" : "movieRating"
    // }
    // Note: To CREATE we use POST method
    @PostMapping("/movies")
    public @ResponseBody String createMovie(@RequestBody Movies movies) {
        System.out.println(movies);
        moviesList.put(movies.getUserName(), movies);
        return "New user "+ movies.getUserName() + " Saved";
    }

    // THIS IS THE READ OPERATION
    // Note: To READ we use GET method
    @GetMapping("/movies/{userName}")
    public @ResponseBody Movies getUserName(@PathVariable String userName) {
        Movies m = moviesList.get(userName);
        return m;
    }

    // THIS IS THE UPDATE OPERATION
    // Updates anything in the user's list.
    // Note: To UPDATE we use PUT method
    @PutMapping("/movies/{userName}")
    public @ResponseBody Movies updateList(@PathVariable String userName, @RequestBody Movies m) {
        moviesList.replace(userName, m);
        return moviesList.get(userName);
    }

    // THIS IS THE DELETE OPERATION
    // Deletes the user and their list.
    // Note: To DELETE we use delete method
    @DeleteMapping("/movies/{userName}")
    public @ResponseBody HashMap<String, Movies> deletePerson(@PathVariable String userName) {
        moviesList.remove(userName);
        return moviesList;
    }
}