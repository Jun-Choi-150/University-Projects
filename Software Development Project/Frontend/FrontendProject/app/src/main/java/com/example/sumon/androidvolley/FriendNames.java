package com.example.sumon.androidvolley;
/**
 * @author sabrinaf
 */
public class FriendNames {
    private String friendName;
    private int friendId;

    /**
     * constructor for movieNames
     * @param friendName string value of the movie name
     */
    public FriendNames(String friendName, int friendId) {
        this.friendName = friendName; this.friendId = friendId;
    }

    /**
     * returns the string value of the given friendName object
     * @return String value of the friendName object
     */
    public String getFriendName() {
        return this.friendName;
    }

    public int getFriendId() {return this.friendId; }
}
