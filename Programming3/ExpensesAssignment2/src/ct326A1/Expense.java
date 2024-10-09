package ct326A1;

import org.joda.money.Money;
import java.time.LocalDate;

/**
 * The {@code Expense} class represents an employee's expense claim.
 * Each expense has a date, description, amount, category, and approval status.
 */
public class Expense {
    private LocalDate date;
    private String description;
    private Money amount;
    private ExpenseCategory category;
    private boolean approved;

    /**
     * Constructs an Expense with the specified date, description, category, and amount.
     *
     * @param date the date of the expense
     * @param description a description of the expense
     * @param category the category of the expense
     * @param amount the amount of the expense (in {@code joda.money.Money})
     * @throws InvalidDateException if the provided date is in the future
     */
    public Expense(LocalDate date, String description, ExpenseCategory category, Money amount) throws InvalidDateException {
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDateException("Date cannot be in the future");
        } else {
            this.date = date;
            this.amount = amount;
            this.description = description;
            this.category = category;
            this.approved = false;  // Default: unapproved
        }
    }
    /**
     * Returns the approval status of the expense.
     *
     * @return {@code true} if the expense is approved, {@code false} otherwise
     */
    public boolean getApproved() {
        return approved;
    }

    /**
     * Sets the approval status of the expense.
     *
     * @param approved the new approval status
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * Returns the date of the expense.
     *
     * @return the expense date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the expense.
     *
     * @param date the new expense date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the description of the expense.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the expense.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the amount of the expense.
     *
     * @return the amount (as {@code joda.money.Money})
     */
    public Money getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the expense.
     *
     * @param amount the new amount of the expense
     */
    public void setAmount(Money amount) {
        this.amount = amount;
    }

    /**
     * Returns the category of the expense.
     *
     * @return the expense category
     */
    public ExpenseCategory getCategory() {
        return category;
    }

    /**
     * Sets the category of the expense.
     *
     * @param category the new expense category
     */
    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    /**
     * Approves the expense.
     */
    public void approve() {
        this.approved = true;
    }

    /**
     * Returns a string representation of the expense in the format:
     * <p>Example: {@code 2022-09-20: Dell 17-inch monitor - EQUIPMENT - USD 540.00}</p>
     *
     * @return a formatted string representing the expense
     */
    @Override
    public String toString() {
        return String.format("%s: %s - %s - %s %.2f",
                date, description, category, amount.getCurrencyUnit(), amount.getAmount());
    }
}
