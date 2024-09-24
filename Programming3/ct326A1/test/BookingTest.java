import exceptions.InvalidDateAndTimeException;
import exceptions.InvalidPatientNumberException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//Ciaran Gray
//Student number: 22427722

public class BookingTest {
    Booking booking1;
    HealthPractice healthPractice1;
    HealthPractice healthPractice2;

        public BookingTest() {
            //setup healthpractice and booking objects for testing
            healthPractice1 = new HealthPractice("Galway Family Practice", "Tuam Road, Galway H56 RY85");
            healthPractice2 = new HealthPractice("Newbridge Family Practice", "Moorefield Road, Kildare W12 HK92");
            try {
                booking1 = new Booking("WH123", healthPractice1, 2024, 10, 26, 10, 30);
            } catch (InvalidDateAndTimeException e) {
                System.out.println(e.getMessage());
            }
        }


    @Test
    void testValidBooking() { //first test for a valid booking, if the test fails the booking has not been made properly
        try {
            booking1 = new Booking("WH123",healthPractice1 , 2024, 10, 26, 10, 30);
            assertNotNull(booking1);
            assertNotNull(booking1.getBookingNumber()); //to ensure a booking number has been made
        }
        catch (InvalidDateAndTimeException e) {
            fail(); //if the booking constructor throws an exception, the test fails
            System.out.println(e.getMessage());
        };
    }
    @Test
    void testInValidBookingDate() { //test for an inValid booking with an invalid date, the test will pass if an exception is thrown for these cases
        assertThrows(InvalidDateAndTimeException.class, () -> {
            Booking booking2 = new Booking("WH123",healthPractice1 , 2022, 10, 26, 10, 30);
        });
        assertThrows(InvalidDateAndTimeException.class, () -> {
            Booking booking2 = new Booking("WH123",healthPractice1 , 2024, 8, 26, 10, 30);
        });
        assertThrows(InvalidDateAndTimeException.class, () -> {
            Booking booking2 = new Booking("WH123",healthPractice1 , 2024, 9, 20, 10, 30);
        });
    }
    @Test
    void testBookingWithoutSpecifiedDate() { //to test the overloaded constructor in the booking class, which allows the user to make a booking without specifying a date
        Booking booking2 = new Booking("WH456", healthPractice2);
        String expectedDate = "On Thursday, 14 November 2024, at 07:30"; //here the webservice interface should return this date with the given healthpractice
        assertEquals(booking2.getDateandTime().toString(), expectedDate); //the test will pass if the correct date has been given to this booking
    }

    @Test
    void ValidPrintBooking() { //to test that the booking's method toString method is printing out the correct string
        assertNotNull(booking1.toString());
        assertEquals("Booking number: " + booking1.getbNum() + "\nPatient number: WH123\nHealth practice name: Galway Family Practice\nHealth practice address: Tuam Road, Galway H56 RY85\nDate & time: On Saturday, 26 October 2024, at 10:30", booking1.toString());
    }
    @Test
    void getPatientNumber() { //to test that the getPNum() method returns the patient number as expected
        assertEquals("WH123", booking1.getPNum());
    }
   @Test
    void setPNumWithVaildPatientNumber() { //to ensure that setPnum() method works correctly
        try {
            booking1.setPnum("WH456");
        }
        catch(InvalidPatientNumberException e) { //test fails if an exception is thrown
            fail();
            System.out.println(e.getMessage());
            assertEquals("WH456", booking1.getPNum()); //pNum should now be updated
        }
    }
   @Test
    void setPNumWithInVaildPatientNumber() { //test will pass if these invalid patient numbers result in thrown exceptions
        assertThrows(InvalidPatientNumberException.class, () -> {
            booking1.setPnum("-45");
        });
       assertThrows(InvalidPatientNumberException.class, () -> {
           booking1.setPnum("WH1000");
       });
    }
    @Test
    void returnHealthPractice() { //testing to see if the getHealthPractice method is returning the health practice correctly
        assertEquals(healthPractice1, booking1.getHealthPractice());
        assertEquals("Health practice name: Galway Family Practice\nHealth practice address: Tuam Road, Galway H56 RY85", booking1.getHealthPractice().toString());

    }



}
