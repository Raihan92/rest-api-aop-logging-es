package com.progcreek.app.api;

import com.progcreek.app.exception.UserNotFoundException;
import com.progcreek.app.service.UserService;
import com.progcreek.app.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Operations related to users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their unique ID")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        logger.info("Fetching user with ID: {}", id);
        User user = userService.getUserById(id);
        if (user == null) {
            logger.error("User not found: {}", id);
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Create user", description = "Create an user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        logger.info("Creating user: {}", user);
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an user")
    public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody User user) {
        logger.info("Updating user with ID: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, user));
    }
}