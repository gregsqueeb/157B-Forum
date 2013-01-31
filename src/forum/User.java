package forum;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * A forum user
 * 
 * One User to many ForumPost
 * Many User to many Forum (moderator relationship)
 * One User to One UserDetails
 * 
 * @author Ryan
 * @version January 2013
 */

@Entity
public class User 
{    
    private long id;
    private String userName;
    
    public User() {}
    
    public User(String userName)
    {
        this.userName = userName;
    }
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    
    @Column(name = "userName")
    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}
    
    /**
     * Print user attributes.
     */
    public void print()
    {
        System.out.printf("%d: %s\n", id, userName);
    }
    
    /**
     * Print Thread attributes within a session.
     */
    public void printInSession()
    {
        Session session = HibernateContext.getSession();
        session.update(this);
        print();
        session.close();
    }
    
    /**
     * Load the User table.
     */
    public static void load()
    {
        Session session = HibernateContext.getSession();
        
        //Fill the Student table in a transaction.
        Transaction tx = session.beginTransaction();
        {
            session.save(new User("Mac"));
            session.save(new User("Dennis"));
            session.save(new User("Dee"));
            session.save(new User("Charlie"));
            session.save(new User("Frank"));  
        }
        tx.commit();
        session.close();
        
        System.out.println("User table loaded.");
    }
    
    /**
     * List all the users.
     */
    public static void list()
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from User");
        
        System.out.println("All Users: ");
        
        for (User user : (List<User>) query.list())
        {
            user.print();
        }
        
        session.close();
    }
}
