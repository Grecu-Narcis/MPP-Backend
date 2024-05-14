package ubb.mppbackend.models.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ubb.mppbackend.models.car.Car;

import java.util.List;

/**
 * Represents a user entity with basic information.
 * This entity is mapped to the "users" table in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "picture_url", nullable = false, columnDefinition = "TEXT")
    private String pictureUrl;

    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Car> cars;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private ProfileImage profileImage;

    /**
     * Constructs a new User with the specified details.
     *
     * @param firstName  The first name of the user.
     * @param lastName   The last name of the user.
     * @param pictureUrl The URL of the user's profile picture.
     * @param age        The age of the user.
     */
    public User(String firstName, String lastName, String pictureUrl, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pictureUrl = pictureUrl;
        this.age = age;
    }

    /**
     * Updates the user's information based on the provided newUser object.
     *
     * @param newUser The new User object containing updated information.
     */
    public void update(User newUser) {
        this.setFirstName(newUser.getFirstName());
        this.setLastName(newUser.getLastName());
        this.setPictureUrl(newUser.getPictureUrl());
        this.setAge(newUser.getAge());
    }

    /**
     * Compares this User object with another object for equality based on ID.
     *
     * @param obj The object to compare with.
     * @return true if the objects are equal (i.e., have the same ID), otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User userToCompare))
            return false;

        return this.getId().equals(userToCompare.getId());
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            ", age=" + age +
            '}';
    }
}
