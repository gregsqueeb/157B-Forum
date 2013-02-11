package forum;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
 * @version  2/6/13
 */

@Entity
public class User
{    
    private long id;
    private String userName;
    private UserDetails userDetails;
    private List<MyForumPost> posts = new ArrayList<MyForumPost>();
    private List<Forum> forums = new ArrayList<Forum>();
    
    public User() {}
    
    public User(String userName, UserDetails userDetails)
    {
        this.userName = userName;
        this.userDetails = userDetails;
    }
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    
    @Column(name = "userName")
    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userDetailsId")
    public UserDetails getUserDetails() {return userDetails;}
    public void setUserDetails(UserDetails userDetails) 
                                        { this.userDetails = userDetails; }
    
    @OneToMany(mappedBy = "user", targetEntity = MyForumPost.class,
                cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    public List<MyForumPost> getPosts() { return posts; }
    public void setPosts(List<MyForumPost> posts) { this.posts = posts; }
    
    @ManyToMany
    @JoinTable(name = "forum_user", 
                joinColumns={@JoinColumn(name = "userId")},
                inverseJoinColumns={@JoinColumn(name = "forumId")})
    public List<Forum> getForums() { return forums;}
    public void setForums(List<Forum> forums) { this.forums = forums; }
    
    /**
     * Print user attributes.
     */
    public void print()
    {
        System.out.printf("%d: %s (%s)\n", id, userName, userDetails.getEmailAddress());
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
            session.save(new User("Mac", new UserDetails("mac@email.com")));
            session.save(new User("Dennis", new UserDetails("dennis@email.com")));
            session.save(new User("Dee", new UserDetails("dee@email.com")));
            session.save(new User("Charlie", new UserDetails("charlie@email.com")));
            session.save(new User("Frank", new UserDetails("frank@email.com")));  
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
            
            System.out.print("    Forums:");
            for (Forum forums : user.getForums())
            {
                System.out.printf(" %s", forums.getName());
            }
            System.out.println("");
        }
        
        session.close();
    }
    
    public static User find(long id)
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from User where id = :idvar");
        
        query.setLong("idvar", id);
        User user = (User) query.uniqueResult();
        System.out.printf("ID you searched for: %d\n", id);
        
        session.close();
        return user;
    }
    
    public static User find(String userName)
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from User where userName = :name");
        
        query.setString("name", userName);
        User user = (User) query.uniqueResult();
        
        session.close();
        return user;
    }
}
