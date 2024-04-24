package ubb.mppbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.mppbackend.business.UsersService;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.exceptions.UserValidatorException;
import ubb.mppbackend.models.user.User;
import ubb.mppbackend.models.user.UserDTO;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userId) {
        System.out.println(userId);
        try {
            User requiredUser = this.usersService.getById(Long.parseLong(userId));
            UserDTO requiredUserDTO = new UserDTO(requiredUser);

            return ResponseEntity.ok().body(requiredUserDTO);
        }

        catch (RepositoryException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getPage")
    @ResponseBody
    public List<UserDTO> getPage(@RequestParam("page") int pageId, @RequestParam("isAscending") boolean isAscending,
                              @RequestParam("pageSize") int pageSize) {
        return this.usersService.getPage(pageId, isAscending, pageSize)
            .stream()
            .map(UserDTO::new)
            .toList();
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<UserDTO> getAll() {
        return this.usersService.getAll()
            .stream()
            .map(UserDTO::new)
            .toList();
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
        this.usersService.deleteUser(Long.parseLong(userId));
    }

    @GetMapping("/countUsers")
    public int getUsersCount() {
        return this.usersService.countUsers();
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        System.out.println("----------------ping-------------");
        return ResponseEntity.ok().body("Ping success!");
    }

}
