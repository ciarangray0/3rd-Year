import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTransactionGenerator implements Runnable{
    private Bank bank;

    public RandomTransactionGenerator(Bank bank) {
        this.bank = bank;
    }

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
