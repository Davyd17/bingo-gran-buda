package com.bingogranbuda.bingo.controller;

import com.bingogranbuda.bingo.model.User;
import com.bingogranbuda.bingo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Integer id){
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        User createdUser = userService.create(user);
        URI location = URI.create("/users/" + createdUser.id());

        return ResponseEntity.created(location).body(createdUser);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Integer id){
        userService.delete(id);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User newUser){

        return userService.update(id, newUser);
    }

}
