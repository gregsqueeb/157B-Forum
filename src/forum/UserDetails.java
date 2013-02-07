package forum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ryan
 */
@Entity
@Table(name = "User_Details")
public class UserDetails {
    
    private long id;
    private String emailAddress;
    
    public UserDetails(){}
    
    public UserDetails(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }
    
    @Id
    @GeneratedValue
    @Column(name="id")
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    
    @Column(name="emailAddress")
    public String getEmailAddress() {return emailAddress;}
    public void setEmailAddress(String address) 
    {
        this.emailAddress = address;
    } 
}
