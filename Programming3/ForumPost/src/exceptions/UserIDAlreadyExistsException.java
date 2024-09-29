package exceptions;

public class UserIDAlreadyExistsException extends Exception {
    public UserIDAlreadyExistsException(String message) {
        super(message);
    }
}
