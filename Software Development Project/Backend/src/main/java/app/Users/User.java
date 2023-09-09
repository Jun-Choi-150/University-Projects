package app.Users;

import java.net.URL;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import app.Messages.Message;
import app.Permissions.Permission;
import app.Posts.Post;

/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */ 

@Entity
@Table(name="user")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class,
		  property = "id")
public class User {

	/*
	 * Fields
	 */
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="emailID")
    private String emailId;
    @Column(name="profilePic")
    private URL profilePic;

    /*
     * Relations
     */
    
    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name = "Friends_list",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "friend_id"))
    @JsonSerialize(using = FriendSerializer.class)
    private Set<User> friends;
    
    @OneToMany(mappedBy = "user",  cascade = CascadeType.REMOVE)
    private Set<Post> post;
       
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Message> sentMessages;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private Set<Message> receivedMessages;
        
    @ManyToOne
    @JoinColumn(name = "permission_id")
    @JsonIgnore
    private Permission permission;


    // =============================== Constructors ================================== //


    public User(String name, String username, String password, String emailId, URL profilePic) {
        this.name = name;
        this.emailId = emailId;
        this.password = password;
        this.username = username;
        this.profilePic = profilePic;
        post = new HashSet<>();
        sentMessages = new HashSet<>();
        receivedMessages = new HashSet<>();
        friends = new HashSet<>();
    }

    public User() {
        post = new HashSet<>();
        sentMessages = new HashSet<>();
        receivedMessages = new HashSet<>();
        friends = new HashSet<>();
    }

    
    // =============================== Getters and Setters for each field ================================== //
    
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmailId(){
        return emailId;
    }

    public void setEmailId(String emailId){
        this.emailId = emailId;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
    
    public URL getProfilePic(){
        return profilePic;
    }

    public void setProfilePic(URL profilePic){
        this.profilePic = profilePic;
    }

    //Relations
    
    public Set<User> getFriends() {
        return friends;
    }
    
    public void setFriends(Set<User> friends){
        this.friends = friends;
    }
    
    public Permission getPermission(){
		return permission;
    }

    public void setPermission(Permission permission){
        this.permission = permission;
    }
    
    public Set<Post> getPost() {
        return post;
    }
    
    public void setPost(Set<Post> post){
        this.post = post;
    }

    public void addPost(Post post){
        this.post.add(post);
    }

    public Set<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(Set<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public Set<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(Set<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }
        
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
}
