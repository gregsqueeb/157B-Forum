package forum;

import java.util.LinkedList;
import java.util.List;

import forum.Thread;
import forum.User;
import java.util.ArrayList;
import javax.persistence.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
public class Forum {
        private long id;
    	private String name;
	public List<Thread> threads;
	public List<User> users = new ArrayList<User>();
    
        public Forum(){}
	public Forum(String name) {
		this.name = name;
	}
        
        @Id
        @GeneratedValue
        @Column(name="id")
        public long getId() { return id; }
        public void setId(long id) { this.id = id; }
    
        @Column(name="name")
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        @OneToMany(mappedBy="forum", targetEntity=Thread.class,
        cascade=CascadeType.ALL, fetch=FetchType.EAGER)
        public List<Thread> getThreads() { return threads; }
        public void setThreads(List<Thread> threads) { this.threads = threads; }
        
        @ManyToMany
        @JoinTable(name = "forum_user",
                    joinColumns = @JoinColumn(name = "forumId"),
                    inverseJoinColumns = @JoinColumn(name = "userId"))
        public List<User> getUsers() { return users; }
        public void setUsers(List<User> users) { this.users = users; }

        public static Forum find(long id)
        {
            Session session = HibernateContext.getSession();
            Query query = session.createQuery("from Forum where id = :idvar");

            query.setLong("idvar", id);
            Forum forum = (Forum) query.uniqueResult();
            System.out.printf("ID you searched for: %d\n", id);

            session.close();
            return forum;
        }

        public static void load()
        {
            Session session = HibernateContext.getSession();
            
            // Create multiple forums
            Forum cs157b = new Forum("CS157B");
            Forum cs151 = new Forum("CS151");
            Forum cs152 = new Forum("CS152");
            Forum cs160 = new Forum("CS160");
            
            User mac = User.find("mac");
            User dennis = User.find("dennis");
            User dee = User.find("dee");
            User charlie = User.find("charlie");
            User frank = User.find("frank");
        
            // Assign users (moderators) to forum
            cs157b.getUsers().add(mac);
            cs157b.getUsers().add(dee);
           
            cs151.getUsers().add(dee);
            cs151.getUsers().add(dennis);
            
            cs152.getUsers().add(charlie);
            cs152.getUsers().add(mac);
            
            cs160.getUsers().add(frank);
            
            // Fill the Thread table in a transaction.
            Transaction tx = session.beginTransaction(); 
            {
                session.save(cs157b);
                session.save(cs151);
                session.save(cs152);
                session.save(cs160);
            }
            tx.commit();
            session.close();
            System.out.println("Forum tables loaded.");
        }
        
        public void print()
        {
            System.out.printf("%d: %s #threads: %d\n", id, name, threads.size());
        }

}
