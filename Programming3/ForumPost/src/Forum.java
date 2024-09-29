import exceptions.UserDoesNotExistException;
import exceptions.UserIDAlreadyExistsException;

import java.util.ArrayList;

public class Forum {
    User user;
    ForumPost post;
    ArrayList<ForumPost> posts = new ArrayList<ForumPost>();
    ArrayList<User> users = new ArrayList<User>();

    public void deleteUser(User deletedUser) throws UserDoesNotExistException {
        if(isUser(deletedUser)) {
            users.remove(deletedUser);
        }
        else {
            throw new UserDoesNotExistException("user does not exist");
        }
    }
    public boolean isUser(User estUser) {
        for(User user : users) {
            if(user.equals(estUser)) {
                return true;
            }
        }
        return false;
    }

    class User{
        private int id;
        private String username;
        public User(int id, String username) throws UserIDAlreadyExistsException {
            if(newUserId(id)) {
                users.add(this);
                this.id = id;
                this.username = username;
            }
            else {
                throw new UserIDAlreadyExistsException("user already exists");
            }
        }

        public boolean newUserId(int id) {
            for(User user : users) {
                if (user.id == id) {
                    return false;
                }
            }
            return true;
        }
    }
}
