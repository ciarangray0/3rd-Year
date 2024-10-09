package ct326A1;
/**
 * Exception thrown when an invalid date is encountered in the {@link Expense} class.
 * Thrown when the provided date is in the future.
 */

public class InvalidDateException extends Exception {
    public InvalidDateException(String message) {
        super(message);
    }
}
