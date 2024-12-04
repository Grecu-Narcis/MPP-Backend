package ubb.mppbackend.models.car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "car_images")
public class CarImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    /**
     * Constructs a new ProfileImage with the specified image URL and associated User.
     *
     * @param imageUrl The URL of the profile image.
     * @param car    The Car to whom this image belongs.
     */
    public CarImage(String imageUrl, Car car) {
        this.imageUrl = imageUrl;
        this.car = car;
    }
}