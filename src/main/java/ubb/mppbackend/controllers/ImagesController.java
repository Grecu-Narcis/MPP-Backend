package ubb.mppbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ubb.mppbackend.business.ImagesService;
import ubb.mppbackend.business.UsersService;

import java.io.FileNotFoundException;
import java.util.Base64;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "localhost:3000")
public class ImagesController {
    private UsersService usersService;
    private ImagesService profileImagesService;

    private final String uploadDirectory = "src/main/resources/profile-images";

    @Autowired
    public ImagesController(UsersService usersService, ImagesService profileImagesService) throws FileNotFoundException {
        this.usersService = usersService;
        this.profileImagesService = profileImagesService;
    }

    @GetMapping("/getImage/{userId}")
    public ResponseEntity<String> getImageByUserId(@PathVariable Long userId) {
        try {
            byte[] requiredImage = this.profileImagesService.getImage(uploadDirectory, userId);

            String encodedImage = Base64.getEncoder().encodeToString(requiredImage);
            return ResponseEntity.ok().body(encodedImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/saveImage")
    public void saveImage(@RequestParam("image") MultipartFile imageToSave, @RequestParam("userId") Long userId) {
        try {
            this.profileImagesService.saveImageToStorage(uploadDirectory, userId, imageToSave);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
