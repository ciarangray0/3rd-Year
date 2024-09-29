import exceptions.InvalidTimestampException;
import exceptions.UserDoesNotExistException;
import exceptions.UserIDAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
        assertNotNull(forum1.getUser(1));
        try {
            forum1.deleteUser(1);
        } catch (UserDoesNotExistException e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertNull(forum1.getUser(1));
    }
    @Test
    void addForumPost() {
        Forum.User user1;
        try {
            user1 = forum1.new User(1, "ciaran");
        } catch (UserIDAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        ForumPost forumPost1 = new ForumPost("New post", "This is a test post", 1);

        try {
            forum1.addPost(forumPost1);
        } catch (UserDoesNotExistException e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertTrue(forum1.postExists(forumPost1));
    }
    @Test
    void addInvalidForumPost() {
        ForumPost forumPost1 = new ForumPost("New post", "This is a test post", 4);
        assertThrows(UserDoesNotExistException.class, () -> {
            forum1.addPost(forumPost1);
        });
    }


}
