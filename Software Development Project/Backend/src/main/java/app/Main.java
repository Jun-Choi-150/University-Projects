package app;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import app.Messages.Message;
import app.Messages.MessageRepository;
import app.Movies.Movie;
import app.Movies.MovieRepository;
import app.Permissions.Permission;
import app.Permissions.PermissionRepository;
import app.Posts.Post;
import app.Posts.PostRepository;
import app.Users.User;
import app.Users.UserRepository;

/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */ 


@SpringBootApplication
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    // comment out main if tables are created
    
    
    // Create 3 users with their movies, friends, and posts
    
    /*
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, MovieRepository movieRepository, PostRepository postRepository, PermissionRepository permissionRepository, MessageRepository messageRepository, DefaultMovieRepository defaultMovieRepository) {
        return args -> {
            User user1 = new User("Jun", "user_jun", "Jun123", "jun@somemail.com", new URL("https://thumbs.dreamstime.com/b/default-avatar-profile-trendy-style-social-media-user-icon-187599373.jpg"));
            User user2 = new User("Sabrina", "user_sabrina", "Sabrina123", "sabrina@somemail.com", new URL("https://thumbs.dreamstime.com/b/default-avatar-profile-trendy-style-social-media-user-icon-187599373.jpg"));
            User user3 = new User("Sonali", "user_sonali", "Sonali123", "sonali@somemail.com", new URL("https://thumbs.dreamstime.com/b/default-avatar-profile-trendy-style-social-media-user-icon-187599373.jpg"));
            
           
            ArrayList<User> user1_friends = new ArrayList<User>();
            user1_friends.add(user2);
            user1_friends.add(user3);
            
            ArrayList<User> user2_friends = new ArrayList<User>();
            user2_friends.add(user1);
            
            ArrayList<User> user3_friends = new ArrayList<User>();
            user3_friends.add(user1);
            
            
            permissionRepository.save(new Permission(1, "guest"));
            permissionRepository.save(new Permission(2, "average"));
            permissionRepository.save(new Permission(3, "verified"));
            permissionRepository.save(new Permission(4, "editor"));
            
            messageRepository.save(new Message(1, "Hello!", 1, 3));
            messageRepository.save(new Message(2, "Hey! What's up?", 3, 1));
            messageRepository.save(new Message(3, "Have you seen this new movie?", 1, 3));
                      
            defaultMovieRepository.save(new DefaultMovie("Avatar", 4.8, "Action/Sci-fi", "Kate Winslet", "James Cameron", 2009, "A movie with blue people!", new URL("https://m.media-amazon.com/images/M/MV5BZDA0OGQxNTItMDZkMC00N2UyLTg3MzMtYTJmNjg3Nzk5MzRiXkEyXkFqcGdeQXVyMjUzOTY1NTc@._V1_.jpg"), "PG-13"));
            defaultMovieRepository.save(new DefaultMovie("Avengers: Endgame", 4.3, "Action/Sci-fi", "Robert Downey Jr.", "Anthony Russo", 2019, "A movie where it is endgame for avengers!", new URL("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQA_-tL18_rj9zEcjN6n41NEaJm-kRNF9UeOtvksZ4z_OW6jRA9"), "PG-13"));
            defaultMovieRepository.save(new DefaultMovie("Titanic", 4.2, "Romance/Drama", "Leonardo DiCaprio", "James Cameron", 1997, "A movie where a ship sinks!", new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdPugWzSnX3TWCYO-KlF4f5VF2JeMp6VGdAi2lQTLzsqHq00dW"), "PG-13"));
            defaultMovieRepository.save(new DefaultMovie("Star Wars Ep. VII: The Force Awakens", 4.1, "Sci-fi/Adventure", "Harrison Ford", "J.J. Abrams", 2015, "A movie where a force awakens!", new URL("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSHJDX40pbMLcIpUBuZUFS01n7pfdKHDqXXBTan2ueBnUfKCTp1"), "PG-13"));
            defaultMovieRepository.save(new DefaultMovie("Spider-Man: No Way Home", 4, "Action/Adventure", "Tom Holland", "Jon Watts", 2021, "A movie where spiderman has no way home!", new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzGBIrBWsQrdZ1muYXhIUPQBupM7oOY8K3IFNic74pPNLar6Xm"), "PG-13"));
            defaultMovieRepository.save(new DefaultMovie("Jurassic World", 4.2, "Action/Sci-fi", "Chris Pratt", "Colin Trevorrow", 2015, "A movie with dinosaurs!", new URL("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTB-qqFJT7vZpgG4zcDvw9sCFRcvv0B62B4-P-pUoNjiTjEKM90"), "PG-13"));
            defaultMovieRepository.save(new DefaultMovie("The Lion King", 4.2, "Action/Adventure", "Seth Rogan", "Jon Favreau", 2019, "A movie where a lion is king!", new URL("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTAaxbfd8Mp6ggTcp4iTvQDBQw1JwkiJWaMVFqfczV9VPLd9Fvp"), "PG"));
            
            for(int i=6; i<13; i++)
            	postRepository.save(new Post(i, "The contents ...", 4, new Date()));
            	
            user1.setFriends(user1_friends);
            user2.setFriends(user2_friends);
            user3.setFriends(user3_friends);
            
            user1.addMovies(defaultMovieRepository.findById(1));
            user1.addMovies(defaultMovieRepository.findById(2));            
            user1.addMovies(defaultMovieRepository.findById(6));
            user2.addMovies(defaultMovieRepository.findById(3));
            user2.addMovies(defaultMovieRepository.findById(4)); 
            user3.addMovies(defaultMovieRepository.findById(5));
            user3.addMovies(defaultMovieRepository.findById(7));
            
            user1.addPosts(postRepository.findById(1));
            user1.addPosts(postRepository.findById(2));            
            user1.addPosts(postRepository.findById(6));
            user2.addPosts(postRepository.findById(3));
            user2.addPosts(postRepository.findById(4)); 
            user3.addPosts(postRepository.findById(5));
            user3.addPosts(postRepository.findById(7));
            
            postRepository.findById(1).setDefaultMovies(defaultMovieRepository.findById(1));
            postRepository.findById(2).setDefaultMovies(defaultMovieRepository.findById(2));
            postRepository.findById(3).setDefaultMovies(defaultMovieRepository.findById(3));
            postRepository.findById(4).setDefaultMovies(defaultMovieRepository.findById(4));
            postRepository.findById(5).setDefaultMovies(defaultMovieRepository.findById(5));
            postRepository.findById(6).setDefaultMovies(defaultMovieRepository.findById(6));
            postRepository.findById(7).setDefaultMovies(defaultMovieRepository.findById(7));
            
            user1.setPermission(permissionRepository.findById(3));
            user2.setPermission(permissionRepository.findById(2));
            user3.setPermission(permissionRepository.findById(3));
            
            user1.addMessages(messageRepository.findById(1));
            user3.addMessages(messageRepository.findById(2));
            user1.addMessages(messageRepository.findById(3));
            
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        };
    }
    */
    
    
    
    

}