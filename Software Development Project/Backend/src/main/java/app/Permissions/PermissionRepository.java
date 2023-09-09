package app.Permissions;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */  

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Permission findByType(String type); 
    Permission findById(int id);   
}
