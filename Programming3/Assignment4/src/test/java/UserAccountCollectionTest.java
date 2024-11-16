import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserAccount and Workspace functionality.
 * Tests the loading of user accounts from a CSV file,
 * natural ordering of user accounts, equality and hash code implementation,
 * and adding users and assosiated workspaces to a hashmap.
 */
public class UserAccountCollectionTest {
    List<UserAccount> userAccountsTest;
    UserAccount u1 = new UserAccount(12345, "ciaran", "ciarangray0@gmail.com");
    Workspace workspace1;
    Workspace workspace2;

    /**
     * Sets up the test environment by loading user accounts from a CSV file
     * and creating a sample workspace instance.
     */
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

    /**
     * Tests if user accounts are correctly loaded from a CSV file.
     * Verifies that 10 users are loaded, as there are 10 users in the csv file and checks that each user is not null.
     */
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

    /**
     * Tests the natural ordering of UserAccount instances by email.
     * Sorts two users and checks that the list is sorted by natural order (email).
     */
    @Test
    void testNaturalOrder() {
        //create two users, the first email address starts with 'f' and the second starts with 's' so firstUser should be registered first
        UserAccount firstUser = new UserAccount(244552, "ciaran", "firstemail@email.com");
        UserAccount secondUser = new UserAccount(7542, "cian", "secondemail@email.com");
        List<UserAccount> sortedUserTest = new ArrayList<>();
        //add seconduser first to avoid bias
        sortedUserTest.add(secondUser);
        sortedUserTest.add(firstUser);
        System.out.println("\nUnsorted by natural order: \n");
        for (UserAccount u : sortedUserTest) {
            System.out.print(u);
            System.out.println("\n");
        }
        assertEquals(secondUser, sortedUserTest.get(0)); //second user is first in list
        assertEquals(firstUser, sortedUserTest.get(1)); //first user is first
        Collections.sort(sortedUserTest); //sort them based on natural order (email address)
        System.out.println("\nSorted by natural order: \n");
        for (UserAccount u : sortedUserTest) {
            System.out.print("\n" + u);
        }
        assertEquals(firstUser, sortedUserTest.get(0)); //now users should be sorted by natural order
        assertEquals(secondUser, sortedUserTest.get(1));
    }

    /**
     * Tests the equality of UserAccount instances.
     * Verifies that users with the same ID are considered equal.
     */
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

    /**
     * Tests the hashCode method for UserAccount.
     * Verifies that identical UserAccount instances have the same hash code.
     */
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

    /**
     * Tests that two different UserAccount instances have different hash codes.
     */
    @Test
    void testHashCodeDifference() {
        UserAccount user1 = new UserAccount(244552, "ciaran", "ciaran@example.com");
        UserAccount user2 = new UserAccount(7542, "cian", "cian@example.com");

        //assert that different UserAccount objects have different hash codes
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    /**
     * Tests the toString method for UserAccount to ensure proper formatting.
     */
    @Test
    void testFormattedToString() {
        UserAccount user1 = new UserAccount(244552, "ciaran", "ciaran@example.com");
        String expectedOutput = "UserAccount: id=244552, name='ciaran', email='ciaran@example.com'";
        assertEquals(user1.toString(), expectedOutput);
    }

    /**
     * Tests sorting the userAccounts list based on different criteria:
     * natural order, UID, and name in descending order.
     * Also, performs a binary search for a specific user.
     */
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
        System.out.printf("\nSearching for position of user 'Linda garcia', expected index: 7, actual index: %d", position);
    }

    /**
     * Tests creating a new workspace and adding collaborators to it.
     * Verifies that the workspace is created and collaborators are added.
     */
    @Test
    public void testCreateWorkspace() {
        workspace1 = new Workspace("Project A", "new project for this workspace", u1);
        assertNotNull(workspace1);
        for(UserAccount u : userAccountsTest) {
            workspace1.addCollaborator(u);
        }
        assertEquals(10, workspace1.getCollaborators().size());
    }

    /**
     * Tests the toString method for a Workspace with no collaborators.
     * Verifies that the output matches the expected string format.
     */
    @Test
    void testToStringWithNoCollaborators() {
        //expected string when no collaborators are added
        String expected = "\nWorkspace Name: Workspace B, Collaborators: ";
        assertEquals(expected, workspace2.toString());
    }


    /**
     * Tests the toString method for a Workspace with multiple collaborators.
     * Verifies that the output matches the expected string format with all collaborator names.
     */
    @Test
    void testToStringWithMultipleCollaborators() {
        for (UserAccount u : userAccountsTest) {
            workspace2.addCollaborator(u); //add all users to this workspace
        }
        String expected = "\nWorkspace Name: Workspace B, Collaborators: Ciaran Gray, Jane Doe, John Doe, Lionel Messi, Bob Duncan, Emily White, Michael Davis, Linda Garcia, Conor McGregor, Xians";
        assertEquals(expected, workspace2.toString());
    }

    /**
     * Tests creating and storing workspaces for different users in a Map.
     * Verifies that workspaces are correctly added and stored under each user.
     */
    @Test
    void testUserAccountWorkspaceMap() {
        Map<UserAccount, ArrayList<Workspace>> map = new HashMap<>();
        //populate the map, add in an empty arraylist as the element for each key
        for(int i = 0; i < 10; i++) {
            map.put(userAccountsTest.get(i), new ArrayList<Workspace>());
        }
        //create a new workspace, with the user at position 0 in the map as the owner
        Workspace user0Workspace = new Workspace("User 0's workSpace from map", "first workspace for user at postion 0", userAccountsTest.get(0)); //set the owner as user at position 0 in the list
        //add collaborators
        user0Workspace.addCollaborator(userAccountsTest.get(4));
        user0Workspace.addCollaborator(userAccountsTest.get(7));
        //retireive the workspace arraylist from user0
        ArrayList<Workspace> user0WorkspaceArrayList = map.get(userAccountsTest.get(0));
        //add the workspace to the array
        user0WorkspaceArrayList.add(user0Workspace);
        //update arraylist
        map.put(userAccountsTest.get(0), user0WorkspaceArrayList);

        System.out.println("\nUser 0's workspaces\n" + map.get(userAccountsTest.get(0)));
    }
}
