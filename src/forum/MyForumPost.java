package forum;

import java.util.LinkedList;
import java.util.List;

import forum.Thread;
import forum.User;
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
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

@Entity
@Table(name = "MyForumPost")
public class MyForumPost {
        
        private long id;
        private String content;
        public User user;
	public Thread thread;
        
        public MyForumPost() {}
        
        public MyForumPost(String content)
        {
            this.content = content;
        }
    
        @Id
        @GeneratedValue
        @Column(name="id")
        public long getId() { return id; }
        public void setId(long id) { this.id = id; }
        
        @Column(name="content")
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        @ManyToOne
        @JoinColumn(name="threadID")
        public Thread getThread() { return thread; }
        public void setThread(Thread thread) { this.thread = thread; }
        
        @ManyToOne
        @JoinColumn(name="userID")
        public User getUser() { return user; }
        public void setUser(User user) { this.user = user; }
        
        public void print()
        {
            System.out.printf("postID: %d %s userID: %d threadID: %d\n",
                        id, content, this.user.getId(), this.thread.getId());
        }
        
        /**
        * Print Post attributes within a session.
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
        
            Thread thread1 = Thread.find(1);
            User user1 = User.find(1);
            MyForumPost post1 = new MyForumPost("I love motherboards!");
            post1.setThread(thread1);
            post1.setUser(user1);
            
            User user2 = User.find(2);
            MyForumPost post2 = new MyForumPost("Motherboards are alright...");
            post2.setThread(thread1);
            post2.setUser(user2);
            
            Thread thread3 = Thread.find(3);
            MyForumPost post3 = new MyForumPost("I hate n00bs");
            post3.setThread(thread3);
            post3.setUser(user1);
            
            Thread thread4 = Thread.find(4);
            MyForumPost post4 = new MyForumPost("I like other languages though!");
            post4.setThread(thread4);
            post4.setUser(user1);
                
            // Fill the Post table in a transaction.
            Transaction tx = session.beginTransaction(); 
            {
                session.save(post1);
                session.save(post2);
                session.save(post3);
                session.save(post4);
            }
            tx.commit();
            session.close();
        
            System.out.println("Posts table loaded.");
        }
        
        public static MyForumPost find(long id)
        {
            Session session = HibernateContext.getSession();
            Query query = session.createQuery("from MyForumPost where id = :idvar");
        
            query.setLong("idvar", id);
            MyForumPost post = (MyForumPost) query.uniqueResult();
            System.out.printf("ID you searched for: %d\n", id);
        
            session.close();
            return post;
        }
        
        public static MyForumPost find(String content)
        {
            Session session = HibernateContext.getSession();
            Query query = session.createQuery("from MyForumPost where content = :content");
        
            query.setString("content", content);
            MyForumPost post = (MyForumPost) query.uniqueResult();
        
            session.close();
            return post;
        }
        public static void list()
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from MyForumPost");
        
        System.out.println("All Forum Posts:");
        
        for (MyForumPost post : (List<MyForumPost>) query.list()) {
            post.print();
        }

        session.close();
    }

}
