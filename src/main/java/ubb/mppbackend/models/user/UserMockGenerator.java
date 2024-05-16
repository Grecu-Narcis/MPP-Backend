package ubb.mppbackend.models.user;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import ubb.mppbackend.repositories.UsersRepositoryJPA;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to generate and save fake User data into a UsersRepository using JPA.
 */
@SuppressWarnings("unused")
public class UserMockGenerator {
    /**
     * Generates a specified number of fake User records and saves them into the provided UsersRepository.
     *
     * @param numberOfUsers    The number of fake User records to generate.
     * @param usersRepository  The UsersRepositoryJPA instance where generated users will be saved.
     * @return                 A list containing the generated fake User records.
     */
    public static List<User> generateFakeData(int numberOfUsers, UsersRepositoryJPA usersRepository) {
        List<User> fakeUsers = new ArrayList<>();

        for (int i = 0; i < numberOfUsers; i++) {
            User fakeUser = generateFakeUser();
            fakeUsers.add(fakeUser);
        }

        usersRepository.saveAll(fakeUsers);
        return fakeUsers;
    }

    /**
     * Generates a single fake User record with random data.
     *
     * @return  A new User object populated with fake data.
     */
    private static User generateFakeUser() {
        Faker fakerUserGenerator = new Faker();
        Name fakeName = fakerUserGenerator.name();
        String fakeEmail = fakerUserGenerator.internet().emailAddress();
        String fakePassword = fakerUserGenerator.internet().password();

        return new User(fakeName.lastName(), fakeName.firstName(), fakeEmail, fakePassword);
    }
}
