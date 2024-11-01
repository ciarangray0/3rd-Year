import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
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
        for(UserAccount u : sortedUserTest) {
            System.out.print(u);
        }
        assertEquals(secondUser, sortedUserTest.get(0));
        assertEquals(firstUser, sortedUserTest.get(1));
        Collections.sort(sortedUserTest);
        for(UserAccount u : sortedUserTest) {
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
}
