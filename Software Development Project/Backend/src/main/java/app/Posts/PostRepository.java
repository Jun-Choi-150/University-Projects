package app.Posts;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */  

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(int id);
    List<Post> findByUserId(Long userId);
    List<Post> findByMovieId(int movieId);
	void deleteById(int id);
}
