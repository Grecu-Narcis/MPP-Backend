package ubb.mppbackend.models.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ubb.mppbackend.models.user.User;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "model", nullable = false, length = 50)
    private String model;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "picture_url", nullable = false, columnDefinition = "TEXT")
    private String pictureUrl;

    @Column(name = "mileage", nullable = false)
    private int mileage;

    @Column(name = "fuel_type", nullable = false, length = 50)
    private String fuelType;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    private User owner;

    public Car(String brand, String model, int year, double price, String pictureUrl, int mileage, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.mileage = mileage;
        this.fuelType = fuelType;
    }

    public Car(String brand, String model, int year, double price, String pictureUrl, int mileage, String fuelType, User owner) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.owner = owner;
    }
}
