package ubb.mppbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ubb.mppbackend.business.ImagesService;
import ubb.mppbackend.business.UsersService;

import java.io.FileNotFoundException;

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

    @PostMapping("/saveImage")
    public void saveImage(@RequestParam("image") MultipartFile imageToSave) {
        try {
            this.profileImagesService.saveImageToStorage(uploadDirectory, 1L, imageToSave);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
