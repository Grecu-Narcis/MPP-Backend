package ubb.mppbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ubb.mppbackend.config.security.JWTUtils;
import ubb.mppbackend.dto.AuthResponseDTO;
import ubb.mppbackend.dto.UserLoginDTO;
import ubb.mppbackend.dto.UserRegisterDTO;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.models.role.Role;
import ubb.mppbackend.models.user.User;
import ubb.mppbackend.repositories.RoleRepositoryJPA;
import ubb.mppbackend.repositories.UsersRepositoryJPA;

import java.util.Optional;
import java.util.Set;

/**
 * Controller responsible for handling user authentication operations via API endpoints.
 * Endpoints are mapped under "/api/auth" with cross-origin support enabled for all origins.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UsersRepositoryJPA usersRepositoryJPA;
    private final RoleRepositoryJPA roleRepositoryJPA;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    /**
     * Constructs a new AuthenticationController with required dependencies.
     *
     * @param authenticationManager The authentication manager used to authenticate users.
     * @param usersRepositoryJPA    The repository for accessing user data.
     * @param roleRepositoryJPA     The repository for accessing role data.
     * @param passwordEncoder       The password encoder for securely encoding passwords.
     * @param jwtUtils              The utility class for handling JWT (JSON Web Token) operations.
     */
    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UsersRepositoryJPA usersRepositoryJPA,
                                    RoleRepositoryJPA roleRepositoryJPA, PasswordEncoder passwordEncoder, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.usersRepositoryJPA = usersRepositoryJPA;
        this.roleRepositoryJPA = roleRepositoryJPA;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Endpoint for user registration.
     *
     * @param registerRequest The user registration request containing user details.
     * @return ResponseEntity indicating the result of the registration process.
     * @throws RepositoryException If an error occurs during data repository operations.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO registerRequest) throws RepositoryException
    {
        if (this.usersRepositoryJPA.existsByEmail(registerRequest.getEmail()))
            return ResponseEntity.badRequest().body("There already exists an account with this email!");

        User userToRegister = new User(registerRequest.getFirstName(), registerRequest.getLastName(),
            registerRequest.getEmail(), this.passwordEncoder.encode(registerRequest.getPassword()));

        Optional<Role> userRole = this.roleRepositoryJPA.findByName("USER");
        if (userRole.isEmpty())
            throw new RepositoryException("Role not found!");

        userToRegister.setRoles(Set.of(userRole.get()));

        this.usersRepositoryJPA.save(userToRegister);

        return ResponseEntity.ok().body("User registered successfully!");
    }

    /**
     * Endpoint for user login.
     *
     * @param loginRequest The user login request containing login credentials.
     * @return ResponseEntity containing an authentication token upon successful login.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserLoginDTO loginRequest) {
        Authentication authentication = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);

        Optional<User> loggedUser = this.usersRepositoryJPA.findByEmail(loginRequest.getEmail());

        //noinspection OptionalIsPresent
        if (loggedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        
        return ResponseEntity.ok().body(new AuthResponseDTO(token, loggedUser.get().getId()));
    }
}
