package ubb.mppbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ubb.mppbackend.models.user.User;

import java.util.Optional;

/**
 * Repository interface for accessing User entities using JPA.
 */
public interface UsersRepositoryJPA extends JpaRepository<User, Long> {
    /**
     * Counts the total number of users in the repository.
     *
     * @return  The total count of users stored in the repository.
     */
    @Query("SELECT count(*) FROM users")
    int countUsers();

    /**
     * Retrieves a user by their email address from the repository, if present.
     *
     * @param email The email address of the user to search for.
     * @return An Optional containing the user with the specified email, if found; otherwise, an empty Optional.
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user with the specified email address exists in the repository.
     *
     * @param email The email address to check for existence.
     * @return true if a user with the specified email exists in the repository; false otherwise.
     */
    Boolean existsByEmail(String email);
}
