package app.Users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */ 

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailId(String emailId);
}
