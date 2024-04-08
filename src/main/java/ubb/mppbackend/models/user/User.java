package ubb.mppbackend.models.user;

import java.util.UUID;

public class User {

    private final UUID id;
    private String firstName;
    private String lastName;
    private String pictureUrl;
    private int age;

    public User(String firstName, String lastName, String pictureUrl, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pictureUrl = pictureUrl;
        this.age = age;

        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newLastName) {
        this.lastName = newLastName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String newUrl) {
        this.pictureUrl = newUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int newAge) {
        this.age = newAge;
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
