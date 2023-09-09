package app.Permissions;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.Posts.Post;
import app.Users.User;
import app.Users.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */ 

@Api(value = "Permission Controller", tags="5. Permission")
@RestController
@RequestMapping(value="/permission")
public class PermissionController {

    @Autowired
    UserRepository userRepository;
	
    @Autowired
    PermissionRepository permissionRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /*
     * Type of Default Permission: General (1), Editor(2), Developer(3) 
     */
    @ApiOperation(value = "Determine what types of permissions exists", response = Permission.class, responseContainer = "List")
    @GetMapping
    List<Permission> getAllPermissions(){
        return permissionRepository.findAll();
    }


    @ApiOperation(value = "Create a new permission type", response = String.class)
    @PostMapping(path = "/create")
    String createPermission(@RequestBody Permission permission){
        if (permission.getType() == null || permission.getType().isEmpty()) {
            return null;
        }
        if (permission.getId() == 0) {
            return null;
        }
        if (permission.getId() == 1 || permission.getId() == 2 || permission.getId() == 3 || permission.getId() == 4) {
            return "Failure: Duplicated permission type";
        }
        
        permissionRepository.save(permission);
        return success;
    }


    @ApiOperation(value = "Update existing types except for the default permission type", response = String.class)
    @PutMapping("/update/{permissionID}")
    String updatePermission(@PathVariable int permissionID, @RequestBody Permission request){
        Permission permission = permissionRepository.findById(permissionID);
        if(permission == null)
            return failure;
        if (permission.getId() == 1 || permission.getId() == 2 || permission.getId() == 3 || permission.getId() == 4) {
            return "Failure: No permission to modify the default type";
        }
        permission.setType(request.getType());
        permissionRepository.save(permission);
        return success;
    }


    @ApiOperation(value = "Delete existing types except for the default permission type", response = String.class)
    @DeleteMapping("/delete/{permissionID}")
    String deletePermission(@PathVariable int permissionID){
        if (permissionID == 1 || permissionID == 2 || permissionID == 3 || permissionID == 4) {
            return "Failure: No permission to delete the default type";
        }
        permissionRepository.deleteById(permissionID);
        return success;
    }
}
