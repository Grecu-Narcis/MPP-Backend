package ubb.mppbackend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.exceptions.UserValidatorException;
import ubb.mppbackend.models.user.User;
import ubb.mppbackend.models.user.UserValidator;
import ubb.mppbackend.repositories.UsersRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User getById(UUID idToSearch) throws RepositoryException {
        return this.usersRepository.getUserById(idToSearch);
    }

    public void addUser(User userToAdd) throws UserValidatorException {
        UserValidator.validate(userToAdd);

        this.usersRepository.addUser(userToAdd);
    }

    public void updateUser(User userToUpdate) throws UserValidatorException, RepositoryException {
        UserValidator.validate(userToUpdate);

        this.usersRepository.updateUser(userToUpdate);
    }

    public void deleteUser(UUID idToRemove) {
        this.usersRepository.deleteUser(idToRemove);
    }

    public List<User> getPage(int requiredPage, boolean isAscending, int pageSize) {
        return this.usersRepository.getPage(requiredPage, isAscending, pageSize);
    }

    public List<User> getAll() {
        return this.usersRepository.getAll();
    }
}
