package ubb.mppbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.mppbackend.business.CarsService;
import ubb.mppbackend.config.security.JWTUtils;
import ubb.mppbackend.models.car.Car;
import ubb.mppbackend.models.user.User;

import java.util.List;

/**
 * RestController class responsible for handling API endpoints related to cars.
 * Endpoints are mapped under "/api/cars" with cross-origin support enabled for all origins.
 */
@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*")
public class CarsController {
    private final CarsService carsService;
    private final JWTUtils jwtUtils;

    /**
     * Constructor for initializing the CarsController with necessary dependencies.
     *
     * @param carsService The service responsible for handling car-related operations.
     * @param jwtUtils    Utility class for processing JSON Web Tokens (JWTs).
     */
    @Autowired
    public CarsController(CarsService carsService, JWTUtils jwtUtils) {
        this.carsService = carsService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Retrieves all cars owned by a specified owner ID.
     *
     * @param ownerId     The ID of the owner whose cars are to be retrieved.
     * @param bearerToken The authorization token (Bearer token) provided in the request header.
     * @return ResponseEntity containing a list of cars belonging to the specified owner if authorized,
     *         otherwise returns HTTP status UNAUTHORIZED.
     */
    @GetMapping("/getAllByOwnerId/{ownerId}")
    public ResponseEntity<List<Car>> getCarsByOwnerId(@PathVariable String ownerId,
                                                      @RequestHeader("Authorization") String bearerToken) {
        String authorizedUserId = jwtUtils.getUserIdFromBearerToken(bearerToken);

        if (!authorizedUserId.equals(ownerId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<Car> cars = this.carsService.getAllCarsByOwnerId(Long.parseLong(ownerId));
        System.out.println(cars);
        return ResponseEntity.ok().body(cars);
    }

    /**
     * Retrieves a paginated list of cars owned by a specified owner ID.
     *
     * @param ownerId     The ID of the owner whose cars are to be retrieved.
     * @param page        The page number for pagination (starting from 0).
     * @param pageSize    The maximum number of cars per page.
     * @return ResponseEntity containing a paginated list of cars if authorized,
     *         otherwise returns HTTP status UNAUTHORIZED.
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
     * @param carId       The ID of the car to retrieve.
     * @return ResponseEntity containing the requested car if authorized,
     *         otherwise returns HTTP status UNAUTHORIZED or NOT_FOUND if the car does not exist.
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
     * Retrieves the count of cars owned by a specified owner ID.
     *
     * @param ownerId     The ID of the owner whose cars are to be counted.
     * @return ResponseEntity containing the count of cars if authorized,
     *         otherwise returns HTTP status UNAUTHORIZED.
     */
    @GetMapping("/getCarsCount/{ownerId}")
    public ResponseEntity<Integer> getCarsCountByOwnerId(@PathVariable String ownerId) {
        int count = this.carsService.countCarsByOwnerId(Long.parseLong(ownerId));
        return ResponseEntity.ok().body(count);
    }

    /**
     * Updates information of a specified car.
     *
     * @param carToAdd     The car object containing updated information.
     * @param bearerToken  The authorization token (Bearer token) provided in the request header.
     * @return ResponseEntity indicating the status of the update operation,
     *         with success message if authorized and completed,
     *         otherwise returns HTTP status UNAUTHORIZED or BAD_REQUEST.
     */
    @PutMapping("/updateCar")
    public ResponseEntity<String> updateCar(@RequestBody Car carToAdd, @RequestHeader("Authorization") String bearerToken) {
        String authorizedUserId = jwtUtils.getUserIdFromBearerToken(bearerToken);

        try {
            User owner = this.carsService.getCarById(carToAdd.getId()).getOwner();

            if (!authorizedUserId.equals(owner.getId().toString()))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            carToAdd.setOwner(owner);
            this.carsService.updateCar(carToAdd);
            return ResponseEntity.ok().body("Car added successfully!");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid car data!");
        }
    }
}
