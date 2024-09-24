import exceptions.InvalidDateAndTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
//Ciaran Gray
//Student number: 22427722
public class DateAndTime {
    //class for representing the LocalDateTime object of the booking
    private LocalDateTime dateAndTime;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'On' EEEE, dd MMMM yyyy, 'at' HH:mm", Locale.ENGLISH); //formatter object for the DateAndTime classes toString method
    private String formattedDateTime;
    public DateAndTime(int year, int month, int day, int hour, int minute)throws InvalidDateAndTimeException {
        if(LocalDateTime.of(year, month, day, hour, minute).isBefore(LocalDateTime.now())) { //this line checks if the given date and time is in the past
            throw new InvalidDateAndTimeException("Booking date cannot be in the past"); //if date is in the past, exception is thrown
        }
        this.dateAndTime = LocalDateTime.of(year, month, day, hour, minute); //set dateAndTime
        this.formattedDateTime = dateAndTime.format(formatter); //set formatted string for toString()
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }
    public String toString() { //class's toString()
        return formattedDateTime;
    }
}
