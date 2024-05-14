package ubb.mppbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ubb.mppbackend.business.ImagesService;
import java.util.Base64;

/**
 * Controller class that handles REST API endpoints related to image operations.
 * Endpoints in this controller allow clients to interact with user profile images.
 */
@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "localhost:3000")
public class ImagesController {
    private final ImagesService profileImagesService;

    /**
     * Constructs a new ImagesController instance with the specified ImagesService dependency.
     *
     * @param profileImagesService The service layer responsible for handling image-related operations.
     */
    @Autowired
    public ImagesController(ImagesService profileImagesService) {
        this.profileImagesService = profileImagesService;
    }

    /**
     * Retrieves a user's profile image as a Base64-encoded string by their user ID.
     *
     * @param userId The ID of the user whose profile image is to be retrieved.
     * @return ResponseEntity containing the Base64-encoded representation of the user's profile image.
     *         If the image retrieval fails, returns a bad request response with an error message.
     */
    @GetMapping("/getImage/{userId}")
    public ResponseEntity<String> getImageByUserId(@PathVariable Long userId) {
        try {
            byte[] requiredImage = this.profileImagesService.getImage(userId);

            String encodedImage = Base64.getEncoder().encodeToString(requiredImage);
            return ResponseEntity.ok().body(encodedImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Saves a user's profile image to storage and associates it with the specified user ID.
     *
     * @param imageToSave The image file to save.
     * @param userId      The ID of the user to associate the image with.
     */
    @PostMapping("/saveImage")
    public void saveImage(@RequestParam("image") MultipartFile imageToSave, @RequestParam("userId") Long userId) {
        try {
            this.profileImagesService.saveImageToStorage(userId, imageToSave);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
