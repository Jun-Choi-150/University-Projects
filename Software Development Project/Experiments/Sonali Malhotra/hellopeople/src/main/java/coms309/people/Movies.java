package coms309.people;

public class Movies {

	// The user's name.
    private String userName;

    // Movie's title.
    private String movieTitle;
    
    // Genre of the movie.
    private String movieGenre;

    // Year the movie was made.
    private String movieYear;

    // Users movie rating.
    // Out of 5 stars.
    // If user wants to rate the movie 5 stars, they simply type "5".
    private String movieRating;

    public Movies(){
        
    }

    public Movies(String userName, String movieTitle, String movieGenre, String movieYear, String movieRating){
        this.userName = userName;
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
        this.movieYear = movieYear;
        this.movieRating = movieRating;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMovieTitle() {
        return this.movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieGenre() {
        return this.movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public String getMovieYear() {
        return this.movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }
    
    public String getMovieRating() {
        return this.movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    @Override
    public String toString() {
        return userName + " " 
               + movieTitle + " "
               + movieGenre + " "
               + movieYear + " "
               + movieRating;
    }
}