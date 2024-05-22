package ubb.mppbackend.test_models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ubb.mppbackend.models.user.User;

import java.util.UUID;

public class UserTests {
    @Test
    public void testGetId() {
        User user = new User("test", "test", "test", 23);
        Long id = user.getId();
        Assertions.assertEquals(user.getId(), id);
    }

    @Test
    public void testGetFirstName() {
        User user = new User("test", "test", "test", 23);
        String firstName = user.getFirstName();
        Assertions.assertEquals(user.getFirstName(), firstName);
    }

    @Test
    public void testSetFirstName() {
        User user = new User("test", "test", "test", 23);
        user.setFirstName("newTest");
        Assertions.assertEquals(user.getFirstName(), "newTest");
    }

    @Test
    public void testGetLastName() {
        User user = new User("test", "test", "test", 23);
        String lastName = user.getLastName();
        Assertions.assertEquals(user.getLastName(), lastName);
    }

    @Test
    public void testSetLastName() {
        User user = new User("test", "test", "test", 23);
        user.setLastName("newTest");
        Assertions.assertEquals(user.getLastName(), "newTest");
    }

    @Test
    public void testGetPictureUrl() {
        User user = new User("test", "test", "test", 23);
        String pictureUrl = user.getPictureUrl();
        Assertions.assertEquals(user.getPictureUrl(), pictureUrl);
    }

    @Test
    public void testSetPictureUrl() {
        User user = new User("test", "test", "test", 23);
        user.setPictureUrl("newTest");
        Assertions.assertEquals(user.getPictureUrl(), "newTest");
    }

    @Test
    public void testGetAge() {
        User user = new User("test", "test", "test", 23);
        int age = user.getAge();
        Assertions.assertEquals(user.getAge(), age);
    }

    @Test
    public void testSetAge() {
        User user = new User("test", "test", "test", 23);
        user.setAge(24);
        Assertions.assertEquals(user.getAge(), 24);
    }

    @Test
    public void testUpdate() {
        User user = new User("test", "test", "test", 23);
        User newUser = new User("newTest", "newTest", "newTest", 24);
        user.update(newUser);
        Assertions.assertEquals(user.getFirstName(), "newTest");
        Assertions.assertEquals(user.getLastName(), "newTest");
        Assertions.assertEquals(user.getPictureUrl(), "newTest");
        Assertions.assertEquals(user.getAge(), 24);
    }

    @Test
    public void testConstructor() {
        User user = new User("test", "test", "test", 23);
        Assertions.assertEquals(user.getFirstName(), "test");
        Assertions.assertEquals(user.getLastName(), "test");
        Assertions.assertEquals(user.getPictureUrl(), "test");
        Assertions.assertEquals(user.getAge(), 23);
    }

    @Test
    public void testEqualsSuccess() {
        User user1 = new User("test", "test", "test", 23);
        user1.setId((long) 2);

        Assertions.assertEquals(user1, user1);
    }

    @Test
    public void testEqualsFailure() {
        User user1 = new User("test", "test", "test", 23);
        User user2 = new User("test", "test", "test", 23);
        user1.setId((long) 2);
        user2.setId((long) 3);

        Assertions.assertNotEquals(user1, user2);
        Assertions.assertNotEquals(user1, null);
        Assertions.assertNotEquals(user1, "test");
    }

    @Test
    public void testToString() {
        User user = new User("test", "test", "test", 23);
        Assertions.assertEquals(user.toString(), "User{id=" + user.getId() + ", firstName='test', lastName='test', pictureUrl='test', age=23}");
    }
}
