package ubb.mppbackend.models.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ubb.mppbackend.models.car.Car;

import java.util.List;

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

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Car> cars;

    public User(String firstName, String lastName, String pictureUrl, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pictureUrl = pictureUrl;
        this.age = age;
    }

    public void update(User newUser) {
        this.setFirstName(newUser.getFirstName());
        this.setLastName(newUser.getLastName());
        this.setPictureUrl(newUser.getPictureUrl());
        this.setAge(newUser.getAge());
    }

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
