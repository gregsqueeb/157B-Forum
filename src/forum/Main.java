/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

/**
 *
 * @author Greg
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Class klasses[] = {Forum.class, Thread.class, MyForumPost.class,
                            User.class, UserDetails.class};
        HibernateContext.addClasses(klasses);
        HibernateContext.createSchema();
        
        
        // Load the necessarry tables
        Forum.load();
        Thread.load();
        User.load();
        MyForumPost.load();
        Thread.list();
        Thread thread = Thread.find(1);
        MyForumPost post = MyForumPost.find(1);
        MyForumPost post2 = MyForumPost.find(2);
        Forum forum = Forum.find(1);
        if (thread != null && post != null) {
            thread.print();
            post.print();
        }
        else {
            System.out.printf("*** No student with id %d\n", 1);
        }
        thread = Thread.find("I hate using PASCAL! It sucks!");
        if (thread != null) {
            thread.print();
        }
        else {
            System.out.printf("*** No student with title 'I hate using PASCAL! It sucks!'\n");
        }
        System.out.println("");
        Thread postThread = post.getThread();
        System.out.println("Showing many to one relationship with posts -> "
                + "thread");
        post.print();
        post.thread.print();
        post.user.printInSession();
        post2.print();
        post2.user.printInSession();
        
        System.out.println("");
        System.out.println("Showing one ot many relationship with forum -> "
                + "threads");
        forum.print();
        for(Thread individualThread : forum.threads){
            individualThread.print();
        }
        
    }
}
