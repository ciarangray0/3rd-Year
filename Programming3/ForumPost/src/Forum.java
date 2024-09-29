import exceptions.UserDoesNotExistException;
import exceptions.UserIDAlreadyExistsException;

import java.util.ArrayList;

public class Forum {
    User user;
    ForumPost post;
    ArrayList<ForumPost> posts = new ArrayList<ForumPost>();
    ArrayList<User> users = new ArrayList<User>();

    public void deleteUser(int userId) throws UserDoesNotExistException {
        User deletedUser = getUser(userId);
        if(deletedUser != null) {
            users.remove(deletedUser);
        }
        else {
            throw new UserDoesNotExistException("user does not exist");
        }
    }
    public User getUser(int userId) {
        for(User user : users) {
            if(user.id == userId) {
                return user;
            }
        }
        return null;
    }

    public void addPost(ForumPost forumPost) throws UserDoesNotExistException {
        User user = getUser(forumPost.getUserID());
        if(user != null) {
            posts.add(forumPost);
        }
        else {
            throw new UserDoesNotExistException("user does not exist");
        }
    }

    public boolean postExists(ForumPost forumPost) {
        for(ForumPost post : posts) {
            if(post.equals(forumPost)) {
                return true;
            }
        }
        return false;
    }

    public void viewForum() {
        for(ForumPost post : posts) {
            System.out.println(post.toString());
        }
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
