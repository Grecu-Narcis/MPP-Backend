package ubb.mppbackend.business;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.exceptions.UserValidatorException;
import ubb.mppbackend.models.user.User;
import ubb.mppbackend.models.user.UserValidator;
import ubb.mppbackend.repositories.UsersRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UsersService {
    private final UsersRepositoryJPA usersRepository;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public UsersService(UsersRepositoryJPA usersRepository, SimpMessagingTemplate messagingTemplate) {
        this.usersRepository = usersRepository;
        this.messagingTemplate = messagingTemplate;

//        this.usersRepository.save(new User("Grecu", "Narcis", "narcis.jpg", 20));
//        this.usersRepository.save(new User("Bogdan", "Ciornohac", "bogdan.jpg", 20));
//        this.usersRepository.save(new User("Medeea", "Condurache", "medeea.jpg", 21));
//        this.usersRepository.save(new User("Iosif", "Pintilie", "iosif.jpg", 20));
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    @Async
    public void sendData() {
        System.out.println("This runs every 5 seconds!");

        // messagingTemplate.convertAndSend("/topic/users", this.generateFakeData(5));
    }

    public List<User> generateFakeData(int numberOfUsers) {
        List<User> fakeUsers = new ArrayList<>();

        for (int i = 0; i < numberOfUsers; i++) {
            User fakeUser = this.generateFakeUser();
            fakeUsers.add(fakeUser);
        }

        this.usersRepository.saveAll(fakeUsers);

        return fakeUsers;
    }

    private User generateFakeUser() {
        Faker fakerUserGenerator = new Faker();
        
        Name fakeName = fakerUserGenerator.name();
        int age = fakerUserGenerator.number().numberBetween(18, 45);

        return new User(fakeName.lastName(), fakeName.firstName(), "dog.jpg", age);
    }

    public User getById(Long idToSearch) throws RepositoryException {
        Optional<User> foundUser = this.usersRepository.findById(idToSearch);

        if (foundUser.isEmpty())
            throw new RepositoryException("User not found!");

        return foundUser.get();
    }

    public void addUser(User userToAdd) throws UserValidatorException {
        UserValidator.validate(userToAdd);

        this.usersRepository.save(userToAdd);
    }

    public void updateUser(User userToUpdate) throws UserValidatorException, RepositoryException {
        UserValidator.validate(userToUpdate);

        if (this.usersRepository.findById(userToUpdate.getId()).isEmpty())
            throw new RepositoryException("User not found!");

        this.usersRepository.save(userToUpdate);
    }

    public void deleteUser(Long idToRemove) {
        this.usersRepository.deleteById(idToRemove);
    }

    public List<User> getPage(int requiredPage, boolean isAscending, int pageSize) {
        Sort sort = Sort.by(isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, "age");
        Pageable requestedPage = PageRequest.of(requiredPage, pageSize, sort);

        return this.usersRepository.findAll(requestedPage).getContent();
    }

    public List<User> getAll() {
        return this.usersRepository.findAll();
    }
}
