import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user account with a unique ID, name, and email address.
 * This class is used for handling user account information and provides
 * comparison, equality, and hashing functionalities.
 */
public class UserAccount implements Comparable<UserAccount> {

    @JsonProperty
    private long userID;
    @JsonProperty
    private String name;
    @JsonProperty
    private String emailAddress;

    /**
     * Constructs a new UserAccount with the specified user ID, name, and email address.
     *
     * @param userID       The ID of the user.
     * @param name         The name of the user.
     * @param emailAddress The email address of the user.
     */
    @JsonCreator
    public UserAccount(@JsonProperty("userID") long userID, @JsonProperty("name") String name, @JsonProperty("emailAddress") String emailAddress) {
        this.userID = userID;
        this.name = name;
        this.emailAddress = emailAddress;
    }

    /**
     * Returns the user ID of this user account.
     *
     * @return the user ID.
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Sets the user ID for this user account.
     *
     * @param userID The new user ID.
     */
    public void setUserID(long userID) {
        this.userID = userID;
    }

    /**
     * Returns the name of this user account.
     *
     * @return the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for this user account.
     *
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email address of this user account.
     *
     * @return the email address.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email address for this user account.
     *
     * @param emailAddress The new email address.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Compares this user account to another user account by their email addresses.
     *
     * @param other The other UserAccount to compare to.
     * @return a negative integer, zero, or a positive integer as this email address
     * is less than, equal to, or greater than the specified email address.
     */
    @Override
    public int compareTo(UserAccount other) {
        return this.emailAddress.compareTo(other.emailAddress);
    }

    /**
     * Indicates whether some other object is "equal to" this one. Two user accounts are considered equal if their user IDs are the same.
     *
     *
     * @param obj The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserAccount)) return false;
        UserAccount other = (UserAccount) obj;
        return this.userID == other.userID;
    }

    /**
     * Returns a calculated unique hash code value for the object.
     *
     *
     * @return a hash code value for this user account.
     */
    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (int) userID;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        return result;
    }

    /**
     * Returns a string representation of this user account with user ID, name, and email address.
     *
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("UserAccount: id=%d, name='%s', email='%s'", userID, name, emailAddress);
    }
}


