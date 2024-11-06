import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccount implements Comparable<UserAccount> {
    @JsonProperty
    private long userID;
    @JsonProperty
    private String name;
    @JsonProperty
    private String emailAddress;


    @JsonCreator
    public UserAccount(@JsonProperty("userID") long userID, @JsonProperty("name") String name, @JsonProperty("emailAddress") String emailAddress) {
        this.userID = userID;
        this.name = name;
        this.emailAddress = emailAddress;
    }

    // Getters and Setters
    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    @Override
    public int compareTo(UserAccount other) {
        return this.emailAddress.compareTo(other.emailAddress);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) { //return true if same objects are being compared
            return true;
        }
        if(!(obj instanceof UserAccount)) { //if the object is not an instance of userAccount return false
            return false;
        }
        UserAccount other = (UserAccount) obj; //cast object to UserAccount
        return this.userID == other.userID; //compare userId's
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (int)userID;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return String.format("UserAccount: id=%d, name='%s', email='%s'", userID, name, emailAddress);
    }
}

