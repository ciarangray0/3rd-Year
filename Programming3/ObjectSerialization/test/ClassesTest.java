import ct326Assignment3.Achievements;
import ct326Assignment3.*;
import ct326Assignment3.Player;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for testing the creation and behavior of serializable {@link Player} objects.
 * The {@link Player} class contains a list of {@link Achievements}, which is non-serializable.
 * This test class validates proper object creation, exception handling, and functionality of the {@link Player} and {@link Achievements} classes.
 */

/**
 * Constructor for the ClassesTest test class.
 * Initializes the {@link Achievements} and {@link Player} objects with valid data that will be used for some of the tests.
 * Handles any exceptions that might occur during object creation.
 */
public class ClassesTest {
    Player player2 = null;
    Achievements achievement1 = null;
    Achievements achievement1Copy = null;
    Achievements achievement2 = null;
    Achievements achievement3 = null;
    List<Achievements> achievementsList;

    public ClassesTest() {
        //instansiate achievements list and populate with objects
        achievementsList = new ArrayList<>();
        try {
            achievement1 = new Achievements("high score on COD", "high score on teamdeathmatch on call of duty", LocalDate.of(2023, 10, 14));
            achievement1Copy = new Achievements("high score on COD", "high score on teamdeathmatch on call of duty", LocalDate.of(2023, 10, 14));
            achievement2 = new Achievements("top 100 fut champs", "top 100 ranking on fifa fut champs", LocalDate.of(2022, 6, 12));
            achievement3 = new Achievements("top scorer in rocketleague ranked", "top scorer in a rocketleague ranked game", LocalDate.of(2021, 12, 25));
            achievementsList.add(achievement1);
            achievementsList.add(achievement2);
            achievementsList.add(achievement3);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }

        try {
            player2 = new Player("7382", "grayzer0", "japan", LocalDate.of(2012, 9, 22), achievementsList);
        } catch (DuplicateAchievementException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests the creation of valid {@link Achievements} objects.
     * Verifies that the objects are not null and correctly initialized.
     */
    @Test
    void testCreateAchievements() {
        //test to create proper achievements objects
        List<Achievements> testAchievementsList = new ArrayList<>();
        Achievements testAchievement1 = null;
        Achievements testAchievement2 = null;
        Achievements testAchievement3 = null;
        try {
            testAchievement1 = new Achievements("high score on COD", "high score on teamdeathmatch on call of duty", LocalDate.of(2023, 10, 14));
        } catch (InvalidDateException e) {
            fail();
        }
        assertNotNull(testAchievement1);
        testAchievementsList.add(testAchievement1);
        try {
            testAchievement2 = new Achievements("top 100 fut champs", "top 100 ranking on fifa fut champs", LocalDate.of(2022, 6, 12));
        } catch (InvalidDateException e) {
            fail();
        }
        assertNotNull(testAchievement2);
        testAchievementsList.add(testAchievement2);
        try {
            testAchievement3 = new Achievements("top scorer in rocketleague ranked", "top scorer in a rocketleague ranked game", LocalDate.of(2021, 12, 25));
        } catch (InvalidDateException e) {
            fail();
        }
        assertNotNull(testAchievement3);
        testAchievementsList.add(testAchievement3);
        assertNotNull(testAchievementsList);
        assertEquals(testAchievementsList.size(), 3);
    }

    /**
     * Tests the creation of invalid {@link Achievements} objects.
     * Verifies that appropriate exceptions are thrown for invalid data, including null fields and future dates.
     */
    @Test
    void testInvalidAchievementsCreation() {
        //test for invalid achievement creation

        assertThrows(IllegalArgumentException.class, () -> { //test for null achievement name
            new Achievements(null, "description", LocalDate.of(2023, 10, 14));
        });

        assertThrows(IllegalArgumentException.class, () -> { //test for null description
            new Achievements("achievement name", null, LocalDate.of(2023, 10, 14));
        });

        assertThrows(IllegalArgumentException.class, () -> { //test for null achievement date
            new Achievements("achievement name", "description", null);
        });

        assertThrows(InvalidDateException.class, () -> { //test for future achievement date
            new Achievements("future achievement", "description", LocalDate.of(2032, 10, 14));
        });
    }

    /**
     * Tests the creation of a valid {@link Player} object.
     * Ensures that the player is created with valid data and that the object is not null.
     */
    @Test
    void testCreatePlayer() {
        //test to create a player object
        Player player1 = null;
        try {
            player1 = new Player("234", "ciarangray0", "ireland", LocalDate.of(2022, 9, 23), achievementsList);
        } catch (DuplicateAchievementException e) {
            fail();
        } catch (InvalidDateException e) {
            fail();
        }
        catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(player1);
        System.out.println(player1);
    }

    /**
     * Tests invalid player creation with invalid data.
     * Ensures exceptions are thrown for invalid dates, usernames, player IDs, countries, and duplicate achievements.
     */
    @Test
    void testInvalidPlayerCreation () {
        assertThrows(InvalidDateException.class, () -> { //test exception throw for invalid date
           Player invalDateP = new Player("7382", "grayzer0", "japan", LocalDate.of(2032, 9, 22), achievementsList);
        });

        assertThrows(RuntimeException.class, () -> { //test exception throw for invalid username
            Player invaluserP = new Player("7382", null, "japan", LocalDate.of(2012, 9, 22), achievementsList);
        });

        assertThrows(RuntimeException.class, () -> { //test exception throw for invalid playerId
            Player invalIdP = new Player(null, "grayzer0", "japan", LocalDate.of(2012, 9, 22), achievementsList);
        });

        assertThrows(RuntimeException.class, () -> { //test exception throw for invalid country
            Player invalCountryP = new Player("7382", "grayzer0", "canada", LocalDate.of(2002, 9, 22), achievementsList);
        });

        List<Achievements> testAchievementsList = new ArrayList<>();
        testAchievementsList.add(achievement1);
        testAchievementsList.add(achievement1Copy);

        assertThrows(DuplicateAchievementException.class, () -> { //test exception throw for invalid achievement array
            Player invalAchievementP = new Player("7382", "grayzer0", "japan", LocalDate.of(2002, 9, 22), testAchievementsList);
        });
    }

    /**
     * Tests the {@link Player#toString()} method for correctness.
     * Verifies that the player's string representation matches the expected format.
     */
    @Test
    void testToString() {
        //test the player's toString method
        assertEquals("id : 7382\n" + "username : grayzer0\n" + "country : JAPAN\n" + "join date : 2012-09-22\n" + "achievements : [\n" + "achievement name : high score on COD\n" + "description : high score on teamdeathmatch on call of duty\n" + "achievement date : 2023-10-14\n" + ", \n" + "achievement name : top 100 fut champs\n" + "description : top 100 ranking on fifa fut champs\n" + "achievement date : 2022-06-12\n" + ", \n" + "achievement name : top scorer in rocketleague ranked\n" + "description : top scorer in a rocketleague ranked game\n" + "achievement date : 2021-12-25\n" + "]", player2.toString());
    }
}
