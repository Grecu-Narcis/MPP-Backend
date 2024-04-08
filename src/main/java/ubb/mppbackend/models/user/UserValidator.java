package ubb.mppbackend.models.user;

import ubb.mppbackend.exceptions.UserValidatorException;

import java.util.*;

public class UserValidator {
    public static void validate(User userToValidate) throws UserValidatorException {
        List<String> errors = new ArrayList<>();

        if (userToValidate.getFirstName().length() < 3)
            errors.add("First name is too short");

        if (userToValidate.getLastName().length() < 3)
            errors.add("Last name is too short");

        if (userToValidate.getAge() < 0 || userToValidate.getAge() > 100)
            errors.add("Invalid age");

        if (!errors.isEmpty())
            throw new UserValidatorException(errors);
    }
}
