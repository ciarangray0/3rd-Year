package ct326Assignment3;

import java.time.LocalDate;

/**
 * The {@code Achievements} class represents an achievement earned by a player,
 * with attributes like name, description, and achievement date.
 */
public class Achievements {
    private String achievementName;
    private String description;
    private LocalDate achievementDate;

    /**
     * Constructs a new {@code Achievements} instance with the specified name, description, and date.
     *
     * @param achievementName The name of the achievement.
     * @param description     The description of the achievement.
     * @param achievementDate The date the achievement was earned.
     * @throws InvalidDateException If the achievement date is in the future.
     * @throws IllegalArgumentException If any of the provided fields are null.
     */
    public Achievements(String achievementName, String description, LocalDate achievementDate) throws InvalidDateException {
        if( achievementName != null && description != null && achievementDate != null) {
            this.achievementName = achievementName;
            this.description = description;
            try {
                addDate(achievementDate);
            } catch (InvalidDateException e) {
                throw e;
            }
        }
        else {
            throw new IllegalArgumentException("Fields cannot be null");
        }

    }

    /**
     * Sets the achievement date after validating that it is not in the future.
     *
     * @param date The achievement date to set.
     * @throws InvalidDateException If the date is in the future.
     */
    private void addDate(LocalDate date) throws InvalidDateException {
        if(!date.isAfter(LocalDate.now())) { //check if date is in future
            this.achievementDate = date; //set achievementdate
        }
        else {
            throw new InvalidDateException("Date " + date + " cannot be added, achievement date cannot be in the future");
        }
    }

    /**
     * Returns the name of the achievement.
     *
     * @return The achievement name.
     */
    public String getAchievementName() {
        return achievementName;
    }

    /**
     * Returns the description of the achievement.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the date the achievement was earned.
     *
     * @return The achievement date.
     */
    public LocalDate getAchievementDate() {
        return achievementDate;
    }

    /**
     * Compares this achievement to another object for equality.
     *
     * @param obj The object to compare.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    //this equals method was written to compare achievements to solve a problem of dupliate achievements data building up in the csv file after every serialization
    //this problem was solved by clearing the csv file in the test method, but i still left this method in anyway
    @Override
    public boolean equals(Object obj) { //custom equals method to compare achievements objects
        if (this == obj) {
            return true; //if comparing the same objects
        }
        if (!(obj instanceof Achievements)) {
            return false; //if object we are attempting to compare isn't an achievements object
        }

        Achievements other = (Achievements) obj; //cast the object to an Achievements object
        return this.achievementName.equals(other.achievementName) && this.description.equals(other.description) && this.achievementDate.equals(other.achievementDate);
    }

    /**
     * Returns a string representation of the achievement object.
     *
     * @return A formatted string containing the achievement details.
     */
    public String toString() { //achievements toString method
        return String.format("\nachievement name : %s\ndescription : %s\nachievement date : %s\n", this.achievementName, this.description, this.achievementDate);
    }
}
