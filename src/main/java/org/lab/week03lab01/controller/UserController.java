package org.lab.week03lab01.controller;

import jakarta.validation.Valid;
import org.lab.week03lab01.model.CreateUserDTO;
import org.lab.week03lab01.model.UserNoPasswordDTO;
import org.lab.week03lab01.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserNoPasswordDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserNoPasswordDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserNoPasswordDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        var createdUser = userService.createUser(createUserDTO);
        URI location = URI.create("users/" + createdUser.getId());
        return ResponseEntity.created(location).body(createdUser);
    }
}
