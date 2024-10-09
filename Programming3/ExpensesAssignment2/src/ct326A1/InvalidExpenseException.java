package ct326A1;

/**
 * Exception thrown when an invalid expense is tried to be added to the list of expenses in the {@link ExpensesPortal} class.
 * Occurs when an expense is missing required fields such as date, category, amount, or description.
 */
public class InvalidExpenseException extends Exception {
    public InvalidExpenseException(String message) {
        super(message);
    }
}
