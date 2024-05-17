package ubb.mppbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.mppbackend.business.ImagesService;
import ubb.mppbackend.business.UsersService;
import ubb.mppbackend.config.security.JWTUtils;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.exceptions.UserValidatorException;
import ubb.mppbackend.models.user.User;
import ubb.mppbackend.dto.UserDTO;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for managing user-related operations via RESTful endpoints.
 * Handles requests related to user retrieval, addition, modification, and deletion.
 * Provides endpoints for fetching users, adding users, updating users, deleting users,
 * and other utility operations like counting users and testing connectivity.
 */
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersService usersService;
    private final ImagesService imagesService;
    private final JWTUtils jwtUtils;

    /**
     * Constructor to initialize UsersController with required services.
     * @param usersService The service handling user-related operations.
     * @param imagesService The service handling image-related operations.
     */
    @Autowired
    public UsersController(UsersService usersService, ImagesService imagesService, JWTUtils jwtUtils) {
        this.usersService = usersService;
        this.imagesService = imagesService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Retrieve a user by ID.
     * @param userId The unique identifier of the user to retrieve.
     * @return ResponseEntity containing the requested UserDTO on success, or 404 if not found.
     */
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userId, @RequestHeader("Authorization") String bearerToken) {
        String authorizedUserId = jwtUtils.getUserIdFromBearerToken(bearerToken);

        if (!authorizedUserId.equals(userId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            User requiredUser = this.usersService.getById(Long.parseLong(userId));
            UserDTO requiredUserDTO = new UserDTO(requiredUser);

            return ResponseEntity.ok().body(requiredUserDTO);
        }

        catch (RepositoryException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieve a page of users.
     * @param pageId The page number to retrieve.
     * @param isAscending Flag indicating whether to sort in ascending order.
     * @param pageSize The number of users per page.
     * @return List of UserDTOs representing the requested user page.
     */
    @GetMapping("/getPage")
    @ResponseBody
    public List<UserDTO> getPage(@RequestParam("page") int pageId, @RequestParam("isAscending") boolean isAscending,
                              @RequestParam("pageSize") int pageSize) {
        return this.usersService.getPage(pageId, isAscending, pageSize)
            .stream()
            .map(UserDTO::new)
            .toList();
    }

    /**
     * Retrieve all users.
     * @return List of UserDTOs representing all users.
     */
    @GetMapping("/getAll")
    @ResponseBody
    public List<UserDTO> getAll() {
        return this.usersService.getAll()
            .stream()
            .map(UserDTO::new)
            .toList();
    }

    /**
     * Add a new user.
     * @param userToAdd The user object to add.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User userToAdd) {
        try {
            this.usersService.addUser(userToAdd);
            return ResponseEntity.ok().body(userToAdd.getId().toString());
        }

        catch (UserValidatorException e) {
            return ResponseEntity.badRequest().body("Invalid user data!");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving image!");
        }
    }

    /**
     * Add multiple users.
     * @param usersToAdd List of users to add.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @PostMapping("/addUsers")
    public ResponseEntity<String> addUsers(@RequestBody List<User> usersToAdd) {
        try {
            this.usersService.addUsers(usersToAdd);
            return ResponseEntity.ok().body("Users added successfully!");
        }

        catch (UserValidatorException e) {
            return ResponseEntity.badRequest().body("Invalid user data!");
        }
    }

    /**
     * Update an existing user.
     * @param userToUpdate The user object containing updated information.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody User userToUpdate) {
        try {
            this.usersService.updateUser(userToUpdate);
            return ResponseEntity.ok().body("User updated successfully!");
        }

        catch (RepositoryException e) {
            return ResponseEntity.badRequest().body("User not found!");
        }

        catch (UserValidatorException e) {
            return ResponseEntity.badRequest().body("Invalid user data!");
        }
    }

    /**
     * Delete a user by ID.
     * @param userId The unique identifier of the user to delete.
     */
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") String userId) {
        try {
            this.imagesService.removeUserProfileImage(Long.parseLong(userId));
            this.usersService.deleteUser(Long.parseLong(userId));
            System.out.println("--------------------------user removed-------------------------------------------");
        }

        catch (IOException e) {
            System.out.println("======================================");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get the count of all users.
     * @return The total number of users.
     */
    @GetMapping("/countUsers")
    public int getUsersCount() {
        return this.usersService.countUsers();
    }

    /**
     * Test endpoint for verifying server connectivity (ping).
     * @return ResponseEntity with success message if ping is successful.
     */
    @GetMapping("/ping")
    public ResponseEntity<String> testServerConnectivity() {
        System.out.println("----------------ping-------------");
        return ResponseEntity.ok().body("Ping success!");
    }
}
