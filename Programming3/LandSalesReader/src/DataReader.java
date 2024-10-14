import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DataReader {

    public static void main(String[] args) {
        String filePath = "/Users/ciarangray/Downloads/lab2 (1).csv";  //path to your CSV file

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            //skip the first line, which contains the headers
            reader.readLine();

            //read each line from the CSV file
            while ((line = reader.readLine()) != null) {
                //check if the line is empty and skip it if necessary
                if (line.trim().isEmpty()) {
                    continue;  //skip empty lines
                }

                try {
                    //use a Scanner to parse the line with a comma delimiter
                    Scanner scanner = new Scanner(line).useDelimiter(",");

                    //ensure that there are at least 3 tokens in the line
                    if (scanner.hasNext()) {
                        String landType = scanner.next();  // First column: land type
                        String regionStr = scanner.next(); // Second column: region
                        //check if the next token is a double (land value)
                        if (scanner.hasNextDouble()) {
                            double landValue = scanner.nextDouble(); // Third column: land value

                            //convert the region string to the Region enum
                            Region region = Region.getRegionFromString(regionStr);

                            //use a switch statement to determine which file to write to
                            String filename = "";

                            switch (region) {
                                case MID_EAST:
                                    filename = "mideast.txt";
                                    break;
                                case MID_WEST:
                                    filename = "midwest.txt";
                                    break;
                                case DUBLIN:
                                    filename = "dublin.txt";
                                    break;
                                case SOUTH_EAST:
                                    filename = "southeast.txt";
                                    break;
                                case SOUTH_WEST:
                                    filename = "southwest.txt";
                                    break;
                                case WEST:
                                    filename = "west.txt";
                                    break;
                                case BORDER:
                                    filename = "border.txt";
                                    break;
                                case MIDLAND:
                                    filename = "midland.txt";
                                    break;
                                case STATE:
                                    filename = "state.txt";
                                    break;
                                default:
                                    throw new IllegalArgumentException("Unknown region: " + region);
                            }

                            //write the data to the appropriate file (open and close each time)
                            DataWriter.writeLandDataToFile(filename, landType, landValue);
                        } else {
                            System.err.println("Invalid land value for line: " + line);
                        }
                    }
                } catch (Exception e) {
                    //exceptions that may occur while parsing the line
                    System.err.println("Error processing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
