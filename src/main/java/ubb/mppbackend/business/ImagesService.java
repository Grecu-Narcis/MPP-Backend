package ubb.mppbackend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ubb.mppbackend.models.user.ProfileImage;
import ubb.mppbackend.models.user.User;
import ubb.mppbackend.repositories.ProfileImagesRepositoryJPA;
import ubb.mppbackend.repositories.UsersRepositoryJPA;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImagesService {
    ProfileImagesRepositoryJPA profileImagesRepository;
    UsersRepositoryJPA usersRepository;

    @Autowired
    public ImagesService(ProfileImagesRepositoryJPA profileImagesRepository, UsersRepositoryJPA usersRepository) {
        this.profileImagesRepository = profileImagesRepository;
        this.usersRepository = usersRepository;
    }

    public String getImageUrl(Long userId) {
        return profileImagesRepository.findByUserId(userId).getImageUrl();
    }

    public void saveImageToStorage(String uploadDirectory, Long userId, MultipartFile imageToSave) throws Exception {
        Optional<User> requiredUser = usersRepository.findById(userId);
        if (requiredUser.isEmpty()) {
            throw new Exception("User not found");
        }

        String imageUrl = UUID.randomUUID() + imageToSave.getOriginalFilename();
        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(imageUrl);

        Files.copy(imageToSave.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ProfileImage profileImage = new ProfileImage();
        profileImage.setImageUrl(imageUrl);
        profileImage.setUser(requiredUser.get());

        profileImagesRepository.save(profileImage);
    }
}
