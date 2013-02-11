/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Greg
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static final String HELP_MESSAGE =
        "*** Commands: "
            + "\n\t create, load, "
            + "\n\t find_user <n>, find_forum <n>, find_thread <n>, find_post <n>  "
            + "\n\t users, forums, forum_posts, threads, quit "
            + "\n***";
    public static void main(String[] args) {
         BufferedReader stdin = 
                new BufferedReader(new InputStreamReader(System.in));
        String command;
        Class klasses[] = {Forum.class, Thread.class, MyForumPost.class,
                            User.class, UserDetails.class};
        HibernateContext.addClasses(klasses);
        
        
        
        
        //command 
       
        do {
            System.out.print("\nCommand? ");
            
            try {
                command = stdin.readLine();
            }
            catch (java.io.IOException ex) {
                command = "?";
            }
            
            String parts[] = command.split(" ");
            
            if (command.equalsIgnoreCase("create")) {
                HibernateContext.createSchema();
            }
            else if (command.equalsIgnoreCase("load")) {
                User.load();
                Forum.load();
                Thread.load();
                MyForumPost.load();
            }
            else if (command.equalsIgnoreCase("users")) {
                User.list();
            }
            else if (command.equalsIgnoreCase("forums")) {
                Forum.list();
            }
            else if (command.equalsIgnoreCase("threads")) {
                Thread.list();
            }
            else if (command.equalsIgnoreCase("forum_posts")) {
                MyForumPost.list();
            }
            else if (parts[0].equalsIgnoreCase("find_user") &&
                    (parts.length >= 2)) {
                long id = Long.parseLong(parts[1]);
                User user = User.find(id);
                user.printInSession();
            }
            else if (parts[0].equalsIgnoreCase("find_user") &&
                    (parts.length >= 2)) {
                long id = Long.parseLong(parts[1]);
                User user = User.find(id);
                user.printInSession();
            }
            else if (parts[0].equalsIgnoreCase("find_forum") &&
                    (parts.length >= 2)) {
                long id = Long.parseLong(parts[1]);
                Forum forum = Forum.find(id);
                forum.printInSession();
            }
            else if (parts[0].equalsIgnoreCase("find_thread") &&
                    (parts.length >= 2)) {
                long id = Long.parseLong(parts[1]);
                Thread thread = Thread.find(id);
                thread.printInSession();
            }
            else if (parts[0].equalsIgnoreCase("find_post") &&
                    (parts.length >= 2)) {
                long id = Long.parseLong(parts[1]);
                MyForumPost post = MyForumPost.find(id);
                post.printInSession();
            }
            else if (!command.equalsIgnoreCase("quit")) {
                System.out.println(HELP_MESSAGE);
            }
        } while (!command.equalsIgnoreCase("quit"));
        //
            
        /*
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
        System.out.println("Showing one to many relationship with forum -> "
                + "threads");
        forum.print();
        for(Thread individualThread : forum.threads){
            individualThread.print();
        }
        */
    }//end of main
}
