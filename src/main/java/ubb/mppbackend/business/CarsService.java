package ubb.mppbackend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.models.car.Car;
import ubb.mppbackend.models.car.CarMockGenerator;
import ubb.mppbackend.repositories.CarsRepositoryJPA;
import ubb.mppbackend.repositories.UsersRepositoryJPA;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for cars entities
 */
@Service
public class CarsService {
    private final CarsRepositoryJPA carsRepository;

    @Autowired
    public CarsService(CarsRepositoryJPA carsRepository) {
        this.carsRepository = carsRepository;
    }

    /**
     *
     * @param id Long representing id of the car to be returned
     * @return the car with the given id
     * @throws RepositoryException if no car matches the id
     */
    public Car getCarById(Long id) throws RepositoryException {
        Optional<Car> car = carsRepository.findById(id);

        if (car.isEmpty())
            throw new RepositoryException("Car not found!");

        return car.get();
    }

    /**
     * Updates the information of the provided car. The updated car object is saved using the carsRepository.
     *
     * @param car The car object to be updated. Must not be null.
     */
    public void updateCar(Car car) {
        carsRepository.save(car);
    }

    /**
     * Retrieves all cars owned by the specified user ID.
     *
     * @param userId The unique identifier of the owner whose cars are to be retrieved.
     * @return A list of cars owned by the specified user ID.
     */
    public List<Car> getAllCarsByOwnerId(Long userId) {
        return carsRepository.findAllByOwnerId(userId);
    }

    /**
     * Counts the number of cars owned by the specified owner ID.
     *
     * @param ownerId The unique identifier of the owner whose cars are to be counted.
     * @return The count of cars owned by the specified owner ID.
     */
    public int countCarsByOwnerId(Long ownerId) {
        return carsRepository.countCarsByOwnerId(ownerId);
    }

    /**
     * Retrieves a page of cars owned by the specified owner ID.
     *
     * @param ownerId The unique identifier of the owner whose cars are to be retrieved.
     * @param page The page number (0-based) of the results to retrieve.
     * @param size The number of cars per page.
     * @return A list of cars belonging to the specified owner ID for the requested page.
     */
    public List<Car> getPageOfCarsByOwnerId(Long ownerId, int page, int size) {
        Pageable requestedPage = PageRequest.of(page, size);

        return carsRepository.findAllByOwnerId(ownerId, requestedPage).getContent();
    }
}
