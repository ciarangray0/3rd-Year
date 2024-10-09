package ct326A1;

import java.util.ArrayList;

/**
 * The {@code PrinterByLabel} class implements the {@code ExpensePrinter} interface
 * and organizes expenses by their categories before printing them.
 */
public class PrinterByLabel implements ExpensePrinter {

    /**
     * Overrides the print method from {@code ExpensePrinter} interface
     * Prints the list of expenses organized by their categories.
     *
     * @param expenses the list of expenses to print
     * @return a string representing the printed expenses, organized by categories
     */
    @Override
    public String print(ArrayList<Expense> expenses) {
        String travelAndSubsistence = "TRAVEL_AND_SUBSISTENCE\n";
        String supplies = "SUPPLIES\n";
        String entertainment = "ENTERTAINMENT\n";
        String equipment = "EQUIPMENT\n";
        String other = "OTHER\n";

        for (Expense expense : expenses) {
            switch (expense.getCategory()) {
                case TRAVEL_AND_SUBSISTENCE:
                    travelAndSubsistence += expense.toString() + "\n";
                    break;
                case SUPPLIES:
                    supplies += expense.toString() + "\n";
                    break;
                case ENTERTAINMENT:
                    entertainment += expense.toString() + "\n";
                    break;
                case EQUIPMENT:
                    equipment += expense.toString() + "\n";
                    break;
                case OTHER:
                    other += expense.toString() + "\n";
                    break;
            }
        }

        return travelAndSubsistence + supplies + entertainment + equipment + other;
    }
}
