import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

import javax.annotation.processing.SupportedAnnotationTypes;

public class BookingTest {
    LocalDateTime dateTime1 = LocalDateTime.of(2024, 10, 26, 10, 30);
    Booking booking1 = new Booking(123, "Galway Family Practice", "Tuam Road, Galway H56 RY85", dateTime1);

    @Test
    void makeBooking() {
        assertNotNull(booking1);
    }
    @Test
    void printBooking() {
        assertEquals("Patient number: 123 Practice name: Galway Family Practice Practice Address: Tuam Road, Galway H56 RY85 Date & time: On 26 October 2024 at 10:30", booking1.ToString());
    }
    /*@Test
    void returnHealthPractice() {
        Booking booking1 = new Booking();
        assertEquals("Galway Family Practice, Tuam Road, Galway H56 RY85", booking1.getHealthPractice(1));
    }
     */
}
