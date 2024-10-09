import ct326A1.*;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Expense} and {@link ExpensesPortal} classes.
 * The tests cover expense creation, printing with lambda expressions and anonymous inner classes,
 * and organizing expenses by category.
 */
public class ExpensesTest {
    Expense expense;
    Expense expense1;
    Expense expense2;
    Expense expense3;
    ExpensesPortal portal;

    /**
     * Initializes sample expenses and an {@link ExpensesPortal} instance.
     */
    public ExpensesTest() {
        portal = new ExpensesPortal();
        try {
            expense1 = new Expense(LocalDate.of(2022, 9, 23), "Flight to Glasgow", ExpenseCategory.TRAVEL_AND_SUBSISTENCE, Money.of(CurrencyUnit.EUR, 270.59));
            expense2 = new Expense(LocalDate.of(2022, 9, 20), "Dell 17-inch monitor", ExpenseCategory.EQUIPMENT, Money.of(CurrencyUnit.USD, 540.00));
            expense3 = new Expense(LocalDate.of(2022, 9, 21), "Java for Dummies", ExpenseCategory.OTHER, Money.of(CurrencyUnit.EUR, 17.99));
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests the creation of an {@link Expense} object and verifies all fields.
     */
    @Test
    void makeExpense() {
        try {
            expense = new Expense(LocalDate.of(2022, 9, 20), "Dell 17-inch monitor", ExpenseCategory.EQUIPMENT, Money.of(CurrencyUnit.USD, 540.00));
        } catch (InvalidDateException e) {
            fail();
        }
        assertNotNull(expense);
        assertEquals(LocalDate.of(2022, 9, 20), expense.getDate());
        assertEquals("Dell 17-inch monitor", expense.getDescription());
        assertEquals(ExpenseCategory.EQUIPMENT, expense.getCategory());
        assertEquals(Money.of(CurrencyUnit.USD, 540.00), expense.getAmount());
        assertFalse(expense.getApproved());
    }

    /**
     * Tests that creating an {@link Expense} with an invalid date throws {@link InvalidDateException}.
     */
    @Test
    void makeExpenseWithInvalidDate() {
        // Assert that an InvalidDateException is thrown when an invalid date is used to create an Expense
        assertThrows(InvalidDateException.class, () -> {
            new Expense(LocalDate.of(2024, 11, 1), "Dell 17-inch monitor", ExpenseCategory.EQUIPMENT, Money.of(CurrencyUnit.USD, 540.00));
        });
    }

    /**
     * Tests the {@code toString()} method of the {@link Expense} class.
     */
    @Test
    void testToString() {
        String expected = "2022-09-20: Dell 17-inch monitor - EQUIPMENT - USD 540.00";
        assertEquals(expected, expense2.toString());
    }

    /**
     * Tests that submitting an expense with a null amount field throws an {@link InvalidExpenseException}.
     */
    @Test
    void testNullExpense() {
        try {
            expense = new Expense(LocalDate.of(2022, 9, 20), "Dell 17-inch monitor", ExpenseCategory.EQUIPMENT, null);
        } catch (InvalidDateException e) {
            System.out.println(e.getMessage());
        }
        finally{
            assertThrows(InvalidExpenseException.class, () -> {
                portal.submitExpense(expense);
            });
        }
    }

    /**
     * Tests printing expenses using a lambda expression to implement the {@link ExpensePrinter} interface.
     */
    @Test
    void testPrintExpensesWithLambda() {
        try {
            portal.submitExpense(expense1);
            portal.submitExpense(expense2);
            portal.submitExpense(expense3);
        } catch (InvalidExpenseException e) {
            throw new RuntimeException(e);
        }

        String lambdaExpenseList = portal.printExpenses(expenses -> {
            String output = "";
            for (Expense expense : expenses) {
                output += expense.toString();
                output += "\n";
            }
            return output.trim();
        });

        String expectedExpenseList = expense1.toString() + "\n" + expense2.toString() + "\n" + expense3.toString();
        assertEquals(expectedExpenseList, lambdaExpenseList);
    }

    /**
     * Tests printing an empty list of expenses using a lambda expression.
     */
    @Test
    void testEmptyExpensesList() {
        String emptyLambdaExpenseList = portal.printExpenses(expenses -> {
            String output = "";
            for (Expense expense : expenses) {
                output += expense.toString();
                output += "\n";
            }
            return output.trim();
        });
        String expectedEmptyExpensesString = "";
        assertEquals(expectedEmptyExpensesString, emptyLambdaExpenseList);
    }

    /**
     * Tests printing a summary of expenses using an anonymous inner class to implement the {@link ExpensePrinter} interface.
     * The summary includes the total expenses converted to EUR.
     */
    @Test
    public void testPrintSummaryUsingAnonymousInnerClass() {
        try {
            portal.submitExpense(expense1);
            portal.submitExpense(expense2);
            portal.submitExpense(expense3);
        } catch (InvalidExpenseException e) {
            throw new RuntimeException(e);
        }

        String summary = portal.printExpenses(new ExpensePrinter() {
            @Override
            public String print(ArrayList<Expense> expenses) {
                Money totalExpenses = portal.totalExpenses();
                return String.format("There are %d expenses in the system totalling to a value of %s.", expenses.size(), totalExpenses);
            }
        });

        String expected = "There are 3 expenses in the system totalling to a value of EUR 779.98.";
        assertEquals(expected, summary);
    }

    /**
     * Tests printing expenses by category using a {@link PrinterByLabel} instance.
     */
    @Test
    public void testPrintExpensesByLabel() {
        try {
            portal.submitExpense(expense1);
            portal.submitExpense(expense2);
            portal.submitExpense(expense3);
        } catch (InvalidExpenseException e) {
            throw new RuntimeException(e);
        }


        // Create an instance of PrinterByLabel
        PrinterByLabel printerByLabel = new PrinterByLabel();

        // Print the expenses using the printer
        String printedExpenses = portal.printExpenses(printerByLabel);

        // Define the expected output
        String expectedOutput = "TRAVEL_AND_SUBSISTENCE\n" +
                "2022-09-23: Flight to Glasgow - TRAVEL_AND_SUBSISTENCE - EUR 270.59\n" +
                "SUPPLIES\n" +
                "ENTERTAINMENT\n" +
                "EQUIPMENT\n" +
                "2022-09-20: Dell 17-inch monitor - EQUIPMENT - USD 540.00\n" +
                "OTHER\n" +
                "2022-09-21: Java for Dummies - OTHER - EUR 17.99\n";

        // Assert that the printed expenses match the expected output
        assertEquals(expectedOutput, printedExpenses);
    }

    /**
     * Tests the approval of an {@link Expense} and verifies its status.
     */
    @Test
    void testApproveExpense() {
        expense1.approve();
        assertTrue(expense1.getApproved());
    }
}
