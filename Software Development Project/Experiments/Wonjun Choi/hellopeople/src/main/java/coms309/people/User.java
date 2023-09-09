package coms309.people;

/**
 *
 * @author Wonjun Choi
 */

public class User {


	// The user's first name.
    private String firstName;

	// The user's last name.
    private String lastName;

    // The user's ID
    private String userID;

    // The user's password
    private String password;

    public User(){
        
    }

    public User(String firstName, String lastName, String userID, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return firstName + " " 
               + lastName + " "
               + userID + " "
               + password;
    }
}
