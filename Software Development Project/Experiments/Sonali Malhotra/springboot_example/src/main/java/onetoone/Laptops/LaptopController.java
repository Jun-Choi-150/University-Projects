package onetoone.Laptops;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import onetoone.Users.User;
import onetoone.Users.UserRepository;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@RestController
public class LaptopController {

    @Autowired
    LaptopRepository laptopRepository;

    @Autowired
    UserRepository userRepository;
    
    // displays message depending on if input worked or not
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    // using: GET --> displays the list of all laptops
    @GetMapping(path = "/laptops")
    List<Laptop> getAllLaptops(){
        return laptopRepository.findAll();
    }

    // using: GET --> displays specific laptop with the given laptop id (ex: id 1)
    @GetMapping(path = "/laptops/{id}")
    Laptop getLaptopById(@PathVariable int id){
        return laptopRepository.findById(id);
    }

    // using: CREATE --> creates a new laptop
    // ex input:
    // {
    	// "cpuClock" : "2.3",
    	// "cpuCores"  : "5",
    	// "ram"   : "5",
    	// "manufacturer" : "Windows",
    	// "cost" : "1000"
    // }
    @PostMapping(path = "/laptops")
    String createLaptop(@RequestBody Laptop Laptop){
        if (Laptop == null)
            return failure;
        laptopRepository.save(Laptop);
        return success;
    }

    // using: PUT --> updates laptop information
    @PutMapping(path = "/laptops/{id}")
    Laptop updateLaptop(@PathVariable int id, @RequestBody Laptop request){
        Laptop laptop = laptopRepository.findById(id);
        if(laptop == null)
            return null;
        laptopRepository.save(request);
        return laptopRepository.findById(id);
    }

    // using: DELETE --> deletes the laptop with the specific laptop id
    @DeleteMapping(path = "/laptops/{id}")
    String deleteLaptop(@PathVariable int id){

        // Check if there is an object depending on user and then remove the dependency
        User user = userRepository.findByLaptop_Id(id);
        user.setLaptop(null);
        userRepository.save(user);

        // delete the laptop if the changes have not been reflected by the above statement
        laptopRepository.deleteById(id);
        return success;
    }
}
