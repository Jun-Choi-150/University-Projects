package app.Posts;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import app.Movies.Movie;
import app.Users.User;


/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */  

@Entity
@Table(name="post")
public class Post {

    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(unique = true)
    @JsonProperty("title")
    private String title;
    @Lob
    private String contents;
    @Column(name="rating")
    private int rating;
    @Column(name="date")
    private Date date = new Date();

    /*
     * Relations
     */
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;
    
    @JsonProperty("movie_id")
    private Integer movieId;   
    
    // =============================== Constructors ================================== //

    public Post() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    
    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getContents(){
        return contents;
    }

    public void setContents(String contents){
        this.contents = contents;
    }
    
    public int getRating(){
        return rating;
    }

    public void setRating(int rating){
        this.rating = rating;
    }
    
    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }
    
    public Integer  getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer  movieId) {
        this.movieId = movieId;
    }
    
    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
 
    
}
