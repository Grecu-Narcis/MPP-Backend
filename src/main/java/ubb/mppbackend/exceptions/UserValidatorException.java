package ubb.mppbackend.exceptions;

import java.util.List;

public class UserValidatorException extends Exception {
    public UserValidatorException(List<String> errors) {
        super(String.join(", ", errors));
    }
}
