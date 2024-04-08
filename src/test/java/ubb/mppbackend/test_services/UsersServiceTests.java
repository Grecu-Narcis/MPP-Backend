package ubb.mppbackend.test_services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ubb.mppbackend.business.UsersService;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.models.user.User;
import ubb.mppbackend.repositories.UsersRepository;

import java.util.UUID;

public class UsersServiceTests {
    private final UsersService usersService;

    public UsersServiceTests() {
        UsersRepository usersRepository = new UsersRepository();
        this.usersService = new UsersService(usersRepository);
    }

    @Test
    public void testGetUserByIdSuccess() throws Exception {
        UUID idToSearch = this.usersService.getAll().get(0).getId();
        Assertions.assertEquals(this.usersService.getById(idToSearch).getId(), idToSearch);
    }

    @Test
    public void testGetUserByIdFails() {
        Assertions.assertThrows(RepositoryException.class, () -> this.usersService.getById(UUID.randomUUID()));
    }

    @Test
    public void testAddUser() throws Exception {
        User userToAdd = new User("test", "user", "test.url", 20);
        this.usersService.addUser(userToAdd);

        User foundUser = this.usersService.getById(userToAdd.getId());

        Assertions.assertEquals(userToAdd, foundUser);
    }

    @Test
    public void testUpdateUserThrowsException() {
        User userToUpdate = new User("test", "user", "test.url", 20);
        Assertions.assertThrows(RepositoryException.class, () -> this.usersService.updateUser(userToUpdate));
    }

    @Test
    public void testDeleteUser() {
        User userToDelete = this.usersService.getAll().get(0);
        this.usersService.deleteUser(userToDelete.getId());

        Assertions.assertThrows(RepositoryException.class, () -> this.usersService.getById(userToDelete.getId()));
    }

    @Test
    public void testGetPage() {
        Assertions.assertEquals(this.usersService.getPage(1, true, 5).size(), 5);
        Assertions.assertEquals(this.usersService.getPage(1, false, 5).size(), 5);
    }
}
