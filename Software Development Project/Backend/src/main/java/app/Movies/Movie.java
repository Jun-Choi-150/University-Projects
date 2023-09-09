package app.Movies;

import java.net.URL;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import app.Posts.Post;

/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */  

@Entity
@Table(name="movie")
public class Movie {

	/*
	 * Fields
	 */
	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    private int id;

    
     // =============================== Constructors ================================== //
    
    public Movie(){

    }
    
    public Movie(int id) {
    	this.id = id;
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }
    
    public int setId(int id){
        return id;
    }
    
}
