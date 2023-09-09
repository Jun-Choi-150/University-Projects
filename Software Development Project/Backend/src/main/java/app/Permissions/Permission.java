package app.Permissions;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import app.Users.User;


/**
 * 
 * @author edited by : Sonali Malhotra / Wonjun Choi
 * 
 */  


@Entity
@Table(name="permission")
public class Permission {
    
	
    @Id
    @Column(name="id")
    private int id;
    @Column(name="type")
    private String type;

    /*
     * Relations
     */

    @OneToMany(mappedBy = "permission")
    private Set<User> users;
    
    // =============================== Constructors ================================== //
 
    public Permission(int id, String type) {
        this.id = id;
    	this.type = type;
    }

    public Permission() {
    }
    
    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    
    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }
}