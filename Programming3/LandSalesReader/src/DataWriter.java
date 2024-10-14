
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DataWriter {

    //static method to write the land data to the appropriate file
    public static void writeLandDataToFile(String filename, String landType, double landValue) {
        //open the file, write to it, and close it each time this method is called
        try (FileWriter fileWriter = new FileWriter(filename, true);  //open in append mode
             PrintWriter writer = new PrintWriter(fileWriter)) {

            //write formatted landType and landValue to the file
            writer.printf("%s %f%n", landType, landValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

