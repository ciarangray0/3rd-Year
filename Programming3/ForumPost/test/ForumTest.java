import exceptions.UserDoesNotExistException;
import exceptions.UserIDAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ForumTest {
    private Forum forum1;
    public ForumTest() {
        forum1 = new Forum();
    }
    @Test
    void registerValidUser() {
        Forum.User user1 = null;
        try {
            user1 = forum1.new User(1, "ciaran");
        } catch (UserIDAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        assertNotNull(user1);
    }

    @Test
    void registerUserWithSameID() {
        Forum.User user1 = null;
        try {
            user1 = forum1. new User(1, "ciaran");
        } catch (UserIDAlreadyExistsException e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(user1);
        assertThrows(UserIDAlreadyExistsException.class, () -> {
            forum1.new User(1, "john");
        });
    }

    @Test
    void deleteUserFromForum() {
        Forum.User user1 = null;
        try {
            user1 = forum1. new User(1, "ciaran");
        } catch (UserIDAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        assertNotNull(user1);
        assertTrue(forum1.isUser(user1));
        try {
            forum1.deleteUser(user1);
        } catch (UserDoesNotExistException e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertFalse(forum1.isUser(user1));
    }


}
