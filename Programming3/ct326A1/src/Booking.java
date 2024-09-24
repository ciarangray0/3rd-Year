import exceptions.InvalidDateAndTimeException;
import exceptions.InvalidPatientNumberException;
//Ciaran Gray
//Student number: 22427722
public class Booking implements HealthPracticeappointmentWebService{
    //declare class variables
    private String pNum;
    private int bNum;
    private HealthPractice healthPractice;
    private DateAndTime dateAndTime;
    private static int counter;


    public Booking(String pNum, HealthPractice healthPractice, int year, int month, int day, int hour, int minute) throws InvalidDateAndTimeException {
        this.pNum = pNum;
        this.healthPractice = healthPractice;
        this.bNum = createBookingNumber(); //calls method to generate the unique booking number
        try {
            this.dateAndTime = new DateAndTime(year, month, day, hour, minute); //try to set the date of the appointment
        } catch (InvalidDateAndTimeException e) {
            throw e; //throw the exception to whichever part of the code tried to instansiate a booking with an invalid date
        }
    }
    public Booking(String pNum, HealthPractice healthPractice) { //overloaded constructor for bookings without specified date
        this.pNum = pNum;
        this.bNum = createBookingNumber(); //generate booking number
        this.healthPractice = healthPractice;
        this.dateAndTime = getBookingDateTime(healthPractice); //call to our implemented method from the webservice interface to return a date for the booking
    }
    public int getBookingNumber() {
        return bNum;
    }
    public HealthPractice getHealthPractice() {
        return healthPractice;
    }
    public DateAndTime getDateandTime() {
        return dateAndTime;
    }
    public int getbNum() {
        return bNum;
    }
    public String getPNum() {
        return pNum;
    }

    @Override
    public DateAndTime getBookingDateTime(HealthPractice practice) { //to set a date and time for a booking when needed
        //just to mimic how the external API would work, there are two different available booking dates, one for each practice
            try {
                if(practice.getHealthPracticeName().equals("Galway Family Practice")) {
                    return new DateAndTime(2024, 11, 14, 7, 30);
                }
                if(practice.getHealthPracticeName().equals("Newbridge Family Practice")) {
                    return new DateAndTime(2024, 11, 14, 7, 30);
                }
                else {
                    return new DateAndTime(2024, 11, 14, 7, 30);
                }
            } catch (InvalidDateAndTimeException e) {
                throw new RuntimeException("External API set an invalid booking date.");
            }
    }

    public void setPnum(String pNum)throws InvalidPatientNumberException {
        //throws an exception to ensure the patient number is the correct length
        if(pNum.length() != 5) {
            throw new InvalidPatientNumberException("Patient number must be 5 characters long" );
        }
        else {
            this.pNum = pNum;
        }
    }

    public int createBookingNumber() {
        counter++; //the static counter variable ensures that each booking number is unique
        this.bNum = counter;
        return bNum;
    }

    public String toString() { //booking's toString method
        return "Booking number: " + bNum + "\nPatient number: " + pNum + "\n" +  healthPractice.toString() + "\nDate & time: " + dateAndTime.toString();
    }

    public static void main(String args[]) {
        //just creating 2 different bookings here to view their toString method's
        HealthPractice healthPractice = new HealthPractice("Galway Family Practice", "Tuam Road, Galway H51 RY64");
        Booking booking;
        Booking booking2;
        try {
            booking = new Booking("WH745", healthPractice, 2024, 12, 5, 9, 45);
        } catch (InvalidDateAndTimeException e) {
            throw new RuntimeException(e);
        }
        booking2 = new Booking("WH073", healthPractice);
        System.out.println(booking.toString());
        System.out.println(booking2.toString());
    }
}
