package com.example.securityjwt;

import com.example.securityjwt.dto.Role;
import com.example.securityjwt.dto.User;
import com.example.securityjwt.service.UserServiceImpl;
import java.util.HashSet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApplication.class, args);
    }

    //@Bean
    CommandLineRunner run(UserServiceImpl userService){
        return args -> {
            userService.saveRole(new Role("ROLE_USER"));
            userService.saveRole(new Role("ROLE_MANAGER"));
            userService.saveRole(new Role("ROLE_ADMIN"));
            userService.saveRole(new Role("ROLE_SUPER_ADMIN"));

            userService.saveUser(new User("John Travolta", "john", "1234", new HashSet<>()));
            userService.saveUser(new User("Will Smith", "will", "1234", new HashSet<>()));
            userService.saveUser(new User("Jim Carry", "jim", "1234", new HashSet<>()));
            userService.saveUser(new User("Arnold Schwarzenegger", "arnold", "1234", new HashSet<>()));

            userService.addRoleToUser("john","ROLE_USER");
            userService.addRoleToUser("john","ROLE_MANAGER");
            userService.addRoleToUser("will","ROLE_MANAGER");
            userService.addRoleToUser("jim","ROLE_ADMIN");
            userService.addRoleToUser("arnold","ROLE_USER");
            userService.addRoleToUser("arnold","ROLE_ADMIN");
            userService.addRoleToUser("arnold","ROLE_SUPER_ADMIN");

        };
    }

}
