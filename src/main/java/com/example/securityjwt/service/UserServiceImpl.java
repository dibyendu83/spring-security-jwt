package com.example.securityjwt.service;

import com.example.securityjwt.dto.Role;
import com.example.securityjwt.dto.User;
import com.example.securityjwt.repository.RoleRepository;
import com.example.securityjwt.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);
        if (user == null) {
            log.error("user {} not found in DB", username);
            throw new UsernameNotFoundException("user name not found in DB");
        }
        Collection<SimpleGrantedAuthority> authorities = user.getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
        org.springframework.security.core.userdetails.User springUser =
            new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
        return springUser;
    }

    public User saveUser(User user) {
        log.info("saving new user {} to db ", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Role saveRole(Role role) {
        log.info("saving new role {} to db ", role);
        return roleRepository.save(role);
    }

    public void addRoleToUser(String userName, String roleName) {
        log.info("adding role {} to user {}  ", userName, roleName);
        User user = getUser(userName);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public User getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
