import exceptions.UserDoesNotExistException;
import exceptions.UserIDAlreadyExistsException;

import java.util.Scanner;

public class ForumApp {
    Scanner scanner = null;
    Forum forum;
    public ForumApp() {
        scanner = new Scanner(System.in);
        forum = new Forum();
    }
    public static void main(String args[]) {
        ForumApp app = new ForumApp();
        app.runApp();
    }
    private void runApp() {
        boolean exit = false;
        while(exit == false) {
            System.out.println("Enter 1 to register a user, 2 to delete a user, 3 to make a post, or 4 to view the forum, or 5 to exit:");
            int choice = Integer.parseInt(scanner.next());
            Input input = Input.userChoice(choice);
            if (input == null) {
                System.out.println("invalid option, Enter 1 to register a user, 2 to delete a user, or 3 to make a post:");
            }
            switch (input) {
                case REGISTER_USER:
                    registerUser();
                    break;
                case DELETE_USER:
                    deleteUser();
                    break;
                case MAKE_POST:
                    makePost();
                    break;
                case VIEW_FORUM:
                    viewForum();
                    break;
                case EXIT:
                    System.out.println("Goodbye!");
                    exit = true;
            }
        }
    }

    private void viewForum() {
        forum.viewForum();
    }

    private void makePost() {
        boolean moveOn = false;
        while(!moveOn) {
            System.out.println("Enter user ID:");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter post title:");
            String title = scanner.nextLine();
            System.out.println("Enter post text:");
            String text = scanner.nextLine();
            ForumPost post = new ForumPost(title, text, id);
            try {
                forum.addPost(post);
            } catch (UserDoesNotExistException e) {
                System.out.println(e.getMessage());
            }
            if(forum.postExists(post)) {
                moveOn = true;
            }
        }
    }

    private void deleteUser() {
        boolean moveOn = false;
        while(!moveOn) {
            System.out.println("Enter user ID:");
            int id = Integer.parseInt(scanner.nextLine());
            try {
                forum.deleteUser(id);
            } catch (UserDoesNotExistException e) {
                System.out.println(e.getMessage());
            }
            if (forum.getUser(id) == null) {
                moveOn = true;
            }
        }

    }

    private void registerUser() {
        boolean moveOn = false;
        while(!moveOn) {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter user ID:");
            int id = Integer.parseInt(scanner.nextLine());
            Forum.User newUser = null;
            try {
               newUser  = forum.new User(id, username);
            } catch (UserIDAlreadyExistsException e) {
                System.out.println(e.getMessage());
            }
            if (newUser != null) {
                moveOn = true;
            }
        }
    }

}
