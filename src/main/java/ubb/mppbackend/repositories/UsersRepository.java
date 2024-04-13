package ubb.mppbackend.repositories;


import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.stereotype.Repository;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.models.user.User;

import java.util.*;

@Repository
public class UsersRepository  {
    private final List<User> users;
    private boolean isAscending;

    public UsersRepository() {
        this.users = new ArrayList<>();
        this.isAscending = true;

        this.generateFakeData(6);

        this.users.add(new User("Grecu", "Narcis", "narcis.jpg", 20));
        this.users.add(new User("Bogdan", "Ciornohac", "bogdan.jpg", 20));
        this.users.add(new User("Medeea", "Condurache", "medeea.jpg", 21));
        this.users.add(new User("Iosif", "Pintilie", "iosif.jpg", 20));

        this.users.sort(Comparator.comparingInt(User::getAge));
    }

    public List<User> generateFakeData(int numberOfUsers) {
        List<User> fakeUsers = new ArrayList<>();

        for (int i = 0; i < numberOfUsers; i++) {
            User fakeUser = this.generateFakeUser();
            fakeUsers.add(fakeUser);
        }

        fakeUsers.forEach(this::addUser);

        return fakeUsers;
    }

    private User generateFakeUser() {
        Faker fakerUserGenerator = new Faker();

        Name fakeName = fakerUserGenerator.name();
        int age = fakerUserGenerator.number().numberBetween(18, 45);

        return new User(fakeName.lastName(), fakeName.firstName(), "dog.jpg", age);
    }

    private void sortUsers(boolean isAscending) {
        this.users.sort(Comparator.comparingInt(User::getAge));

        if (!isAscending)
            Collections.reverse(this.users);
    }

    public void addUser(User userToSave) {
        this.users.add(userToSave);
    }

    public User getUserById(UUID idToFind) throws RepositoryException {
        Optional<User> foundUser = this.users.
            stream().
            filter(currentUser -> currentUser.getId().equals(idToFind)).
            findFirst();

        if (foundUser.isEmpty()) throw new RepositoryException("User not found!");

        return foundUser.get();
    }

    public void updateUser(User userToUpdate) throws RepositoryException {
        User currentUser = this.getUserById(userToUpdate.getId());
        currentUser.update(userToUpdate);
    }

    public void deleteUser(UUID idToRemove) {
        this.users.removeIf(currentUser -> currentUser.getId().equals(idToRemove));
    }

    public List<User> getPage(int requiredPage, boolean isAscending, int pageSize) {
        if (this.isAscending != isAscending) {
            this.isAscending = isAscending;
            this.sortUsers(isAscending);
        }

        int start = Math.min(requiredPage * pageSize, this.users.size());
        int end = Math.min((requiredPage + 1) * pageSize, this.users.size());

        return this.users.subList(start, end);
    }

    public List<User> getAll() {
        return this.users;
    }
}
