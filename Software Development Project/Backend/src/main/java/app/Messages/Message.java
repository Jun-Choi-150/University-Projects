package app.Messages;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import antlr.collections.List;
import app.Users.User;


/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */  

@Entity
@Table(name="message")
public class Message {
    
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="text")
    private String text;
    
    /*
     * Relations
     */
    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnore
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    @JsonIgnore
    private User recipient;

    // =============================== Constructors ================================== //

    
    public Message(Long id, String text) {
        this.id = id;
        this.text = text;
    }
    
    public Message() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    
    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }
    
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
    
}