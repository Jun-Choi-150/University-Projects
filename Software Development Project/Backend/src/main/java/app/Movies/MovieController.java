package app.Movies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import app.Permissions.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
 
/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */ 


@Api(value = "Movie Controller", tags="2. Movie")
@RestController
@RequestMapping(value="/movies")
public class MovieController {
	    
    @Autowired
    private MovieService movieService;
	

    @ApiOperation(value = "Get the movie's information by movie Id", response = MovieDTO.class)
    @GetMapping("/{movie_id}")
    public MovieDTO getMovieInfo(@PathVariable("movie_id") String movieId) {
        return movieService.getMovieInfo(movieId);
    }

    @ApiOperation(value = "Get a list of names of actors in a movie by movie Id", response = String.class, responseContainer = "List")
    @GetMapping("/{movie_id}/casts")
    public List<String> getMovieCasts(@PathVariable("movie_id") String movieId) {
        String castApiUrl = movieService.getCastApiUrl(movieId);
        CastListDTO castListDTO = movieService.getCastList(castApiUrl, movieId);

        // Filter the cast members who are known for "Acting" and extract their names
        List<String> actingCastNames = castListDTO.getCast().stream()
            .filter(cast -> "Acting".equalsIgnoreCase(cast.getKnownForDepartment()))
            .map(CastDTO::getName) // Extract the names of the filtered cast members
            .collect(Collectors.toList());

        return actingCastNames;
    }


    @ApiOperation(value = "Search by movie title to get a list of results", response = resultMovieDTO.class)
    @GetMapping("/search")
    public List<resultMovieDTO> searchMoviesByTitle(@RequestParam("movieTitle") String movieTitle){
        return movieService.getMoviesListByTitle(movieTitle);
    }
    
    
    @ApiOperation(value = "Get a list of movies currently playing", response = resultMovieDTO.class)
    @GetMapping("/now_playing")
    public List<resultMovieDTO> getNowPlayingMovies() {
        return movieService.getNowPlayingMovies();
    }
    
    
    /*
     * Class for listing genre-related information in movie information
     */
    public static class GenreNameListDeserializer extends JsonDeserializer<List<String>> {

        @Override
        public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            List<String> genreNames = new ArrayList<>();
            for (JsonNode genreNode : node) {
                genreNames.add(genreNode.get("name").asText());
            }
            return genreNames;
        }
    }
}