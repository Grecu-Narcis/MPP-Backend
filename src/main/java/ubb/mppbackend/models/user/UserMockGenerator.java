package ubb.mppbackend.models.user;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import ubb.mppbackend.repositories.UsersRepositoryJPA;

import java.util.ArrayList;
import java.util.List;

public class UserMockGenerator {
    public static void generateFakeData(int numberOfUsers, UsersRepositoryJPA usersRepository) {
        List<User> fakeUsers = new ArrayList<>();

        for (int i = 0; i < numberOfUsers; i++) {
            User fakeUser = generateFakeUser();
            fakeUsers.add(fakeUser);
        }

        usersRepository.saveAll(fakeUsers);
    }

    private static User generateFakeUser() {
        Faker fakerUserGenerator = new Faker();
        Name fakeName = fakerUserGenerator.name();
        int age = fakerUserGenerator.number().numberBetween(22, 45);

        return new User(fakeName.lastName(), fakeName.firstName(), "dog.jpg", age);
    }
}
