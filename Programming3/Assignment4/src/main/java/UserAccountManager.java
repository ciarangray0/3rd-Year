import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserAccountManager {

    public static void main(String[] args) {
        //load user accounts from CSV
        List<UserAccount> userAccounts = loadUserAccountsFromCSV("/Users/ciarangray/Desktop/3rdYear/Assignments/Programming3/Assignment4/src/main/resources/users.csv");

        //serialize user accounts to JSON
        serializeToJSON(userAccounts, "users.json");

        //deserialize user accounts from JSON
        List<UserAccount> deserializedAccounts = deserializeFromJSON("users.json");
        deserializedAccounts.forEach(System.out::println);
    }

    public static List<UserAccount> loadUserAccountsFromCSV(String filePath) {
        List<UserAccount> userAccounts = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] attributes = line.split(",");
                long userID = Long.parseLong(attributes[0].trim());
                String name = attributes[1].trim();
                String email = attributes[2].trim();
                userAccounts.add(new UserAccount(userID, name, email));
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return userAccounts;
    }

    //method to serialize list of user accounts to JSON
    public static void serializeToJSON(List<UserAccount> userAccounts, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); //pretty printing
        try {
            mapper.writeValue(new File(filePath), userAccounts);
            System.out.println("User accounts serialized to " + filePath);
        } catch (IOException e) {
            System.out.println("Error serializing to JSON: " + e.getMessage());
        }
    }

    //deserialize json file to list of user accounts
    public static List<UserAccount> deserializeFromJSON(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        List<UserAccount> userAccounts = new ArrayList<>();
        try {
            UserAccount[] usersArray = mapper.readValue(new File(filePath), UserAccount[].class);
            userAccounts = List.of(usersArray);
            System.out.println("User accounts deserialized from " + filePath);
        } catch (IOException e) {
            System.out.println("Error deserializing from JSON: " + e.getMessage());
        }
        return userAccounts;
    }
}
