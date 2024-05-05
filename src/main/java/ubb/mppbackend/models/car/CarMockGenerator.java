package ubb.mppbackend.models.car;

import com.github.javafaker.Faker;
import ubb.mppbackend.models.user.User;
import ubb.mppbackend.repositories.CarsRepositoryJPA;

import java.util.*;

public class CarMockGenerator {
    private static final List<String> brands = Arrays.asList(
        "Toyota", "Honda", "Ford", "Chevrolet", "BMW"
    );
    private static final Map<String, List<String>> modelsByBrand = new HashMap<>();

    static {
        modelsByBrand.put("Toyota", Arrays.asList("Corolla", "Camry", "RAV4", "Highlander", "Sienna"));
        modelsByBrand.put("Honda", Arrays.asList("Civic", "Accord", "CR-V", "Pilot", "Odyssey"));
        modelsByBrand.put("Ford", Arrays.asList("F-150", "Escape", "Explorer", "Mustang", "Edge"));
        modelsByBrand.put("Chevrolet", Arrays.asList("Silverado", "Equinox", "Traverse", "Tahoe", "Suburban"));
        modelsByBrand.put("BMW", Arrays.asList("3 Series", "5 Series", "X3", "X5", "X7"));
    }

    public static Car generateFakeCar(List<User> users) {
        Faker faker = new Faker();
        Random random = new Random();

        String brand = brands.get(random.nextInt(brands.size()));
        String model = modelsByBrand.get(brand).get(random.nextInt(modelsByBrand.get(brand).size()));
        int year = faker.number().numberBetween(2000, 2022);
        int price = faker.number().numberBetween(5000, 50000);
        String image = "default.png";
        int mileage = faker.number().numberBetween(0, 500000);
        String fuelType = faker.options().option("Gasoline", "Diesel", "Electric");
        User owner = users.get(random.nextInt(users.size()));

        return new Car(brand, model, year, price, image, mileage, fuelType, owner);
    }

    public static void addFakeCars(int numberOfCars, List<User> users, CarsRepositoryJPA carsRepository) {
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < numberOfCars; i++) {
            Car fakeCar = generateFakeCar(users);
            cars.add(fakeCar);

            if ((i + 1) % 1000 == 0) {
                System.out.println("Saving batch of 1000 cars");
                carsRepository.saveAll(cars);
                cars.clear();
                System.out.println("Saved batch of 1000 cars");
            }
        }

        carsRepository.saveAll(cars);
    }
}
