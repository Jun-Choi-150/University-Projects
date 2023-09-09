package app.Movies;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */ 

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findById(int id);
}
