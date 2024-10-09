package ct326A1;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * The {@code ExpensesPortal} class maintains a list of expenses and provides
 * methods for submitting, printing, and calculating total expenses.
 */
public class ExpensesPortal {
    private ArrayList<Expense> expenses;

    /**
     * Constructs an {@code ExpensesPortal} with an empty list of expenses.
     */
    public ExpensesPortal() {
        expenses = new ArrayList<>();
    }

    /**
     * Prints the expenses using a specified {@code ExpensePrinter}.
     *
     * @param printer the printer used to format and print the expenses
     * @return a string representing the printed expenses
     */
    public String printExpenses(ExpensePrinter printer) {
        return printer.print(expenses);
    }

    /**
     * Returns the list of expenses.
     *
     * @return the list of expenses
     */
    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Submits a new expense to the list of expenses.
     * Validates the expense before adding it to the list.
     *
     * @param expense the expense to be added
     * @throws InvalidExpenseException if the expense is invalid (missing required fields)
     */
    public void submitExpense(Expense expense) throws InvalidExpenseException {
        try {
            //first check if the expense is valid
            if (validExpense(expense)) {
                this.expenses.add(expense);
            }
        } catch (InvalidExpenseException e) {
            throw e;
        }
    }

    /**
     * Checks the expense to make sure that it contains all required fields.
     *
     * @param expense the expense to validate
     * @return {@code true} if the expense is valid
     * @throws InvalidExpenseException if any required fields (date, category, amount, description) are null
     */
    public boolean validExpense(Expense expense) throws InvalidExpenseException {
        if (expense.getDate() != null &&
                expense.getCategory() != null &&
                expense.getAmount() != null &&
                expense.getDescription() != null) {
            return true;
        } else {
            throw new InvalidExpenseException("Expense is missing required fields");
        }
    }

    /**
     * Calculates the total value of all expenses in EUR.
     * If an expense is in USD, it is converted to EUR using a conversion rate of 0.91.
     *
     * @return the total value of expenses in EUR
     */
    public Money totalExpenses() {
        Money totalSum = Money.of(CurrencyUnit.EUR, 0.00);  //start with 0 EUR
        double conversionRate = 0.91;  //conversion rate USD or EUR

        for (Expense expense : expenses) {
            Money amount = expense.getAmount(); //get ammount for each expense

            if (amount.getCurrencyUnit().equals(CurrencyUnit.USD)) {
                //convert USD to euro's, and round up
                Money converted = amount.convertedTo(CurrencyUnit.EUR, BigDecimal.valueOf(conversionRate), RoundingMode.HALF_UP);
                totalSum = totalSum.plus(converted); //add to sum
            } else {
                totalSum = totalSum.plus(amount); //add to sum
            }
        }
        return totalSum;
    }
}
