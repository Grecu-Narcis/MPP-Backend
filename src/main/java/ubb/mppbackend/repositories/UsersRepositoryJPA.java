package ubb.mppbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ubb.mppbackend.models.user.User;

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
}
