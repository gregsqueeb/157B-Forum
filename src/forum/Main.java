/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg157b;

/**
 *
 * @author Greg
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Class klasses[] = {Thread.class};
        HibernateContext.addClasses(klasses);
        HibernateContext.createSchema();
        
        
        // Testing the Threads
        Thread.load();
        Thread.list();
        Thread thread = Thread.find(1);
        if (thread != null) {
            thread.print();
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
        
    }
}
