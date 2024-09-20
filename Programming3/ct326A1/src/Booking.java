import java.time.LocalDateTime;

public class Booking {
    private int pNum, bNum;
    private String hpName, hpAddress;
    private LocalDateTime dateAndTime;
    private static int lastBookingNum;


    public Booking(int pNum, String hpName, String hpAddress, LocalDateTime dateAndTime) {
        this.pNum = pNum;
        this.hpName = hpName;
        this.hpAddress = hpAddress;
        this.dateAndTime = dateAndTime;
        this.bNum = createBookingNumber();
    }
    /*
    public String getHealthPractice(int i) {

    }
*/
    public int createBookingNumber() {
        return ++lastBookingNum;
    }

    public String ToString() {
        return "Hi";
    }
}
