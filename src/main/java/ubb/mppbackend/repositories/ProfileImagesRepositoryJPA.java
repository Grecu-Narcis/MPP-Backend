package ubb.mppbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.mppbackend.models.user.ProfileImage;

public interface ProfileImagesRepositoryJPA extends JpaRepository<ProfileImage, Long> {
    ProfileImage findByUserId(Long userId);
}
