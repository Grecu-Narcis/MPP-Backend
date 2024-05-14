package ubb.mppbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.mppbackend.business.CarsService;
import ubb.mppbackend.models.car.Car;
import ubb.mppbackend.models.user.User;

import java.util.List;

/**
 * Controller class that handles REST API endpoints related to car operations.
 * Endpoints in this controller allow clients to interact with car data.
 */
@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*")
public class CarsController {
    private final CarsService carsService;

    /**
     * Constructs a new CarsController instance with the specified CarsService dependency.
     *
     * @param carsService The service layer responsible for handling car-related operations.
     */
    @Autowired
    public CarsController(CarsService carsService) {
        this.carsService = carsService;
    }

    /**
     * Retrieves all cars owned by a specific owner ID.
     *
     * @param ownerId The ID of the owner whose cars are to be retrieved.
     * @return ResponseEntity containing a list of cars owned by the specified owner.
     */
    @GetMapping("/getAllByOwnerId/{ownerId}")
    public ResponseEntity<List<Car>> getCarsByOwnerId(@PathVariable String ownerId) {
        List<Car> cars = this.carsService.getAllCarsByOwnerId(Long.parseLong(ownerId));
        System.out.println(cars);
        return ResponseEntity.ok().body(cars);
    }

    /**
     * Retrieves a page of cars owned by a specific owner ID.
     *
     * @param ownerId   The ID of the owner whose cars are to be retrieved.
     * @param page      The page number (0-based) of the results to retrieve.
     * @param pageSize  The number of cars per page.
     * @return ResponseEntity containing a list of cars for the specified page and owner.
     */
    @GetMapping("/getPageByOwnerId")
    public ResponseEntity<List<Car>> getPageByOwnerId(@RequestParam String ownerId,
                                                      @RequestParam String page,
                                                      @RequestParam String pageSize) {
        List<Car> cars = this.carsService.getPageOfCarsByOwnerId(Long.parseLong(ownerId), Integer.parseInt(page), Integer.parseInt(pageSize));
        return ResponseEntity.ok().body(cars);
    }

    /**
     * Retrieves a specific car by its ID.
     *
     * @param carId The ID of the car to retrieve.
     * @return ResponseEntity containing the requested car if found, or a 404 error if not found.
     */
    @GetMapping("/getCar/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable String carId) {
        try {
            Car requiredCar = this.carsService.getCarById(Long.parseLong(carId));
            return ResponseEntity.ok().body(requiredCar);
        }

        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves the count of cars owned by a specific owner.
     *
     * @param ownerId The ID of the owner whose cars are to be counted.
     * @return ResponseEntity containing the count of cars owned by the specified owner.
     */
    @GetMapping("/getCarsCount/{ownerId}")
    public ResponseEntity<Integer> getCarsCountByOwnerId(@PathVariable String ownerId) {
        int count = this.carsService.countCarsByOwnerId(Long.parseLong(ownerId));
        return ResponseEntity.ok().body(count);
    }

    /**
     * Updates the details of a specific car.
     *
     * @param carToAdd The updated car data to be saved.
     * @return ResponseEntity indicating the success or failure of the update operation.
     */
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
