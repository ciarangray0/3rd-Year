import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserAccountCollectionTest {

    @Test
    void testNaturalOrder() {
        UserAccount firstUser = new UserAccount(244552, "ciaran", "firstemail@email.com");
        UserAccount secondUser = new UserAccount(7542, "cian", "secondemail@email.com");
        List<UserAccount> sortedUserTest = new ArrayList<>();
        sortedUserTest.add(secondUser);
        sortedUserTest.add(firstUser);
        for (UserAccount u : sortedUserTest) {
            System.out.print(u);
        }
        assertEquals(secondUser, sortedUserTest.get(0));
        assertEquals(firstUser, sortedUserTest.get(1));
        Collections.sort(sortedUserTest);
        for (UserAccount u : sortedUserTest) {
            System.out.print("\n" + u);
        }
        assertEquals(firstUser, sortedUserTest.get(0));
        assertEquals(secondUser, sortedUserTest.get(1));
    }

    @Test
    void testUserAccountEquality() {
        UserAccount u1 = new UserAccount(2673, "ciaran", "ciaran@ciaran.com");
        UserAccount u2 = new UserAccount(2673, "john", "john@john.com");
        UserAccount u3 = new UserAccount(387492, "ciaran", "ciaran@ciaran.com");
        assertTrue(u1.equals(u1));
        assertTrue(u1.equals(u2));
        assertEquals(u1, u2);
        assertFalse(u1.equals(u3));
        assertNotEquals(u2, u3);
    }

    @Test
    void testHashCodeConsistency() {
        UserAccount user1 = new UserAccount(244552, "ciaran", "ciaran@example.com");
        UserAccount user2 = new UserAccount(244552, "ciaran", "ciaran@example.com");

        // Assert that identical UserAccount objects have the same hash code
        assertEquals(user1.hashCode(), user2.hashCode());

        int initialHashCode = user1.hashCode();
        assertEquals(initialHashCode, user1.hashCode(), "hashCode() should return the same result consistently");
        assertEquals(initialHashCode, user1.hashCode(), "hashCode() should return the same result on repeated calls");
    }

    @Test
    void testHashCodeDifference() {
        UserAccount user1 = new UserAccount(244552, "ciaran", "ciaran@example.com");
        UserAccount user2 = new UserAccount(7542, "cian", "cian@example.com");

        // Assert that different UserAccount objects have different hash codes
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testFormattedToString() {
        UserAccount user1 = new UserAccount(244552, "ciaran", "ciaran@example.com");
        String expectedOutput = "UserAccount: id=244552, name='ciaran', email='ciaran@example.com'";
        assertEquals(user1.toString(), expectedOutput);
    }

    @Test
    void testSortedList() {
        List<UserAccount> userAccountsTest = new ArrayList<>(); //array to hold the users
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/ciarangray/Desktop/3rdYear/Assignments/Programming3/Assignment4/src/main/resources/users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                long userID = Long.parseLong(parts[0]);
                String name = parts[1];
                String email = parts[2];
                userAccountsTest.add(new UserAccount(userID, name, email));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nUnsorted array: ");
        //print unsorted array
        for(UserAccount u : userAccountsTest) {
            System.out.println(u);
        }

        Collections.sort(userAccountsTest); //sort the array based on natural order

        System.out.println("\nSorted array based on natural order: ");
        //print sorted array
        for(UserAccount u : userAccountsTest) {
            System.out.println(u);
        }
        Comparator<UserAccount> comparatorAIC = new Comparator<UserAccount>() {
            @Override
            public int compare(UserAccount user1, UserAccount user2) {
                if(user1.getUserID() > user2.getUserID()) {
                    return 1;
                }
                if(user1.getUserID() == user2.getUserID()) {
                    return 0;
                }
                else {
                    return -1;
                }
            }
        };

        Collections.sort(userAccountsTest, comparatorAIC); //sort array again, pass in the inner class comparator object to based on uid
        System.out.println("\nSorted array based on UID in ascending order using anonymous inner class: ");
        for(UserAccount u : userAccountsTest) {
            System.out.println(u);
        }

        //lambda expression that sorts u2 ahead of u1 if u2's name is bigger, by comparing the name strings
        Comparator<UserAccount> comparatorLE = (u1, u2) -> u2.getName().compareTo(u1.getName());

        Collections.sort(userAccountsTest, comparatorLE);
        System.out.println("\nSorted array based on name in descending order using lambda expression: ");
        for(UserAccount u : userAccountsTest) {
            System.out.println(u);
        }

        //we will search for user linda garcia, who is at index 4 based off the natural order

        Collections.sort(userAccountsTest); //sort again by natural order
        UserAccount searchKey = new UserAccount(0, "", "linda.garcia@example.com"); //create a useraccount object that we will use as the key, we are just searching for the user with emailaddress as it is the natural order
        int position = Collections.binarySearch(userAccountsTest, searchKey); //no need to pass in a comparator object as we're assuming the array is sorted based on natural order
        if(position < 0) {
            userAccountsTest.add(-position-1, searchKey);
        }
        System.out.printf("\nSearching for position of user 'Linda garcia', expected index: 4, actual index: %d", position);
    }
}
