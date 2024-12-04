package ubb.mppbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.mppbackend.business.S3Service;
import ubb.mppbackend.models.car.Car;
import ubb.mppbackend.repositories.CarsRepositoryJPA;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/car-images")
@CrossOrigin(origins = "*")
public class CarImagesController {
    private final S3Service s3Service;
    private final CarsRepositoryJPA carsRepositoryJPA;
    private final String CARS_BUCKET = "pc-cars-images";

    @Autowired
    public CarImagesController(S3Service s3Service, CarsRepositoryJPA carsRepositoryJPA) {
        this.s3Service = s3Service;
        this.carsRepositoryJPA = carsRepositoryJPA;
    }

    @GetMapping("get-image/{carId}")
    public ResponseEntity<String> getCarImageUrl(@PathVariable Long carId) {
        Optional<Car> requiredCarOptional = this.carsRepositoryJPA.findById(carId);

        if (requiredCarOptional.isEmpty() || requiredCarOptional.get().getPictureUrl().equals("default"))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("default");

        Car requiredCar = requiredCarOptional.get();

        String getUrl = this.s3Service.createPresignedGetUrl(CARS_BUCKET, requiredCar.getPictureUrl());

        return ResponseEntity.ok(getUrl);
    }

    @GetMapping("upload-url/{carId}")
    public ResponseEntity<String> getUploadUrlForImage(@PathVariable Long carId) {
        Optional<Car> requiredCarOptional = this.carsRepositoryJPA.findById(carId);

        if (requiredCarOptional.isEmpty())
            return ResponseEntity.notFound().build();

        Car requiredCar = requiredCarOptional.get();
        String pictureUrl = UUID.randomUUID() + ".jpg";
        requiredCar.setPictureUrl(pictureUrl);
        this.carsRepositoryJPA.save(requiredCar);

        String uploadUrl = this.s3Service.createPresignedUploadUrl(CARS_BUCKET, pictureUrl);

        return ResponseEntity.ok(uploadUrl);
    }
}
