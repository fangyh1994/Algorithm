/**
 * 
 */
import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
/**
 * @author Mark
 *
 */
public class Permutation {
   public static void main(String[] args) {
     RandomizedQueue<String> queue = new RandomizedQueue<String>();
     int k = Integer.parseInt(args[0]);
     while (!StdIn.isEmpty()) {  
       String string = StdIn.readString();  
       queue.enqueue(string);  
   }  
   while (k > 0){  
       StdOut.println(queue.dequeue());  
       k--;  
   } 
     //StdIn.readString();
   }
}
