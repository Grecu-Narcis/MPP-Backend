package ubb.mppbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.mppbackend.business.UsersService;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.exceptions.UserValidatorException;
import ubb.mppbackend.models.user.User;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        System.out.println(userId);
        try {
            User requiredUser = this.usersService.getById(UUID.fromString(userId));
            return ResponseEntity.ok().body(requiredUser);
        }

        catch (RepositoryException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getPage")
    @ResponseBody
    public List<User> getPage(@RequestParam("page") int pageId, @RequestParam("isAscending") boolean isAscending,
                              @RequestParam("pageSize") int pageSize) {
        return this.usersService.getPage(pageId, isAscending, pageSize);
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<User> getAll() {
        return this.usersService.getAll();
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User userToAdd) {
        try {
            this.usersService.addUser(userToAdd);
            return ResponseEntity.ok().body("User added successfully!");
        }

        catch (UserValidatorException e) {
            return ResponseEntity.badRequest().body("Invalid user data!");
        }
    }

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

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") String userId) {
        this.usersService.deleteUser(UUID.fromString(userId));
    }

    @GetMapping("/countUsers")
    public int getUsersCount() {
        return this.usersService.getAll().size();
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok().body("Ping success!");
    }
}
