package app.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
    UserRepository userRepository;

	public List<User> getAllUsers()
    {
        return this.userRepository.findAll();
    }
 
    public UserService(UserRepository userRepository)
    {
        this.userRepository =  userRepository;
    }
}