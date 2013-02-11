package forum;

import java.util.LinkedList;
import java.util.List;

import forum.Thread;
import forum.User;
import java.util.ArrayList;
import javax.persistence.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

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
                    joinColumns={@JoinColumn(name = "forumId")},
                    inverseJoinColumns={@JoinColumn(name = "userId")})
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
            
            User mac = User.find("mac");
            User dennis = User.find("dennis");
            User dee = User.find("dee");
            User frank = User.find("frank");
            
            // Add users to forum
            cs157b.getUsers().add(mac);
            cs157b.getUsers().add(dee);
            cs157b.getUsers().add(frank);
           
            cs151.getUsers().add(dee);
            cs151.getUsers().add(dennis);
            
            // Fill the Thread table in a transaction.
            Transaction tx = session.beginTransaction(); 
            {
                session.save(cs157b);
                session.save(cs151);
            }
            tx.commit();
            session.close();
            System.out.println("Forum tables loaded.");
        }
        
        public void print()
        {
            System.out.printf("%d: %s #threads: %d\n", id, name, threads.size());
        }
        public void printInSession()
    {
        Session session = HibernateContext.getSession();
        session.update(this);
        print();
        session.close();
    }
        public static void list()
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Forum");
        
        System.out.println("All Forums: ");
        
        for (Forum forum : (List<Forum>) query.list())
        {
            forum.print();
            
            for (Thread threads : forum.getThreads())
            {
                System.out.printf("    Thread: %s\n", threads.getTitle());
                
                for(MyForumPost posts : threads.getPosts()){
                    System.out.printf("        Posts: %s\n", posts.getContent());
                }
            }
        }
        /*
        Criteria criteria = session.createCriteria(Forum.class);
        criteria.addOrder(Order.asc("name"));
        
        List<Forum> forums = criteria.list();
        System.out.println("All Forums:");
        
        for(Forum forum : forums){
            forum.print();
            
            for(Thread thread : forum.getThreads()){
                System.out.printf("    Thread: %s\n", thread.getTitle());
                
                for(MyForumPost posts : thread.getPosts()){
                    System.out.printf("        Posts: %s\n", posts.getContent());
                }
            }
        }*/
        
        session.close();
    }
}
