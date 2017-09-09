

/**
 * @author Yehe Fang
 * Written:       5/25/2017
 * Last updated:  5/25/2017
 * Percolation class
 * A double-ended queue or deque: supports adding and removing items
 * from either the front or the back of the data structure.
 */
import java.util.Iterator; 
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
  
  private Node first; //first element on queue
  private Node last; //last element on queue
  private int numofelement; //number of elements of queue 
  private class Node {
    private Item item;
    private Node previous;
    private Node next;
  }
  
  /**
   * construct an empty deque.
   */
   public Deque() {
     numofelement = 0;
     first = null;
     last = null;
   }
   
   /**
    * is the deque empty?
    */
   public boolean isEmpty() {
     return (numofelement == 0);
   }
   
   /**
    * return the number of items on the deque.
    */
   public int size() {
     return numofelement;
   }
   
   /**
    * add the item to the front.
    */
   public void addFirst(Item item){
     if (item == null){
       throw new java.lang.NullPointerException();
     }
     Node oldfirst = first;
     first = new Node();
     first.item = item;
     first.previous = null;
     if (isEmpty()) {
       last = first;
     } else {
       oldfirst.previous = first;
       first.next = oldfirst;
     }
     numofelement++;
   }
   
   /**
    * add the item to the end
    */
   public void addLast(Item item){
     if (item == null) {
       throw new java.lang.NullPointerException();
     }
     Node oldlast = last;
     last = new Node();
     last.item = item;
     last.next = null;
     if (isEmpty()) {
       first = last;
     }
     else {
       oldlast.next = last;
       last.previous = oldlast;
     }
     numofelement++;
   }
   
   /**
    * remove and return the item from the front
    */
   public Item removeFirst() {
     if (isEmpty()) {
       throw new java.util.NoSuchElementException();  
     }
     Item item = first.item;
     numofelement--;
     if (isEmpty()) {
       first = null;
       last = null;
     } else {
       first = first.next;
       //first.previous = null;
     }
     return item;
   }
   
   /**
    * remove and return the item from the end
    */
   public Item removeLast() {
     if (isEmpty()) {
       throw new java.util.NoSuchElementException();  
     }
     Item item = last.item;
     //last = last.previous;
     numofelement--;
     if (isEmpty()) {
       first = null;
       last = null;
     } else {
       last = last.previous;
       //last.next = null;
     }
     return item;
   }
   
   /**
    * return an iterator over items in order from front to end
    */
   public Iterator<Item> iterator() {
     return new ListIterator();
 }

 // an iterator, doesn't implement remove() since it's optional
 private class ListIterator implements Iterator<Item> {
     private Node current = first;
     public boolean hasNext() { 
       return current != null;
       }
     public void remove() {
       throw new UnsupportedOperationException();
       }

     public Item next() {
         if (!hasNext()) throw new java.util.NoSuchElementException();
         Item item = current.item;
         current = current.next; 
         return item;
     }
 }
   
 /**
  * unit testing.
  * @param args the command-line arguments
  */
   public static void main(String[] args) {
     Deque<String> stack = new Deque<String>();
    
     for (int i = 0; i < 6; i++){
     String item = args[i];
     stack.addFirst(item);
     stack.removeFirst();
     }
     for (int i = 0; i < 6; i++){
       String item = args[i];
       stack.addFirst(item);
     }
     Iterator<String> it = stack.iterator();
     
     //StdOut.println("(" + stack.size() + " left on stack)");
     while (it.hasNext() ) {
       StdOut.print(it.next() + " ");
     }
     
     
     
     //StdOut.print("test");
   }
}
