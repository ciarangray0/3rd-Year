import ct326Assignment3.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class contains multiple test cases for verifying the serialization and deserialization
 * of Player objects and ensuring the integrity of data during these processes. It uses a list of
 * Player objects with associated Achievements to perform these tests.
 */

public class SerializeTest {
    Player p1 = null;
    Player p2 = null;
    Player p3 = null;
    Player p4 = null;
    Player p5 = null;
    List<Player> playersList;
    Achievements achievement1 = null;
    Achievements achievement2 = null;
    Achievements achievement3 = null;
    Achievements achievement4 = null;
    Achievements achievement5 = null;
    Achievements achievement6 = null;
    List<Achievements> achievementsList1;
    List<Achievements> achievementsList2;

    /**
     * Constructs a new instance of the SerializeTest class. This constructor is responsible for
     * initializing test data, including:
     * - A list of Player objects (`playersList`), each with associated Achievements.
     * - Two lists of Achievements (`achievementsList1` and `achievementsList2`) with different achievement
     *   data for each player.
     *
     * The Achievements and Player objects are instantiated with predefined data, and any potential
     * exceptions such as `InvalidDateException` or `DuplicateAchievementException` are handled.
     *
     * The following data is initialized:
     * - Achievements include a variety of achievements earned in different games, such as "high score on COD"
     *   and "defeated the ender dragon".
     * - Players include different individuals with unique IDs, usernames, countries, and a list of achievements
     *   associated with each player.
     */
    public SerializeTest()  { //instansiate achievementList and achievements objects
        achievementsList1 = new ArrayList<>();
        try {
            achievement1 = new Achievements("high score on COD", "high score on teamdeathmatch on call of duty", LocalDate.of(2023, 10, 14));
            achievementsList1.add(achievement1);

            achievement2 = new Achievements("top 100 fut champs", "top 100 ranking on fifa fut champs", LocalDate.of(2022, 6, 12));
            achievementsList1.add(achievement2);

            achievement3 = new Achievements("top scorer in rocketleague ranked", "top scorer in a rocketleague ranked game", LocalDate.of(2021, 12, 25));
            achievementsList1.add(achievement3);

            achievementsList2 = new ArrayList<>();
            achievement4 = new Achievements("fifa tournament winner", "winner of the fifa world cup tournament 2021", LocalDate.of(2021, 6, 9));
            achievementsList2.add(achievement4);

            achievement5 = new Achievements("defeated the ender dragon", "slayed the ender dragon in minecraft", LocalDate.of(2014, 2, 15));
            achievementsList2.add(achievement5);

            achievement6 = new Achievements("platinum award for heist", "completed a gta5 heist without any deaths", LocalDate.of(2020, 5, 29));
            achievementsList2.add(achievement6);

        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }

        playersList = new ArrayList<>(); //instansiate playerList and populate with 5 player objects
        try {
            p1 = new Player("7382", "grayzer0", "japan", LocalDate.of(2012, 9, 22), achievementsList1);
            p2 = new Player("134", "johndunne123", "germany", LocalDate.of(2001, 12, 25), achievementsList1);
            p3 = new Player("99999", "surejack2003", "morrocco", LocalDate.of(2014, 4, 12), achievementsList2);
            p4 = new Player("14", "adamb109", "spain", LocalDate.of(2018, 1, 1), achievementsList1);
            p5 = new Player("6554", "lightingGames0", "england", LocalDate.of(2013, 3, 10), achievementsList2);
            playersList.add(p1);
            playersList.add(p2);
            playersList.add(p3);
            playersList.add(p4);
            playersList.add(p5);
        } catch (DuplicateAchievementException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests the serialization of the Player objects in the playersList to a .ser file and ensures
     * that the serialized file is correctly created. It also deserializes the Player objects from
     * the .ser file and validates that the deserialized objects maintain the original structure and data.
     *
     * Steps:
     * 1. Serialize the playersList to a file.
     * 2. Check if the serialized file and the CSV file for achievements exist.
     * 3. Deserialize the playersList from the file.
     * 4. Validate that the deserialized objects match the original Player objects.
     */
    @Test
    void playerSerializationTest() {
        //declare input/output streams
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            new FileWriter("achievements.csv", false).close(); //before every serialization, i am clearing the csv file to avoid duplicate data
            oos = new ObjectOutputStream(new BufferedOutputStream((new FileOutputStream("players.ser")))); //instansiate output stream
            oos.writeObject(playersList); //write the playersList to the players.ser file
            oos.close(); //close stream

            File playersSerFile = new File("players.ser");
            assertTrue(playersSerFile.exists()); //assert file was created

            File achievementsCSVFile = new File("achievements.csv");
            assertTrue(achievementsCSVFile.exists()); //assert file was created

            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("players.ser"))); //instansiate a new input stream from the ser file

            List<?> deserializedPlayers = (List<?>) ois.readObject(); //use generics to define the list that will hold the deserialized objects of an unknown type

            assertNotNull(deserializedPlayers); //assert the list is not null
            assertEquals(playersList.size(), deserializedPlayers.size()); //check that the deserialized list contains the same amount of objects in the original list of players
            for (Object obj : deserializedPlayers) {
                assertTrue(obj instanceof Player); //assert that the objects in the deserialized objects array are Player objects
                Player player = (Player) obj;  //cast the objects to Player objects
                assertNotNull(player);
                System.out.println(player);
            }
        } catch (FileNotFoundException e) { //catch errors
           fail();
        } catch (IOException e) {
            fail();
        } catch (ClassNotFoundException e) {
           fail();
        } finally {
            try { //close input/output streams
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                fail();
            }
        }
    }

    /**
     * Tests the integrity of the serialized Player objects by comparing the original and deserialized
     * Player objects' properties. This ensures that the Player data (playerId, username, country, joinDate,
     * and achievements) remains consistent before and after serialization.
     *
     * Steps:
     * 1. Serialize the playersList to a .ser file.
     * 2. Deserialize the list of players.
     * 3. Compare each Player object's fields to ensure that the data remains the same.
     */
    @Test
    void playerSerializationIntegrityTest() {
        //declare input/output streams
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            new FileWriter("achievements.csv", false).close(); //before every serialization, i am clearing the csv file to avoid duplicate data
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("playersIntegrity.ser")));
            oos.writeObject(playersList); //write the playersList to the ser
            oos.close(); //close stream

            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("playersIntegrity.ser"))); // Instantiate a new input stream from the ser file
            List<?> deserializedPlayers = (List<?>) ois.readObject(); //deserialize the player list

            for (int i = 0; i < playersList.size(); i++) { //loop through both arrays and compare original data with deserialized data
                Player originalPlayer = playersList.get(i);
                Player deserializedPlayer = (Player) deserializedPlayers.get(i);

                assertEquals(originalPlayer.getPlayerId(), deserializedPlayer.getPlayerId());
                assertEquals(originalPlayer.getUsername(), deserializedPlayer.getUsername());
                assertEquals(originalPlayer.getCountry(), deserializedPlayer.getCountry());
                assertEquals(originalPlayer.getJoinDate(), deserializedPlayer.getJoinDate());
                assertEquals(originalPlayer.getAchievements(), deserializedPlayer.getAchievements());
            }
        } catch (FileNotFoundException e) {
            fail();
        } catch (IOException e) {
            fail();
        } catch (ClassNotFoundException e) {
            fail();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                fail();
            }
        }
    }

    /**
     * Tests the serialization of an empty Player list to verify that the serialization process works
     * even when no Player objects are present. It ensures that the file is created and deserialization
     * of an empty list functions correctly.
     *
     * Steps:
     * 1. Serialize an empty playersList to a .ser file.
     * 2. Check if the file is created.
     * 3. Deserialize the empty playersList.
     * 4. Validate that the deserialized list is not null and that it is empty.
     */
    @Test
    void emptyPlayerListSerializationTest() {
        List<Player> emptyPlayersList = new ArrayList<>();

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            new FileWriter("achievements.csv", false).close(); //before every serialization, i am clearing the csv file to avoid duplicate data
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("emptyPlayers.ser")));
            oos.writeObject(emptyPlayersList); //write the empty players list to the emptyPlayers.ser file
            oos.close(); //close stream

            File emptyPlayersSerFile = new File("emptyPlayers.ser");
            assertTrue(emptyPlayersSerFile.exists()); //assert file was created

            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("emptyPlayers.ser"))); // Instantiate a new input stream from the ser file
            List<?> deserializedEmptyPlayers = (List<?>) ois.readObject(); //usage of generics to define the list that will hold the deserialized (player) objects

            assertNotNull(deserializedEmptyPlayers); //assert the list is not null
            assertEquals(0, deserializedEmptyPlayers.size()); //check that the deserialized list is empty

        } catch (FileNotFoundException e) {
            fail();
        } catch (IOException e) {
            fail();
        } catch (ClassNotFoundException e) {
            fail();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                fail();
            }
        }
    }


}
