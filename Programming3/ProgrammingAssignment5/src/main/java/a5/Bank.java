package a5;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * The {@code Bank} class simulates a bank managing a customer {@code Account} and
 * processing transactions. It uses a thread-safe architecture to handle
 * concurrent transaction processing via a {@link LinkedBlockingQueue} and
 * worker threads.
 *
 * The class supports features such as adding accounts, submitting transactions,
 * and processing transactions via {@code AutomatedBankClerk} threads.
 */
public class Bank {
    private Map<Integer, Account> customerAccount;
    private LinkedBlockingQueue<Transaction> bankAccountTransactionsqueue;
    private ExecutorService threadPool;
    private volatile boolean available = false;


    /**
     * Constructs a new {@code Bank} instance. Initializes the account storage
     * and transaction queue, and creates a fixed thread pool for processing
     * transactions.
     */
    public Bank() {
        customerAccount = new HashMap<>();
        bankAccountTransactionsqueue = new LinkedBlockingQueue<>();
        threadPool = Executors.newFixedThreadPool(2); //set the size to 2 to allow 2 threads to run
    }

    /**
     * Checks if the bank is available for processing transactions.
     *
     * @return {@code true} if the bank is available; otherwise {@code false}.
     */
    public boolean getAvailable() {
        return available;
    }

    /**
     * Sets the availability status of the bank for processing transactions.
     *
     * @param b {@code true} to make the bank available; {@code false} otherwise.
     */
    public void setAvailable(boolean b) {
        this.available = b;
    }

    /**
     * Adds a new account to the bank.
     *
     * @param a The {@link Account} instance to be added.
     */
    public void addAccount(Account a) {
        customerAccount.put(a.getAccountNumber(), a);
    }
    /**
     * Retrieves an account given its account number.
     *
     * @param num The account number.
     * @return The {@link Account} instance associated with the account number, or {@code null} if not found.
     */
    public Account getAccount(int num) {
        return customerAccount.get(num);
    }
    /**
     * Submits a new transaction to the bank's transaction queue for processing.
     *
     * @param t The {@link Transaction} to be added to the queue.
     */
    public void submitTransaction(Transaction t) {
        bankAccountTransactionsqueue.add(t);
    }
    /**
     * Prints the details of all accounts, including their account number and balance.
     */
    public void printAccountDetails() {
        customerAccount.forEach((num, account) ->
                System.out.println("Account Number: " + num + ", Balance: " + account.getBalance()));

    }

    /**
     * Retrieves the set of all account numbers in the bank.
     *
     * @return A {@link Set} of integers representing the account numbers.
     */
    public Set<Integer> getAccountNumbers() {
        return customerAccount.keySet();
    }

    /**
     * Opens the bank for transaction processing by starting two {@link AutomatedBankClerk} threads.
     */
    public void openBank() {
        setAvailable(true);
        for (int i = 1; i <= 2; i++) {
            threadPool.submit(new AutomatedBankClerk("TPT" + i));
        }

    }

    /**
     * Closes the bank for transaction processing by shutting down the thread pool.
     * Waits for the threads to terminate, or forcibly shuts them down after 10 seconds.
     */
    public void closeBank() {
        setAvailable(false);
        try {
            threadPool.shutdown();
            //if the threadpool has not shutdown after 10 seconds force it to shut down
            if(!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); //stop the thread if exception is caught
        }
    }

    /**
     * Inner class representing a worker thread that processes transactions from the transaction queue.
     */
    private class AutomatedBankClerk implements Runnable {
        private final String name;
        private int deposits;
        private int withdrawals;
        private int transactions;

        /**
         * Constructs a new {@code AutomatedBankClerk} with a specified name.
         *
         * @param name The name of the clerk thread.
         */
        public AutomatedBankClerk(String name) {
            this.name = name;
        }

        /**
         * Processes transactions from the transaction queue. Deposits or withdraws amounts
         * based on the transaction details. Ensures thread-safe access to accounts using
         * synchronized blocks.
         */
        @Override
        public void run() {
            try {
                //first check if is the available boolean is still set to true
                while (available == true) {
                    Transaction transaction = bankAccountTransactionsqueue.poll(5, TimeUnit.SECONDS); //keep taking transactions until no new transactions are given in a 5 second timeout
                    if (transaction == null || transaction.getAccountNumber() == 0) { //checks if the account is null or the 'poison pill' of accNumber = 0 has been given, indicating to stop the thread
                        break; //breaks out of loop if poison pill has been given
                    }
                    Account account = getAccount(transaction.getAccountNumber()); //get the account assosiated with the transaction
                    if (account != null) {
                        //synchronize the block of code using this instance of the account class, only 1 thread can access this specific account at a time
                        //this instance of the acccount class is being used as the lock
                        synchronized (account) { //synchronized block of code to only allow 1 thread to aquire the lock and excecute the code at a time
                            if (transaction.getAmount() > 0) { //if transaction ammount is bigger then 0, make a deposit
                                account.makeDeposit(Money.of(CurrencyUnit.EUR, transaction.getAmount()));
                                deposits++;
                                System.out.printf("\n%s is processing a deposit of %f from %d.", name, transaction.getAmount(), account.getAccountNumber());
                            } else { //if transaction is not a deposit, it is a withdrawal
                                try {
                                    account.makeWithdrawal(Money.of(CurrencyUnit.EUR, transaction.getAmount()));
                                    withdrawals++;
                                    System.out.printf("\n%s is processing a withdrawal of %f from %d.", name, transaction.getAmount(), account.getAccountNumber());
                                } catch (InsufficientFundsException e) {
                                    System.out.println("Insufficent Funds, transaction cancelled.");
                                }
                            }
                            transactions++;
                        }
                    }
                    Thread.sleep((int) (Math.random() * 1000)); //sleep for a random time of up to 1 second
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); //stop the thread if exception is caught
            }
            finally {
                System.out.printf("\n%s processed %d transactions,  %d deposits and %d withdrawals.%n", name, transactions, deposits, withdrawals);
            }
        }
    }

}
