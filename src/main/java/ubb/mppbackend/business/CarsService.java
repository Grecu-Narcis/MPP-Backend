package ubb.mppbackend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.models.car.Car;
import ubb.mppbackend.models.user.User;
import ubb.mppbackend.repositories.CarsRepositoryJPA;
import ubb.mppbackend.repositories.UsersRepositoryJPA;

import java.util.List;
import java.util.Optional;

@Service
public class CarsService {
    private final CarsRepositoryJPA carsRepository;

    @Autowired
    public CarsService(CarsRepositoryJPA carsRepository) {
        this.carsRepository = carsRepository;

        // this.addDemoCars();
    }

    public Car getCarById(Long id) throws RepositoryException {
        Optional<Car> car = carsRepository.findById(id);

        if (car.isEmpty()) {
            throw new RepositoryException("Car not found!");
        }

        return car.get();
    }

//    public Car addCar(Car car) {
//        return carsRepository.save(car);
//    }

    public void updateCar(Car car) {
        carsRepository.save(car);
    }

    public List<Car> getAllCarsByOwnerId(Long userId) {
        return carsRepository.findAllByOwnerId(userId);
    }

//    private void addDemoCars() {
//        User userNarcis = usersRepository.findById(1L).orElse(null);
//        User userBogdan = usersRepository.findById(2L).orElse(null);
//        Car car1 = new Car("Audi", "A4", 2018, 20000, "audi-a4.jpg", 10000, "Diesel", userBogdan);
//        Car car2 = new Car("BMW", "X6", 2019, 30000, "bmw-x6.jpg", 20000, "Gasoline", userBogdan);
//        Car car3 = new Car("Mercedes", "C180", 2017, 15000, "mercedes-c180.jpg", 30000, "Diesel", userNarcis);
//        Car car4 = new Car("Seat", "Ibiza", 2011, 5499, "seat-ibiza.jpg", 295345, "Diesel", userNarcis);
//        Car car5 = new Car("Ford", "Mustang", 2015, 10000, "mustang.jpg", 40000, "Gasoline", userBogdan);
//        Car car6 = new Car("Mazda", "Miata", 2022, 5235, "miata.jpg", 5234, "Gasoline", userNarcis);
//        Car car7 = new Car("Volkswagen", "Golf", 2022, 30000, "golf.jpg", 20000, "Gasoline", userBogdan);
//
//        carsRepository.save(car1);
//        carsRepository.save(car2);
//        carsRepository.save(car3);
//        carsRepository.save(car4);
//        carsRepository.save(car5);
//        carsRepository.save(car6);
//        carsRepository.save(car7);
//    }
}
