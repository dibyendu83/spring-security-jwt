package com.example.securityjwt.controller;

import com.example.securityjwt.dto.Role;
import com.example.securityjwt.dto.User;
import com.example.securityjwt.service.UserServiceImpl;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return new ResponseEntity(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return new ResponseEntity(userService.saveRole(role), HttpStatus.CREATED);
    }

    @PostMapping("/role/user")
    public ResponseEntity saveRoleToUser(@RequestBody UserRoleForm userRoleForm) {
        userService.addRoleToUser(userRoleForm.userName, userRoleForm.roleName);
        return ResponseEntity.ok().build();
    }

    @Data
    class UserRoleForm {
        private String userName;
        private String roleName;
    }
}
