package ct326Assignment3;

/**
 * The {@code DuplicateAchievementException} is thrown when attempting to add
 * a duplicate achievement to a player's achievement list.
 */
public class DuplicateAchievementException extends Exception {

    /**
     * Constructs a {@code DuplicateAchievementException} with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public DuplicateAchievementException(String message) {
        super(message);
    }
}
