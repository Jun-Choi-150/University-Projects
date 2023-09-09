package app.Movies;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    @Value("${themoviedb.api.key}")
    private String apiKey;

    private RestTemplate restTemplate = new RestTemplate();
    
    private String getMovieApiUrl(String movieId) {
        return "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey;
    }
    
    public String getCastApiUrl(String movieId) {
        return "https://api.themoviedb.org/3/movie/" + movieId + "/casts?api_key=" + apiKey;
    }
    
    private String getNowPlayingMoviesUrl() {
        return "https://api.themoviedb.org/3/movie/now_playing?api_key=" + apiKey + "&language=en-US&page=1";
    }
    
    private String searchMoviesByTitle(String movieTitle) {
        return "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&language=en-US&query=" + movieTitle + "&page=1&include_adult=false" ;    	
    }

    
    
    public MovieDTO getMovieInfo(String movieId) {
        String movieApiUrl = getMovieApiUrl(movieId);
        return restTemplate.getForObject(movieApiUrl, MovieDTO.class, movieId);
    }

    public CastListDTO getCastList(String castApiUrl, String movieId) {
        return restTemplate.getForObject(castApiUrl, CastListDTO.class, movieId);
    }
    
    public List<resultMovieDTO> getNowPlayingMovies() {
    	ResultMovieListDTO nowPlayingMovies = restTemplate.getForObject(getNowPlayingMoviesUrl(), ResultMovieListDTO.class);
        return nowPlayingMovies.results;
    }
    
    public List<resultMovieDTO> getMoviesListByTitle(String movieTitle) {
        ResultMovieListDTO nowPlayingMovies = restTemplate.getForObject(searchMoviesByTitle(movieTitle), ResultMovieListDTO.class);
        return nowPlayingMovies.results;
    }

}

