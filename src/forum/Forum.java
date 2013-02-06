package forum;

import java.util.LinkedList;
import java.util.List;

import forum.Thread;
import forum.User;
import javax.persistence.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
public class Forum {
        private long id;
    	private String name;
	public List<Thread> threads;
	public List<User> users;
    
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
        
            // Fill the Thread table in a transaction.
            Transaction tx = session.beginTransaction(); 
            {
                session.save(new Forum("CS157B"));
            }
            tx.commit();
            session.close();
        
            System.out.println("Forum table loaded.");
        }
        
        public void print()
        {
            System.out.printf("%d: %s #threads: %d\n", id, name, threads.size());
        }

}
