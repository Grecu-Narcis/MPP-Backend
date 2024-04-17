package ubb.mppbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.mppbackend.models.user.User;

public interface UsersRepositoryJPA extends JpaRepository<User, Long> {
}
