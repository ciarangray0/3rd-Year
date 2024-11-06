import org.junit.jupiter.api.BeforeEach;
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
    List<UserAccount> userAccountsTest;
    UserAccount u1 = new UserAccount(12345, "ciaran", "ciarangray0@gmail.com");
    Workspace workspace1;
    Workspace workspace2;

    @BeforeEach
    public void setUp() {
        userAccountsTest = new ArrayList<>(); //array to hold the users
        //create new buffered reader
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/ciarangray/Desktop/3rdYear/Assignments/Programming3/Assignment4/src/main/resources/users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) { //while the line is not null
                String[] parts = line.split(", "); //split line by comma and store in array
                long userID = Long.parseLong(parts[0]);//store userID
                String name = parts[1]; //store name
                String email = parts[2]; //store email
                userAccountsTest.add(new UserAccount(userID, name, email)); //add new user to array
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        workspace2 = new Workspace("Workspace B", "another new project for this workspace", u1);
    }

    @Test
    public void testLoadUserAccountsFromCsv() throws IOException {
        List<UserAccount> userAccounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/ciarangray/Desktop/3rdYear/Assignments/Programming3/Assignment4/src/main/resources/users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                long userID = Long.parseLong(parts[0]);
                String name = parts[1];
                String email = parts[2];
                userAccounts.add(new UserAccount(userID, name, email));
            }
        }

        //test if 10 users were loaded
        assertEquals(10, userAccounts.size());

        for(UserAccount u : userAccounts) { //test all the users were created
            assertNotNull(u);
        }
    }

    @Test
    void testNaturalOrder() {
        //create two users, the first email address starts with 'f' and the second starts with 's' so firstUser should be registered first
        UserAccount firstUser = new UserAccount(244552, "ciaran", "firstemail@email.com");
        UserAccount secondUser = new UserAccount(7542, "cian", "secondemail@email.com");
        List<UserAccount> sortedUserTest = new ArrayList<>();
        //add seconduser first to avoid bias
        sortedUserTest.add(secondUser);
        sortedUserTest.add(firstUser);
        for (UserAccount u : sortedUserTest) {
            System.out.print(u);
        }
        assertEquals(secondUser, sortedUserTest.get(0)); //second user is first in list
        assertEquals(firstUser, sortedUserTest.get(1)); //first user is first
        Collections.sort(sortedUserTest); //sort them based on natural order (email address)
        for (UserAccount u : sortedUserTest) {
            System.out.print("\n" + u);
        }
        assertEquals(firstUser, sortedUserTest.get(0)); //now users should be sorted by natural order
        assertEquals(secondUser, sortedUserTest.get(1));
    }

    @Test
    void testUserAccountEquality() {
        //create 3 users, 2 have the same userID
        UserAccount u1 = new UserAccount(2673, "ciaran", "ciaran@ciaran.com");
        UserAccount u2 = new UserAccount(2673, "john", "john@john.com");
        UserAccount u3 = new UserAccount(387492, "ciaran", "ciaran@ciaran.com");
        assertTrue(u1.equals(u1)); //should be true regardless
        assertTrue(u1.equals(u2)); //users have the same userID and overrideen equals method returns true if two users have the same userID
        assertEquals(u1, u2);
        assertFalse(u1.equals(u3)); //incompatable userID's should return false
        assertNotEquals(u2, u3);
    }

    @Test
    void testHashCodeEquals() {
        //create two identical users
        UserAccount user1 = new UserAccount(244552, "ciaran", "ciaran@example.com");
        UserAccount user2 = new UserAccount(244552, "ciaran", "ciaran@example.com");

        //identical UserAccount objects should have the same hash code
        assertEquals(user1.hashCode(), user2.hashCode());

        int initialHashCode = user1.hashCode();
        assertEquals(initialHashCode, user1.hashCode());
        //call again to ensure consistency
        assertEquals(initialHashCode, user1.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        UserAccount user1 = new UserAccount(244552, "ciaran", "ciaran@example.com");
        UserAccount user2 = new UserAccount(7542, "cian", "cian@example.com");

        //assert that different UserAccount objects have different hash codes
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
        //create new buffered reader
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/ciarangray/Desktop/3rdYear/Assignments/Programming3/Assignment4/src/main/resources/users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) { //while the line is not null
                String[] parts = line.split(", "); //split line by comma and store in array
                long userID = Long.parseLong(parts[0]);//store userID
                String name = parts[1]; //store name
                String email = parts[2]; //store email
                userAccountsTest.add(new UserAccount(userID, name, email)); //add new user to array
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nUnsorted array: ");
        //print unsorted array
        for(UserAccount u : userAccountsTest) {
            System.out.println(u);
        }

        Collections.sort(userAccountsTest); //sort the array based on natural order, no comparator needed

        System.out.println("\nSorted array based on natural order: ");
        //print sorted array
        for(UserAccount u : userAccountsTest) {
            System.out.println(u);
        }

        //create an anonymous inner class using the comparator interface to sort the list based on userID in ascending order
        Comparator<UserAccount> comparatorAIC = new Comparator<UserAccount>() {
            @Override
            public int compare(UserAccount user1, UserAccount user2) {
                if(user1.getUserID() > user2.getUserID()) { //if user1 has a bigger userID then user2, return 1, meaning user1 should come after user2
                    return 1;
                }
                if(user1.getUserID() == user2.getUserID()) {
                    return 0; //return 0 if equal
                }
                else {
                    return -1; //last scenario is user1 has a smaller userID then user2, and user1 should come before user2
                }
            }
        };

        //sort array again, pass in the inner class comparator object to based on uid
        Collections.sort(userAccountsTest, comparatorAIC);
        System.out.println("\nSorted array based on UID in ascending order using anonymous inner class: ");
        for(UserAccount u : userAccountsTest) {
            System.out.println(u);
        }

        //lambda expression that sorts u2 ahead of u1 if u2's name is larger, by comparing the name strings
        //compareTo method from the String class compares lexicographical order, returns 1 if u2 is bigger than u1, 0 if the same and -1 if u1 is bigger than u2
        //as we want to sort in descending order, i compare u2 to u1 rather than u1 to u2
        Comparator<UserAccount> comparatorLE = (u1, u2) -> u2.getName().compareTo(u1.getName());

        //sort the list again based on the lambda expression comparator
        Collections.sort(userAccountsTest, comparatorLE);
        System.out.println("\nSorted array based on name in descending order using lambda expression: ");
        for(UserAccount u : userAccountsTest) {
            System.out.println(u);
        }

        //we will search for user linda garcia, who is at index 4 based off the natural order

        Collections.sort(userAccountsTest); //sort again by natural order
        //create a useraccount object that we will use as the key, we are just searching for the user with emailaddress as it is the natural order
        UserAccount searchKey = new UserAccount(0, "", "linda.garcia@example.com");
        int position = Collections.binarySearch(userAccountsTest, searchKey); //no need to pass in a comparator object as we're assuming the array is sorted based on natural order
        if(position < 0) {
            userAccountsTest.add(-position-1, searchKey); //if user is not found add it to the list (best practice for using binary search)
        }
        System.out.printf("\nSearching for position of user 'Linda garcia', expected index: 4, actual index: %d", position);
    }

    @Test
    public void testCreateWorkspace() {
        workspace1 = new Workspace("Project A", "new project for this workspace", u1);
        assertNotNull(workspace1);
        for(UserAccount u : userAccountsTest) {
            workspace1.addCollaborator(u);
        }
        assertEquals(10, workspace1.getCollaborators().size());
    }

    @Test
    void testToStringWithNoCollaborators() {
        //when no collaborators are added, we expect "None" to appear in the output
        String expected = "\nWorkspace Name: Workspace B, Collaborators: ";
        assertEquals(expected, workspace2.toString());
    }

    @Test
    void testToStringWithMultipleCollaborators() {
        for (UserAccount u : userAccountsTest) {
            workspace2.addCollaborator(u);
        }
        String expected = "\nWorkspace Name: Workspace B, Collaborators: Tim Stewart, Jane Doe, John Smith, Sarah Johnson, Robert Brown, Emily White, Michael Davis, Linda Garcia, David Wilson, Susan Martinez, ";
        assertEquals(expected, workspace2.toString());
    }
}
