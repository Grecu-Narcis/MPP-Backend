package ubb.mppbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ubb.mppbackend.models.car.Car;

import java.util.List;

public interface CarsRepositoryJPA extends JpaRepository<Car, Long> {
    List<Car> findAllByOwnerId(Long ownerId);
    Page<Car> findAllByOwnerId(Long ownerId, Pageable pageable);

    @Query("SELECT count(*) FROM cars c where c.owner.id = ?1")
    int countCarsByOwnerId(Long ownerId);
}
