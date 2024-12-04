package ubb.mppbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.mppbackend.models.car.CarImage;

import java.util.Optional;

public interface CarImagesRepositoryJPA extends JpaRepository<CarImage, Long> {
    /**
     * Retrieves a CAR image associated with a specific CAR ID, if it exists.
     *
     * @param carId  The ID of the car whose image is to be retrieved.
     * @return        An Optional containing the car image associated with the specified car ID,
     *                or an empty Optional if no image is found for the car.
     */
    Optional<CarImage> findByCarId(Long carId);
}
