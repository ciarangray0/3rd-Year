
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataWriterTest {

    @Test
    public void testWriteLandDataToFile() throws IOException {
        String testFile = "test_mideast.txt";
        String expectedLine = "Permanent Grassland 26616120.700000";

        //write test data to the file
        DataWriter.writeLandDataToFile(testFile, "Permanent Grassland", 26616120.7);

        //try to read the file and verify the contents
        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            String line = reader.readLine();
            assertEquals(expectedLine, line);
        }
    }
}
