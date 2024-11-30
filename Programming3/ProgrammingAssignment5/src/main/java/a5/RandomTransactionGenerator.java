package a5;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * The {@code RandomTransactionGenerator} class is responsible for generating random transactions
 * and submitting them to the bank's transaction queue for processing.
 *
 * This class simulates transactions for various accounts within the bank by generating random
 * deposit or withdrawal amounts. The transactions are submitted continuously while the bank is open.
 * Once the bank is closed, the class sends a "poison pill" transaction to signal the
 * AutomatedBankClerk threads to stop processing.
 */
public class RandomTransactionGenerator implements Runnable{
    private Bank bank;

    /**
     * Constructs a {@code RandomTransactionGenerator} for a specific bank.
     *
     * @param bank The {@link Bank} instance for which transactions are to be generated.
     */
    public RandomTransactionGenerator(Bank bank) {
        this.bank = bank;
    }

/**
 * Continuously generates random transactions and submits them to the bank's transaction queue.
 *
 * <p>Each transaction is randomly chosen to be either a deposit or a withdrawal within the range
 * of -10,000 to 10,000 EUR. The transactions are submitted while the bank is open, and
 * the thread sleeps for a random amount of time (up to 1 second) between generating transactions.</p>
 *
 * <p>When the bank closes, the method submits two "poison pill" transactions (account number 0 and amount 0)
 * to signal the worker threads to terminate.</p>
 */
    @Override
    public void run() {
        try {
        while (bank.getAvailable() == true) { //while bank is open

            Random random = new Random();
            ArrayList<Integer> accountNumbers = new ArrayList<>(bank.getAccountNumbers()); //get all of the account numbers
            int accountnumber = accountNumbers.get(random.nextInt(accountNumbers.size())); //pick a random account
            int ammount = random.nextInt(-10001, 10001); //pick a random transaction ammount between -10,000 and 10,000
            Transaction transaction = new Transaction(accountnumber, ammount);
            bank.submitTransaction(transaction); //add the transaction to the linked blocking queue
            Thread.sleep((int) (Math.random() * 1000)); //sleep the thread for a random time up to 1 second
        }
        bank.submitTransaction(new Transaction(0, 0));//if bank has been shut down, send the 'poison pill' to the clerk threads to shut down
        bank.submitTransaction(new Transaction(0, 0));//sending 2 to ensure both threads recieve one
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); //stop the thread if exception is caught
            }

    }
}
