package com.jeferson.springcloud.msvc.users.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.springcloud.msvc.users.entities.User;
import com.jeferson.springcloud.msvc.users.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.save(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> optUser = userService.findById(id);
        return optUser.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> optUser = userService.findByUsername(username);
        return optUser.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id){
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(userDb -> {
            userDb.setEmail(user.getEmail());
            userDb.setUsername(user.getUsername());
            userDb.setEnable(user.getEnable());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDb));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}