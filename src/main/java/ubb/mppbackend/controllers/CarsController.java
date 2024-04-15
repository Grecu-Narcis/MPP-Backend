package ubb.mppbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.mppbackend.business.CarsService;
import ubb.mppbackend.models.car.Car;
import ubb.mppbackend.models.user.User;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "http://localhost:3000")
public class CarsController {
    private final CarsService carsService;

    @Autowired
    public CarsController(CarsService carsService) {
        this.carsService = carsService;
    }

    @GetMapping("/getAllByOwnerId/{ownerId}")
    public ResponseEntity<List<Car>> getCarsByOwnerId(@PathVariable String ownerId) {
        List<Car> cars = this.carsService.getAllCarsByOwnerId(Long.parseLong(ownerId));
        System.out.println(cars);
        return ResponseEntity.ok().body(cars);
    }

    @GetMapping("/getCar/{carId}")
    public ResponseEntity<Car> getCar(@PathVariable String carId) {
        try {
            Car requiredCar = this.carsService.getCarById(Long.parseLong(carId));
            return ResponseEntity.ok().body(requiredCar);
        }

        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateCar")
    public ResponseEntity<String> updateCar(@RequestBody Car carToAdd) {
        try {
            User owner = this.carsService.getCarById(carToAdd.getId()).getOwner();
            carToAdd.setOwner(owner);
            this.carsService.updateCar(carToAdd);
            return ResponseEntity.ok().body("Car added successfully!");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid car data!");
        }
    }
}