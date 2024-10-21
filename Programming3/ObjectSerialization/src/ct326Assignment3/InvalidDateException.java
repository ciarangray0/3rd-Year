package ct326Assignment3;

/**
 * The {@code InvalidDateException} is thrown when an invalid date is encountered,
 * such as when a future date is provided where it is not allowed.
 */
public class InvalidDateException extends Exception {
    /**
     * Constructs an {@code InvalidDateException} with the specified detail message.
     *
     * @param s The detail message explaining the cause of the exception.
     */
    public InvalidDateException(String s) {
        super(s);
    }
}
