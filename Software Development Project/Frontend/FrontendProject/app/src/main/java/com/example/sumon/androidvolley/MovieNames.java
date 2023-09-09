package com.example.sumon.androidvolley;

/**
 * @author sabrinaf
 * MovieNames class contains the constructor for the movieName object
 */
public class MovieNames {
    private String movieName;
    private int movieId;

    /**
     * constructor for movieNames
     * @param movieName string value of the movie name
     */
    public MovieNames(String movieName, int movieId) {
        this.movieName = movieName; this.movieId = movieId;
    }

    /**
     * returns the string value of the given movieName object
     * @return String value of the movieName object
     */
    public String getMovieName() {
        return this.movieName;
    }

    public int getMovieId() {return this.movieId; }
}
