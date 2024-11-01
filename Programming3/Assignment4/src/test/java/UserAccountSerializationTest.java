import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserAccountSerializationTest {


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

        // Test if 10 users were loaded
        assertEquals(10, userAccounts.size());
    }
    @Test
    void testSerializeToJSON() {
        List<UserAccount> testUsers = UserAccountManager.loadUserAccountsFromCSV("/Users/ciarangray/Desktop/3rdYear/Assignments/Programming3/Assignment4/src/main/resources/users.csv");
        UserAccountManager.serializeToJSON(testUsers, "testUsers.json");
        assertTrue(new File("testUsers.json").exists());
    }

    @Test
    void testDeserializeFromJSON() {
        List<UserAccount> testUsers = UserAccountManager.loadUserAccountsFromCSV("/Users/ciarangray/Desktop/3rdYear/Assignments/Programming3/Assignment4/src/main/resources/users.csv");
        UserAccountManager.serializeToJSON(testUsers, "testUsers.json");  // Serialize first
        List<UserAccount> deserializedUsers = UserAccountManager.deserializeFromJSON("testUsers.json");
        assertNotNull(deserializedUsers);
        assertEquals(testUsers.size(), deserializedUsers.size());
        assertEquals(testUsers.get(0).getEmailAddress(), deserializedUsers.get(0).getEmailAddress());
    }
}
