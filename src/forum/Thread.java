package forum;

import forum.Forum;
import forum.User;
import java.util.Set;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

/**
 * A forum Thread.
 * 
 * Many Thread to one Forum
 * One Thread to many ForumPost
 * 
 * @author chris
 * @version January 2013
 */
@Entity
public class Thread{
    
    private long id;
    private String title;
    public Forum forum;
    public Set<MyForumPost> posts;
    
    public Thread() {}
    
    public Thread(String title)
    {
        this.title = title;
    }
    
    @Id
    @GeneratedValue
    @Column(name="id")
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    @Column(name="title")
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    
    @OneToMany(mappedBy="thread", targetEntity=MyForumPost.class,
        cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public Set<MyForumPost> getPosts() { return posts; }
    public void setPosts(Set<MyForumPost> posts) { this.posts = posts; }
    
    @ManyToOne
    @JoinColumn(name="forumId")
    public Forum getForum() { return forum; }
    public void setForum(Forum forum) { this.forum = forum; }
    
    /**
     * Print Thread attributes.
     */
    public void print()
    {
        System.out.printf("%d: %s #posts: %d forum: %s\n", id, title, posts.size(), forum.getName());
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
        Forum forum = Forum.find(1);
        Forum forum2 = Forum.find(2);
        Thread thread1 = new Thread("Computers are so cool!");
        thread1.setForum(forum);
        
        Thread thread2 = new Thread("What did you think of Star Wars?");
        thread2.setForum(forum);
        
        Thread thread3 = new Thread("Can you believe all these n00bs?");
        thread3.setForum(forum2);
        
        Thread thread4 = new Thread("I hate using PASCAL! It sucks!");
        thread4.setForum(forum2);

        
        
        // Fill the Thread table in a transaction.
        Transaction tx = session.beginTransaction(); 
        {
            session.save(thread1);
            session.save(thread2);
            session.save(thread3);
            session.save(thread4);
        }
        tx.commit();
        session.close();
        
        System.out.println("Thread table loaded.");
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
