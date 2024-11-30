package a5;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
/**
 * The {@code BankManager} class serves as the entry point for the bank simulation.
 * It initializes the {@link Bank} and a set of {@link Account} instances, starts
 * transaction processing with {@link RandomTransactionGenerator}, and runs the
 * simulation for a specified duration.
 *
 * <p>The class demonstrates the usage of the {@link Bank} system by:
 * <ul>
 *     <li>Creating a bank instance</li>
 *     <li>Adding accounts to the bank</li>
 *     <li>Starting transaction generation</li>
 *     <li>Running the bank for 10 seconds</li>
 *     <li>Shutting down the bank and displaying final account balances</li>
 * </ul>
 */
public class BankManager {
/**
 * The main method initializes and runs the bank simulation.
 *
 * <p>Steps performed:
 * <ol>
 *     <li>Creates a {@link Bank} instance</li>
 *     <li>Initializes three {@link Account} instances with predefined balances</li>
 *     <li>Adds the accounts to the bank</li>
 *     <li>Starts the {@link RandomTransactionGenerator} in a separate thread</li>
 *     <li>Opens the bank to allow transactions to be processed</li>
 *     <li>Runs the simulation for 10 seconds</li>
 *     <li>Closes the bank and terminates transaction processing</li>
 *     <li>Prints the final account details</li>
 * </ol>
 *
 */
    public static void main(String[] args) {
        Bank bank = new Bank(); //instansiate the bank
        //instansiate 3 new accounts
        Account a1;
        Account a2;
        Account a3;
        try {
             a1 = new Account(123, Money.of(CurrencyUnit.EUR, 50000));
             a2 = new Account(456, Money.of(CurrencyUnit.EUR, 10000));
             a3 = new Account(789, Money.of(CurrencyUnit.EUR, 35000));
        } catch (NegativeBalanceException e) {
            throw new RuntimeException(e);
        }

        bank.addAccount(a1);
        bank.addAccount(a2);
        bank.addAccount(a3);

        Thread t = new Thread(new RandomTransactionGenerator(bank));

        t.start(); //start the random transaction generator thread
        bank.openBank();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); //stop the thread if exception is caught
        }



        bank.closeBank(); //close the bank, set available to false and stop the thread pool
        //the randomtransaction generator will stop as the available will be set to false
        System.out.println("Account details:");
        bank.printAccountDetails();
    }
}
