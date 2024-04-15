package ubb.mppbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.mppbackend.models.car.Car;

import java.util.List;

public interface CarsRepositoryJPA extends JpaRepository<Car, Long> {
    public List<Car> findAllByOwnerId(Long ownerId);
}
