package ct326Assignment3;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

/**
 * The {@code Player} class represents a player in the system with attributes like player ID, username, country, represented by an enum,
 * join date, and a list of achievements. The class implements {@link Serializable} for object serialization
 * and includes mechanisms for custom serialization and deserialization of achievements.
 */
public class Player implements Serializable {
    private String playerId;
    private String username;
    private Country country;
    private LocalDate joinDate;
    private transient List<Achievements> achievements = new ArrayList<>();

    /**
     * Constructs a new {@code Player} instance with the specified attributes.
     *
     * @param playerId     The unique identifier for the player.
     * @param username     The username of the player.
     * @param country      The country associated with the player (as a string).
     * @param joinDate     The date the player joined.
     * @param achievements The list of achievements the player has earned.
     * @throws DuplicateAchievementException If an achievement is added more than once.
     * @throws InvalidDateException If the join date is in the future.
     * @throws IllegalArgumentException If any of the provided fields are null or an invalid country is passed
     */
    public Player(String playerId, String username, String country,LocalDate joinDate, List<Achievements> achievements) throws DuplicateAchievementException, InvalidDateException {
        if(playerId != null && username != null && country != null && joinDate != null && achievements != null) {
            this.playerId = playerId;
            this.username = username;

            setValidCountry(country);

            try {
                addDate(joinDate);
            } catch (InvalidDateException e) {
                throw e; //rethrow error to be handles elsewhere
            }
            for (Achievements a : achievements) {
                try {
                    addAchievement(a);
                } catch (DuplicateAchievementException e) {
                    throw e; //re throw exception to be handles elsewhere
                }
            }
        }
        else{
            throw new IllegalArgumentException("Fields cannot be null");
        }
    }

    /**
     * Returns the unique identifier of the player.
     *
     * @return The player's ID.
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Returns the username of the player.
     *
     * @return The player's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the country associated with the player.
     *
     * @return The player's country.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Returns the date the player joined.
     *
     * @return The join date.
     */
    public LocalDate getJoinDate() {
        return joinDate;
    }

    /**
     * Returns the list of achievements earned by the player.
     *
     * @return A list of {@code Achievements}.
     */
    public List<Achievements> getAchievements() {
        return achievements;
    }

    /**
     * Sets the country of the player after validating that it exists in the {@code Country} enum.
     *
     * @param country The country to set, provided as a string.
     * @throws IllegalArgumentException If the country is not valid.
     */
    private void setValidCountry(String country) {
        for (Country c : Country.values()) { //iterate through enum values
            if (c.name.equalsIgnoreCase(country)) { //compare strings, ignore case
                this.country = c; //assign variable
                return;
            }
        }
        throw new IllegalArgumentException("Invalid country: " + country); //if country is not in enum, throw exception
    }

    /**
     * Sets the join date of the player after validating that it is not in the future.
     *
     * @param date The join date to set.
     * @throws InvalidDateException If the date is in the future.
     */
    private void addDate(LocalDate date) throws InvalidDateException {
        if(!date.isAfter(LocalDate.now())) { //check if date is in future
            this.joinDate = date; //set joindate
        }
        else {
            throw new InvalidDateException("Date " + date + " cannot be added, join date cannot be in the future");
        }
    }

    /**
     * Adds an achievement to the player's list if it is not a duplicate.
     *
     * @param achievement The achievement to add.
     * @throws DuplicateAchievementException If the achievement already exists in the player's list.
     */
    //this addAchievement method was written to solve a problem of dupliate achievements data building up in the csv file after every serialization
    //this problem was solved by clearing the csv file in the test method, but i still left this method in anyway
    private void addAchievement(Achievements achievement) throws DuplicateAchievementException{
        if (!this.achievements.contains(achievement)) { //check to avoid adding duplicate achievements to the player's achievement array, using a custom equals method in the achievements class
            this.achievements.add(achievement); //add achievements to the player's achievement array if it is not a duplicate
        }
        else {
            throw new DuplicateAchievementException("Duplicate achievement " + achievement.getAchievementName());
        }
    }

    /**
     * Serializes the player object and writes achievements to a CSV file.
     * Uses defaultWriteObject to write the Player class, and custom logic to write out the Achievements
     * object to a csv file
     *
     * @param out The {@link ObjectOutputStream} used for writing.
     * @throws IOException If an I/O error occurs.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); //write all non-transient variables normally
        PrintWriter printOut = null; //define PrintWriter object
        try {
            printOut = new PrintWriter(new FileWriter("achievements.csv", true)); //append to true so file is not overitten
            for (Achievements a : achievements) { //print each achievement object on a new line, member variables seperated by commas
                printOut.printf("\n%s, %s, %s, %s", this.playerId, a.getAchievementName(), a.getDescription(), a.getAchievementDate());
            }
        } catch(IOException e) { //catch exceptions
            e.printStackTrace();
        } finally{
            if(printOut != null) {
                printOut.close();
            }
        }
    }

    /**
     * Deserializes the player object and reads achievements from a CSV file, reconstructing them into the player's list.
     *
     * @param in The {@link ObjectInputStream} used for reading.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class is not found during deserialization.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); //read in all non-transient variables normally

        Scanner lineScanner = null; //define linescanner
        BufferedReader bufferedIn = null; //define buffered reader
        if (this.achievements == null) {
            this.achievements = new ArrayList<>(); //if the achievements array is null(it should be), instansiate it
        }
        try {
            bufferedIn = new BufferedReader(new FileReader("achievements.csv")); //instansiate reader and direct to achievements csv file
            String line = null;
            bufferedIn.readLine(); //read in the achievements file

            while ((line = bufferedIn.readLine()) != null) { //loop while the current line isnt null (file hasnt ended)
                lineScanner = new Scanner(line); //instansiate scanner
                lineScanner.useDelimiter(","); //use scanner delimitter ','
                //define temp variables for reading in each line
                String id = null;
                String achievementName = null;
                String description = null;
                String achievementDate = null;

                //read in each variable and trim any whitespace
                id = lineScanner.next().trim();
                achievementName = lineScanner.next().trim();
                description = lineScanner.next().trim();
                achievementDate = lineScanner.next().trim();

                if (id.equals(this.playerId)) { //if the id assosiated with the achievement is the same as the playerId, the achievement belongs to that player
                    System.out.println(String.format("player Id %s being compared with achievement id %s", this.playerId, id)); //debugging
                    LocalDate date = LocalDate.parse(achievementDate); //create the LocalDate object
                    Achievements achievement = null; //make up the achievement object
                    try {
                        achievement = new Achievements(achievementName, description, date);
                    } catch (InvalidDateException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        //this addAchievement method was written to solve a problem of dupliate achievements data building up in the csv file after every serialization
                        //this problem was solved by clearing the csv file in the test method, but i still left this method in anyway
                        addAchievement(achievement); //achievement passed to method that will add achievement to achievement list if it's not a duplicate
                    } catch (DuplicateAchievementException e) { //no action needed if exceptions are caught
                    }
                }
            }
        } catch (IOException e) { //catch exceptions
            e.printStackTrace();
        } finally {
            if (bufferedIn != null) { //close streams if they were opened
                bufferedIn.close();
            }
            if (lineScanner != null) {
                lineScanner.close();
            }
        }
    }

    /**
     * Returns a string representation of the player object, including player ID, username, country, join date, and achievements.
     *
     * @return A formatted string containing player information.
     */
    public String toString() { //tostring method
        return String.format("id : %s\nusername : %s\ncountry : %s\njoin date : %s\nachievements : %s", this.playerId, this.username, this.country, this.joinDate, this.achievements);
    }
}
