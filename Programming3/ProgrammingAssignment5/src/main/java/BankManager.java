import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class BankManager {
    public static void main(String[] args) {
        Bank bank = new Bank();
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

        t.start();
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
