package ct326A1;

import java.util.ArrayList;

/**
 * The {@code ExpensePrinter} interface defines a method for printing a list of expenses.
 */
public interface ExpensePrinter {

    /**
     * Prints the list of expenses and returns a string representation.
     *
     * @param expenses the list of expenses to print
     * @return a string representing the printed expenses
     */
    public String print(ArrayList<Expense> expenses);
}
