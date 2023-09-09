package app.Posts;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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

import app.Messages.Message;
import app.Movies.Movie;
import app.Movies.MovieDTO;
import app.Movies.MovieRepository;
import app.Movies.MovieService;
import app.Users.User;
import app.Users.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */ 

//Note: Format is located at the bottom

@RestController
@Api(value = "Posts Controller", tags="2. Post")
public class PostController {

    @Autowired
    UserRepository userRepository;
	
    @Autowired
    PostRepository postRepository;
    
    @Autowired
     MovieRepository movieRepository;
       
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    
    
    @ApiOperation(value = "Get data from all posts", response = Post.class, responseContainer = "List")
    @GetMapping(path = "/get/posts/getAll")
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }


    @ApiOperation(value = "Get a post by Post's ID", response = Post.class)
    @GetMapping(path = "/get/posts/byPostId/{postId}")
    Post getPostByPostId(@PathVariable int postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            return postOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }

   
    @ApiOperation(value = "Get posts by User ID", response = List.class)
    @GetMapping(path = "/get/posts/user/{userId}")
    public List<Post> getPostsByUserId(@PathVariable long userId) {
        return postRepository.findByUserId(userId);
    }

    
    @ApiOperation(value = "Get posts by Movie ID", response = List.class)
    @GetMapping(path = "/get/posts/movie/{movieId}")
    public List<Post> getPostsByMovieId(@PathVariable int movieId) {
        return postRepository.findByMovieId(movieId);
    }

    
    @ApiOperation(value = "Create a user's post about a movie", response = String.class)
    @PostMapping(path = "users/{userId}/create/post/movie/{movieId}")
    public String createPost(@PathVariable("userId") long userId, @PathVariable("movieId") int movieId, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return failure;
        }
        User user = userOptional.get();

        post.setUser(user);
        post.setMovieId(movieId);

        postRepository.save(post);
        return success;
    }
   


    @ApiOperation(value = "Update a user's post", response = Post.class)
    @PutMapping(path = "users/{userId}/update/post/{postId}")
    Post updatePost(@RequestBody Post request, @PathVariable int postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            post.setTitle(request.getTitle());
            post.setContents(request.getContents());
            post.setRating(request.getRating());

            postRepository.save(post);

            return post;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }


   
    @ApiOperation(value = "Delete user's post", response = String.class)
    @DeleteMapping(path = "users/delete/post/{postId}")
    String deletePost(@PathVariable int postId){
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            postRepository.delete(postOptional.get());
            return success;
        }
        return failure;
    	
    }
}
