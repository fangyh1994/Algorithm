import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * 
 */

/**
 * @author Mark
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
  
  private Item[] s;
  private int numofelement;
  
  private void resize (int capacity) {
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < numofelement; i++) {
      copy[i] = s[i];
    }
    s = copy;
  }
  /**
   * construct an empty randomized queue
   */
   public RandomizedQueue(){
     s = (Item[]) new Object[1];
   }
   
   /**
    * is the queue empty?
    */
   public boolean isEmpty() {
     return (numofelement == 0);
   }
   
   /**
    * return the number of items on the queue
    */
   public int size() {
     return numofelement;
   }
   
   /**
    * add the item
    */
   public void enqueue(Item item) {
     if (item == null) {
       throw new java.lang.NullPointerException();
     }
     if (numofelement == s.length) {
       resize (2 * s.length);
     }
     s[numofelement] = item;
     numofelement++;
   }
   
   /**
    * remove and return a random item
    */
   public Item dequeue() {
     if (isEmpty()) {
       throw new java.util.NoSuchElementException();
     }
     int random = StdRandom.uniform(0 , numofelement);
     Item item = s[random];
     s[random] = s[numofelement - 1];
     if (random != numofelement - 1) {
       s[numofelement - 1] = null;
     }
     numofelement--;
     
     if (numofelement > 0 && numofelement == s.length/4) {
       resize (s.length/2);
     }
     return item;
   }
   
   /**
    * return (but do not remove) a random item
    */
   public Item sample() {
     if (isEmpty()) {
       throw new java.util.NoSuchElementException();
   }
     int random = StdRandom.uniform(0, numofelement);
     return s[random];
   }
   /**
    * return an independent iterator over items in random order
    */
   public Iterator<Item> iterator() {
     return new QueueIterator();
   }
   
   private class QueueIterator implements Iterator<Item> {
     private int N = numofelement;
     private Item[] iterItem = (Item[]) new Object[s.length];
     
     public QueueIterator() {
       for (int i = 0;i < numofelement;i++) {
         iterItem[i] = s[i];
         }
     }
     public boolean hasNext() { 
       return (N != 0);
       }
     public void remove() {
       throw new UnsupportedOperationException();
       }

     public Item next() {
         if (!hasNext()) {
           throw new java.util.NoSuchElementException();
         }
         int random = StdRandom.uniform(0 , N);
         Item item = s[random];
         s[random] = s[N - 1];
         if (random != N - 1) {
           s[N - 1] = null;
         }
         N--;
         
         return item;
     }
 }
}
   
   
   /**
    * unit testing (optional)
    */
 /*  public static void main(String[] args) {
     RandomizedQueue<String> queue = new RandomizedQueue<String>();
     for (int i = 0; i < 6; i++){
       String item = args[i];
       queue.enqueue(item);
     }

     
     Iterator<String> it = queue.iterator();
     //StdOut.println("(" + stack.size() + " left on stack)");
     while (it.hasNext() ) {
       StdOut.print(it.next() + " ");
     }
   }
}*/