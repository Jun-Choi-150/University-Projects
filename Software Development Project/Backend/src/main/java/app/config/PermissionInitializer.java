package app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import app.Permissions.Permission;
import app.Permissions.PermissionRepository;

@Configuration
public class PermissionInitializer {

    @Bean
    public CommandLineRunner initPermission(PermissionRepository permissionRepository) {
        return args -> {
            if (permissionRepository.findByType("Guest") == null) {
                permissionRepository.save(new Permission(1, "Guest"));
            }
            if (permissionRepository.findByType("General") == null) {
                permissionRepository.save(new Permission(2, "General"));
            }
            if (permissionRepository.findByType("Developer") == null) {
                permissionRepository.save(new Permission(3, "Developer"));
            }
            if (permissionRepository.findByType("Editor") == null) {
                permissionRepository.save(new Permission(4, "Editor"));
            }
        };
    }
}
