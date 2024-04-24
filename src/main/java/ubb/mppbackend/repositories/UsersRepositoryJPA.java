package ubb.mppbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ubb.mppbackend.models.user.User;

public interface UsersRepositoryJPA extends JpaRepository<User, Long> {
    @Query("SELECT count(*) FROM users")
    int countUsers();
}
