package pkg157b;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

/**
 * A forum Thread.
 * 
 * Many ForumThread to one Forum
 * One ForumThread to many ForumPost
 * 
 * @author chris
 * @version January 2013
 */
@Entity
public class Thread {
    
    private long id;
    private String title;
    private long forumID;
    
    public Thread() {}
    
    public Thread(String title, long forumID)
    {
        this.title = title;
        this.forumID = forumID;
    }
    
    @Id
    @GeneratedValue
    @Column(name="id")
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    @Column(name="title")
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    @Column(name="forumID")
    public long getForumID() { return forumID; }
    public void setForumID(long forumID) { this.forumID = forumID; }
    
    /**
     * Print Thread attributes.
     */
    public void print()
    {
        System.out.printf("%d: %s %d\n", id, title, forumID);
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
    
    public static void load()
    {
        Session session = HibernateContext.getSession();
        
        // Fill the Thread table in a transaction.
        Transaction tx = session.beginTransaction(); 
        {
            session.save(new Thread("Computers are so cool!", 1));
            session.save(new Thread("What did you think of Star Wars?", 1));
            session.save(new Thread("Can you believe all these n00bs?", 1));
            session.save(new Thread("I hate using PASCAL! It sucks!", 1));
        }
        tx.commit();
        session.close();
        
        System.out.println("Student table loaded.");
    }
    
    /**
     * List all the Threads.
     */
    public static void list()
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Thread");
        
        System.out.println("All Threads:");
        
        for (Thread thread : (List<Thread>) query.list()) {
            thread.print();
        }

        session.close();
    }
    
    /**
     * Fetch the Thread with a matching id.
     * @param id the id to match.
     * @return the Thread or null.
     */
    public static Thread find(long id)
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Thread where id = :idvar");
        
        query.setLong("idvar", id);
        Thread thread = (Thread) query.uniqueResult();
        System.out.printf("ID you searched for: %d\n", id);
        
        session.close();
        return thread;
    }
    
    /**
     * Fetch the Thread with a matching title.
     * @param title the Thread title to match.
     * @return the student or null.
     */
    public static Thread find(String title)
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Thread where title = :title");
        
        query.setString("title", title);
        Thread thread = (Thread) query.uniqueResult();
        
        session.close();
        return thread;
    }
        
}
