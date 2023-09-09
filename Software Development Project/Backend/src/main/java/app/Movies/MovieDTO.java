package app.Movies;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class MovieDTO {

    @JsonProperty("id")
    private int id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("genres")
    @JsonDeserialize(using = MovieController.GenreNameListDeserializer.class)
    private List<String> genres;
    
    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("overview")
    private String overview;
    
    // http://image.tmdb.org/t/p/w500/{poster_path}
    @JsonProperty("poster_path")
    private String poster;

}

 class ResultMovieListDTO {
	
    @JsonProperty("results") List<resultMovieDTO> results;  
}

 class resultMovieDTO {

	    @JsonProperty("id")
	    private int id;
	    
	    @JsonProperty("title")
	    private String title;
	    
	    @JsonProperty("genre_ids")
	    private List<Integer> genreIds;
	    
	    @JsonProperty("release_date")
	    private String releaseDate;

	    @JsonProperty("overview")
	    private String overview;
	    
	    // http://image.tmdb.org/t/p/w500/{poster_path}
	    @JsonProperty("poster_path")
	    private String poster;

}

class CastListDTO {
    
    @JsonProperty("cast")
    private List<CastDTO> cast;

    public List<CastDTO> getCast() {
        return cast;
    }

    public void setCast(List<CastDTO> cast) {
        this.cast = cast;
    }
}

class CastDTO {
	
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("known_for_department")
    private String knownForDepartment;

    @JsonProperty("name")
    private String name;

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}