package ubb.mppbackend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.models.car.Car;
import ubb.mppbackend.models.car.CarMockGenerator;
import ubb.mppbackend.models.user.User;
import ubb.mppbackend.repositories.CarsRepositoryJPA;
import ubb.mppbackend.repositories.UsersRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarsService {
    private final CarsRepositoryJPA carsRepository;
    private final UsersRepositoryJPA usersRepository;

    @Autowired
    public CarsService(CarsRepositoryJPA carsRepository, UsersRepositoryJPA usersRepository) {
        this.carsRepository = carsRepository;
        this.usersRepository = usersRepository;
    }

    public Car getCarById(Long id) throws RepositoryException {
        Optional<Car> car = carsRepository.findById(id);

        if (car.isEmpty()) {
            throw new RepositoryException("Car not found!");
        }

        return car.get();
    }

    public void updateCar(Car car) {
        carsRepository.save(car);
    }

    public List<Car> getAllCarsByOwnerId(Long userId) {
        return carsRepository.findAllByOwnerId(userId);
    }

    public int countCarsByOwnerId(Long ownerId) {
        return carsRepository.countCarsByOwnerId(ownerId);
    }

    public List<Car> getPageOfCarsByOwnerId(Long ownerId, int page, int size) {
        Pageable requestedPage = PageRequest.of(page, size);

        return carsRepository.findAllByOwnerId(ownerId, requestedPage).getContent();
    }
}
