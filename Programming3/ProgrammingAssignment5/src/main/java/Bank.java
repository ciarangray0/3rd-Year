import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Bank {
    private Map<Integer, Account> customerAccount;
    private LinkedBlockingQueue<Transaction> bankAccountTransactionsqueue;
    private ExecutorService threadPool;
    private volatile boolean available = false;

    public Bank() {
        customerAccount = new HashMap<>();
        bankAccountTransactionsqueue = new LinkedBlockingQueue<>();
        threadPool = Executors.newFixedThreadPool(2); //set the size to 2 to allow 2 threads to run
    }

    public boolean getAvailable() {
        return available;
    }
    public void setAvailable(boolean b) {
        this.available = b;
    }

    public void addAccount(Account a) {
        customerAccount.put(a.getAccountNumber(), a);
    }

    public Account getAccount(int num) {
        return customerAccount.get(num);
    }

    public void submitTransaction(Transaction t) {
        bankAccountTransactionsqueue.add(t);
    }

    public void printAccountDetails() {
        customerAccount.forEach((num, account) ->
                System.out.println("Account Number: " + num + ", Balance: " + account.getBalance()));

    }

    public Set<Integer> getAccountNumbers() {
        return customerAccount.keySet();
    }

    public void openBank() {
        setAvailable(true);
        for (int i = 1; i <= 2; i++) {
            threadPool.submit(new AutomatedBankClerk("TPT" + i));
        }

    }

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

    private class AutomatedBankClerk implements Runnable {
        private final String name;
        private int deposits;
        private int withdrawals;
        private int transactions;

        public AutomatedBankClerk(String name) {
            this.name = name;
        }

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
                        }
                    }
                    Thread.sleep((int) (Math.random() * 1000)); //sleep for a random time of up to 1 second
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); //stop the thread if exception is caught
            }
            finally {
                System.out.printf("\n%s processed %d deposits and %d withdrawals.%n", name, deposits, withdrawals);
            }
        }
    }

}
