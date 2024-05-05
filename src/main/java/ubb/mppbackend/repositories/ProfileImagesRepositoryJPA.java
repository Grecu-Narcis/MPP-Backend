package ubb.mppbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.mppbackend.models.user.ProfileImage;

import java.util.Optional;

public interface ProfileImagesRepositoryJPA extends JpaRepository<ProfileImage, Long> {
    Optional<ProfileImage> findByUserId(Long userId);
}
